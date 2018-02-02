package com.rxfuel.rxfuel

import io.reactivex.Observable
import io.reactivex.ObservableTransformer

/**
 * Created by salah on 27/1/18.
 */

abstract class RxFuelProcessor<A : RxFuelAction, R : RxFuelResult> {

    abstract val processors : HashMap<Class<out A>,ObservableTransformer<out A,out R>>

    @Suppress("UNCHECKED_CAST")
    fun process(): ObservableTransformer<A, R> {
        return ObservableTransformer { actions ->
            actions.publish({ shared ->
                Observable.merge<R>(
                        processors.map { shared.ofType(it.key).compose(it.value as ObservableTransformer<in A, out R>) }
                )
            })
        }
    }
}