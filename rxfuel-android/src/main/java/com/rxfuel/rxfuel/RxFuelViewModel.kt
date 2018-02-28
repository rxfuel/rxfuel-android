package com.rxfuel.rxfuel

import android.arch.lifecycle.ViewModel
import com.rxfuel.rxfuel.internal.ProcessorController.process
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import kotlin.reflect.full.findAnnotation

/**
 * Creates an Observable of ViewState by merging all events including initial events if any.
 *
 * @param E the type of Event in this context.
 * @param VS the type of RxFuelViewState in this context.
 * @constructor Creates ViewModel instance with processor.
 * @author Salah (nh.salah@gmail.com)
 */
abstract class RxFuelViewModel<E : RxFuelEvent, VS : RxFuelViewState> : ViewModel() {

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
     * Maps event to action for processable(asynchronous) events.
     *
     * @param event event to be converted to an action
     * @return action to the corresponding event
     */
    abstract fun eventToAction(event: E) : RxFuelAction

    /**
     * Maps result to ViewState for [Scope.DOMAIN] scoped events.
     *
     * @param previousState last rendered ViewState
     * @param result result data to create new ViewState.
     * @return new ViewState.
     */
    abstract fun resultToViewState(previousState: VS, result: RxFuelResult) : VS

    /**
     * Maps events to ViewState for [Scope.UI] scoped events.
     *
     * @param previousState last rendered ViewState
     * @param event event data to create new ViewState.
     * @return new ViewState.
     */
    abstract fun eventToViewState(previousState: VS, event: E) : VS

    private val eventsSubject: PublishSubject<E> = PublishSubject.create()

    private val statesObservable: Observable<VS> = compose()

    /**
     * Observable that emits view states.
     */
    fun states(): Observable<VS> = statesObservable

    /**
     * Takes in Event Observable for processing.
     */
    fun processEvents(events: Observable<E>?) {
        if(events!=null && initialEvent!=null)
            events.startWith(initialEvent).subscribe(eventsSubject)
        else if (events==null && initialEvent!=null)
            eventsSubject.onNext(initialEvent!!)
        else if (events!=null && initialEvent==null)
            events.subscribe(eventsSubject)
    }

    private fun compose(): Observable<VS> {
        return eventsSubject
                .compose(eventsTransformer())
                .scan(idleState, accumulator())
                .compose(navigationReply())
                .replay(1)
                .autoConnect(0)
    }

    private fun eventsTransformer() =
            ObservableTransformer<E, Any> { events ->
                events.flatMap { event ->
                    when(event::class.findAnnotation<EventScope>()?.scope) {
                        Scope.DOMAIN -> Observable.just(event)
                                .map(this::eventToAction).compose(process())
                        else -> Observable.just(event)
                    }
                }
            }


    private fun navigationReply() =
            ObservableTransformer<VS, VS> { states ->
                states.flatMap { state ->
                    if(state.navigate != null)
                        Observable.just(state, stateAfterNavigation(state))
                    else
                        Observable.just(state)
                }
            }

    abstract fun stateAfterNavigation(previousState: VS) : VS

    private fun accumulator(): BiFunction<VS, Any, VS> = BiFunction {
        previousState: VS, event: Any -> viewStateGenerator(previousState, event)
    }

    @Suppress("UNCHECKED_CAST")
    private fun viewStateGenerator(previousState: VS, feed: Any) : VS {
        return if(feed is RxFuelEvent)
            eventToViewState(previousState, feed as E)
        else
            resultToViewState(previousState, feed as RxFuelResult)
    }
}