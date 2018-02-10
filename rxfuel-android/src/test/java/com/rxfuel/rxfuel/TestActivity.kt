package com.rxfuel.rxfuel

import android.support.v4.app.FragmentActivity
import io.reactivex.Observable

open class TestActivity : FragmentActivity(), RxFuelView<TestEvent, TestViewState> {
    override fun events(): Observable<TestEvent>? = null
    override fun render(state: TestViewState) {}
}