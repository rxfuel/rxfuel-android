package com.rxfuel.rxfuel.testData.mockActivity

import android.support.v4.app.FragmentActivity
import com.rxfuel.rxfuel.RxFuelView
import io.reactivex.Observable

open class MockActivity : FragmentActivity(), RxFuelView<MockEvent, MockViewState> {
    override fun events(): Observable<MockEvent>? = null
    override fun render(state: MockViewState) {}
}