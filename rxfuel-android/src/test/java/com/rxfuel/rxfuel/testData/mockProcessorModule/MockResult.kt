package com.rxfuel.rxfuel.testData.mockProcessorModule

import com.rxfuel.rxfuel.RxFuelResult

sealed class MockResult : RxFuelResult {
    data class Result1(val text : String) : MockResult()
}