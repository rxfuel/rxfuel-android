package com.rxfuel.rxfuel

import android.support.v4.app.FragmentActivity
import kotlin.reflect.KClass

sealed class TestEvent : RxFuelEvent {
    data class SampleEvent1(val text : String, override val isLocal: Boolean = true) : TestEvent()
    data class SampleEvent2(val text : String, override val isLocal: Boolean = false) : TestEvent()
}

data class TestAction(val text : String) : RxFuelAction

data class TestResult(val text : String) : RxFuelResult

data class TestViewState(val text : String, override var navigate: KClass<out FragmentActivity>? = null) : RxFuelViewState
