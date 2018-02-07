package com.rxfuel.rxfuelsample.ui.detail

import com.rxfuel.rxfuel.RxFuelEvent
import com.rxfuel.rxfuelsample.model.Repo

/**
 * Created by salah on 2/2/18.
 */
sealed class DetailEvent : RxFuelEvent {
    data class DisplayRepoEvent(val repo: Repo) : DetailEvent() {
        override val isLocal: Boolean = true
    }
}