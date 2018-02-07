package com.rxfuel.rxfuel

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.SchedulerSupport
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * Created by salah on 23/1/18.
 */

abstract class RxFuelViewModel<E : RxFuelEvent, out A : RxFuelAction, R : RxFuelResult,  VS : RxFuelViewState>(private val processor : RxFuelProcessor<A,R>) : ViewModel() {

    abstract var idleState : VS
    abstract var initialEvent : E?
    abstract var isInitialEventLocal : Boolean

    private val eventsSubject: PublishSubject<E> = PublishSubject.create()
    private val localEventsSubject: PublishSubject<E> = PublishSubject.create()
    private val statesObservable: Observable<VS> = compose()

    fun processEvents(events: Observable<E>?, localEvents : Observable<E>?) {

        if(initialEvent!=null){
            if(isInitialEventLocal) {
                if(localEvents!=null)
                    Observable.merge(Observable.just(initialEvent),localEvents).subscribe(localEventsSubject)
                else
                    Observable.just(initialEvent).subscribe(localEventsSubject)
                events?.subscribe(eventsSubject)
            } else {
                if(events!=null)
                    Observable.merge(Observable.just(initialEvent),events).subscribe(eventsSubject)
                else
                    Observable.just(initialEvent).subscribe(eventsSubject)
                localEvents?.subscribe(localEventsSubject)
            }
        }else{
            events?.subscribe(eventsSubject)
            localEvents?.subscribe(localEventsSubject)
        }
    }

    fun states(): Observable<VS> = statesObservable

    private fun compose(): Observable<VS> {
        return Observable.merge(
                    eventsSubject
                        .map(this::eventToAction)
                        .compose(processor.process()),
                    localEventsSubject
                        .map(this::eventToResult)
                )
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

    @CheckReturnValue
    @SchedulerSupport(SchedulerSupport.NONE)
    fun <T : Any, U : Any> Observable<T>.notOfType(clazz: Class<U>): Observable<T> {
        checkNotNull(clazz) { "clazz is null" }
        return filter { !clazz.isInstance(it) }
    }

    abstract fun eventToResult(event: E) : R

    abstract fun eventToAction(event: E): A

    private fun reducer(): BiFunction<VS, R, VS> {
        return BiFunction { previousState: VS, result: R ->
            resultToViewState(previousState,result)
        }
    }

    abstract fun resultToViewState(previousState: VS, result: R) : VS

}