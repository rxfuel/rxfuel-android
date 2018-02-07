package com.rxfuel.rxfuelsample.ui.main

import com.rxfuel.rxfuel.RxFuelEvent

/**
 * Created by salah on 2/2/18.
 */
sealed class MainEvent : RxFuelEvent {
    object InitialEvent : MainEvent()
    data class Search(val query : String) : MainEvent()
}