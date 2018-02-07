package com.rxfuel.rxfuel

import io.reactivex.Observable
import io.reactivex.ObservableTransformer

/**
 * Merges all processors into single Observable stream.
 * Create Processor class inheriting this.
 *
 * @param A the type of Action in this context.
 * @param R the type of Result in this context.
 * @author Salah (nh.salah@gmail.com)
 */
abstract class RxFuelProcessor<A : RxFuelAction, R : RxFuelResult> {

    /**
     * Hashmap of all Actions paired with it's processor.
     */
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