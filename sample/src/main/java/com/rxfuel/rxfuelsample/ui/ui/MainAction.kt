package com.rxfuel.rxfuelsample.ui.main

import com.rxfuel.rxfuel.RxFuelAction

/**
 * Created by salah on 2/2/18.
 */

sealed class MainAction : RxFuelAction {
    object IdleAction : MainAction()
    data class SearchAction(val query : String) : MainAction()
}