package com.rxfuel.rxfuel

import android.arch.lifecycle.ViewModel
import android.util.Log
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * Created by salah on 23/1/18.
 */

abstract class RxFuelViewModel<E : RxFuelEvent, out A : RxFuelAction, R : RxFuelResult,  VS : RxFuelViewState>(private val processor : RxFuelProcessor<A,R>?) : ViewModel() {

    constructor() : this(null)

    abstract var idleState : VS
    var initialEvent : E? = null

    private val eventsSubject: PublishSubject<E> = PublishSubject.create()
    private val initialEventSubject: PublishSubject<E> = PublishSubject.create()
    private val statesObservable: Observable<VS> = compose()

    fun states(): Observable<VS> = statesObservable

    fun processEvents(events: Observable<E>?) {
        if(events!=null)
            Observable.merge(initialEventSubject, events).subscribe(eventsSubject)
        else
            initialEventSubject.subscribe(eventsSubject)
        if(initialEvent!=null) initialEventSubject.onNext(initialEvent!!)
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
                .flatMap {state ->
                    if(state.navigate!=null) {
                        Observable.just(1, TimeUnit.MICROSECONDS)
                                .map { state.apply { navigate = null } }
                                .startWith(state)
                    } else
                        Observable.just(state)
                }
                .replay(1)
                .autoConnect(0)
    }

    abstract fun eventToResult(event: E) : R

    abstract fun eventToAction(event: E) : A

    private fun reducer(): BiFunction<VS, R, VS> {
        return BiFunction { previousState: VS, result: R ->
            resultToViewState(previousState,result)
        }
    }

    abstract fun resultToViewState(previousState: VS, result: R) : VS

}