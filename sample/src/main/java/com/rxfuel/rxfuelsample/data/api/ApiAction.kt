package com.rxfuel.rxfuelsample.data.api

import com.rxfuel.rxfuel.RxFuelAction

sealed class ApiAction : RxFuelAction {
    object IdleAction : ApiAction()
    data class SearchAction(val query : String) : ApiAction()
}