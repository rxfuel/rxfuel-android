package com.rxfuel.rxfuelsample.ui.detail

import com.rxfuel.rxfuel.EventScope
import com.rxfuel.rxfuel.RxFuelEvent
import com.rxfuel.rxfuel.Scope
import com.rxfuel.rxfuelsample.model.Repo

sealed class DetailEvent : RxFuelEvent {

    @EventScope(Scope.UI)
    data class DisplayRepoEvent(val repo: Repo) : DetailEvent()
}