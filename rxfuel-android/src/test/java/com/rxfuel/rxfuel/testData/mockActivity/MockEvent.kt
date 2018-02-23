package com.rxfuel.rxfuel.testData.mockActivity

import com.rxfuel.rxfuel.EventScope
import com.rxfuel.rxfuel.RxFuelEvent
import com.rxfuel.rxfuel.Scope

sealed class MockEvent: RxFuelEvent {

    @EventScope(Scope.UI)
    data class SampleEvent1(val text : String) : MockEvent()

    @EventScope(Scope.DOMAIN)
    data class SampleEvent2(val text : String) : MockEvent()

    @EventScope(Scope.UI)
    object NavigateEvent : MockEvent()
}