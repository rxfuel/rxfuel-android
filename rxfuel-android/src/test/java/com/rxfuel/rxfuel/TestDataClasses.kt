package com.rxfuel.rxfuel

import android.support.v4.app.FragmentActivity
import kotlin.reflect.KClass

sealed class TestEvent : RxFuelEvent {
    data class SampleEvent1(val text : String, override val isLocal: Boolean = true) : TestEvent()
    data class SampleEvent2(val text : String, override val isLocal: Boolean = false) : TestEvent()
    object NavigateEvent : TestEvent() {
        override val isLocal: Boolean
            get() = true
    }
}

data class TestAction(val text : String) : RxFuelAction

sealed class TestResult : RxFuelResult{
    data class SampleResult(val text : String) : TestResult()
    object NavigateResult : TestResult()
}

data class TestViewState(val text : String, override var navigate: KClass<out FragmentActivity>? = null) : RxFuelViewState
