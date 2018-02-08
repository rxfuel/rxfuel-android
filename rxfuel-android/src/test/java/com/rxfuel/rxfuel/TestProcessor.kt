package com.rxfuel.rxfuel

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers

class TestProcessor : RxFuelProcessor<TestAction, TestResult>() {
    override val processors: HashMap<Class<out TestAction>, ObservableTransformer<out TestAction, out TestResult>>
        get() = hashMapOf(
                TestAction::class.java to sampleDataEmitter()
        )

    private fun sampleDataEmitter() =
            ObservableTransformer<TestAction, TestResult> { actions ->
                actions.flatMap { action ->
                    Observable.just(" -> Result")
                            .map { response -> TestResult(action.text + response) }
                            .observeOn(AndroidSchedulers.mainThread())
                }
            }
}