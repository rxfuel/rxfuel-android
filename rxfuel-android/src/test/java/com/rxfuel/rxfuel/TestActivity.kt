package com.rxfuel.rxfuel

import android.support.v4.app.FragmentActivity
import io.reactivex.Observable

open class TestActivity : FragmentActivity(), RxFuelView<TestEvent, TestViewState> {
    override fun events(): Observable<TestEvent>? {
        return Observable.merge(
                    Observable.just("First Event").map { TestEvent.SampleEvent1(it) },
                    Observable.just("Second Event").map { TestEvent.SampleEvent1(it) },
                    Observable.just("Third Event").map { TestEvent.SampleEvent2(it) }
                )
    }
    override fun render(state: TestViewState) {}
}