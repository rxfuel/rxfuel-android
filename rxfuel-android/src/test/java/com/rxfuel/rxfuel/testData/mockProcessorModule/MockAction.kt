package com.rxfuel.rxfuel.testData.mockProcessorModule

import com.rxfuel.rxfuel.RxFuelAction

sealed class MockAction : RxFuelAction {
    data class Action1(val text : String) : MockAction()
    object FakeAction : MockAction()
}