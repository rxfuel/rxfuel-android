package com.rxfuel.rxfuel

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers

class TestProcessor : RxFuel {

/*
    private fun sampleDataEmitter() =
            ObservableTransformer<TestAction, TestResult> { actions ->
                actions.flatMap { action ->
                    Observable.just(" -> Result")
                            .map { response -> TestResult.SampleResult(action.text + response) }
                            .observeOn(AndroidSchedulers.mainThread())
                }
            }*/
}