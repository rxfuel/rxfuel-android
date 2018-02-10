package com.rxfuel.rxfuel

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject

/**
 * Creates an Observable of ViewState by merging all events including initial events if any.
 *
 * @param E the type of Event in this context.
 * @param A the type of Action in this context.
 * @param R the type of Result in this context.
 * @param VS the type of RxFuelViewState in this context.
 * @constructor Creates ViewModel instance with processor.
 * @author Salah (nh.salah@gmail.com)
 */
abstract class RxFuelViewModel<E : RxFuelEvent, out A : RxFuelAction, R : RxFuelResult,
        VS : RxFuelViewState>(private val processor : RxFuelProcessor<A,R>?) : ViewModel() {

    /**
     * Empty constructor to use in case no processor
     */
    constructor() : this(null)

    /**
     * Idle state is rendered initially.
     */
    abstract var idleState : VS

    /**
     * Initial event is invoked upon ViewModel binding if not null.
     * Also used internally to set initial event during activity navigation.
     */
    var initialEvent : E? = null

    /**
     * Maps event to result for local(synchronous) events.
     *
     * @param event local event to be converted to result
     * @return result of event
     */
    abstract fun eventToResult(event: E) : R

    /**
     * Maps event to action for processable(asynchronous) events.
     *
     * @param event event to be converted to an action
     * @return action to the corresponding event
     */
    abstract fun eventToAction(event: E) : A

    /**
     * Maps result to ViewState to render the UI.
     *
     * @param previousState last rendered ViewState
     * @param result result data to create new ViewState.
     * @return new ViewState.
     */
    abstract fun resultToViewState(previousState: VS, result: R) : VS

    private val eventsSubject: PublishSubject<E> = PublishSubject.create()
    private val statesObservable: Observable<VS> = compose()

    fun states(): Observable<VS> = statesObservable

    fun processEvents(events: Observable<E>?) {
        if(events!=null && initialEvent!=null)
            events.startWith(initialEvent).subscribe(eventsSubject)
        else if (events==null && initialEvent!=null)
            eventsSubject.onNext(initialEvent!!)
        else if (events!=null && initialEvent==null)
            events.subscribe(eventsSubject)
    }

    private fun compose(): Observable<VS> {

        val mainObservable =
                if(processor!=null)
                    Observable.merge(
                            eventsSubject
                                    .filter{ event -> !event.isLocal }
                                    .map(this::eventToAction)
                                    .compose(processor.process()),
                            eventsSubject
                                    .filter{ event -> event.isLocal}
                                    .map(this::eventToResult)
                    )
                else
                    eventsSubject
                            .filter{ event -> event.isLocal}
                            .map(this::eventToResult)

        return mainObservable
                .scan(idleState, reducer())
                .flatMap { state ->
                    if(state.navigate!=null) {
                        Observable.just(state)
                                .startWith(state)
                                .doAfterNext {
                                    state.apply { navigate = null }
                                }
                    } else
                        Observable.just(state)
                }
                .replay(1)
                .autoConnect(0)
    }

    private fun reducer(): BiFunction<VS, R, VS>
            = BiFunction { previousState: VS, result: R -> resultToViewState(previousState,result) }

}