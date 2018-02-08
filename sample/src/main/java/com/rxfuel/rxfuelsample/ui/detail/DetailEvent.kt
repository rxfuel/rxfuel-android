package com.rxfuel.rxfuelsample.ui.detail

import com.rxfuel.rxfuel.RxFuelEvent
import com.rxfuel.rxfuelsample.model.Repo

sealed class DetailEvent : RxFuelEvent {
    data class DisplayRepoEvent(val repo: Repo) : DetailEvent() {
        override val isLocal: Boolean = true
    }
}