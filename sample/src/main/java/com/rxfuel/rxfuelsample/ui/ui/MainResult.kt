package com.rxfuel.rxfuelsample.ui.main

import com.rxfuel.rxfuel.RxFuelResult

/**
 * Created by salah on 2/2/18.
 */

sealed class MainResult : RxFuelResult {
    object InitialResult : MainResult()
    data class SearchResult(val repos : List<String>) : MainResult()
}