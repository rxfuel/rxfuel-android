package com.rxfuel.rxfuel.testData.mockProcessorModule

import com.rxfuel.rxfuel.RxFuelProcessor
import com.rxfuel.rxfuel.RxFuelProcessorModule
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object MockProcessor : RxFuelProcessorModule {

    @RxFuelProcessor(MockAction.Action1::class)
    val MockProcessor =
            ObservableTransformer<MockAction.Action1, MockResult.Result1> { actions ->
                actions.flatMap { action ->
                    Observable.just(action.text)
                            .subscribeOn(Schedulers.io())
                            .map { response -> MockResult.Result1(response + " -> Result") }
                            .observeOn(AndroidSchedulers.mainThread())
                }
            }
}