package com.rxfuel.rxfuel

import android.support.v4.app.FragmentActivity
import kotlin.reflect.KClass

sealed class TestEvent : RxFuelEvent {
    data class SampleEvent1(val text : String) : TestEvent()
    data class SampleEvent2(val text : String) : TestEvent()
    object NavigateEvent : TestEvent()
}

data class TestViewState(val text : String, override var navigate: KClass<out FragmentActivity>? = null) : RxFuelViewState