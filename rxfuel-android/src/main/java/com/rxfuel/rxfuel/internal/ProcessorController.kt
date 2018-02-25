package com.rxfuel.rxfuel.internal

import com.rxfuel.rxfuel.RxFuelAction
import com.rxfuel.rxfuel.RxFuelResult
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import kotlin.reflect.KClass

object ProcessorController {

    var processorMap : HashMap<KClass<out RxFuelAction>,
            ObservableTransformer<out RxFuelAction, out RxFuelResult>> = hashMapOf()

    @Suppress("UNCHECKED_CAST")
    fun process(): ObservableTransformer<RxFuelAction, RxFuelResult> {
        return ObservableTransformer { actions ->
            actions.publish({ shared ->
                Observable.merge<RxFuelResult>(
                        processorMap.map {
                            shared.ofType(it.key.java)
                                    .compose(it.value
                                            as ObservableTransformer<in RxFuelAction,
                                            out RxFuelResult>
                                    )
                        }
                )
            })
        }
    }

}