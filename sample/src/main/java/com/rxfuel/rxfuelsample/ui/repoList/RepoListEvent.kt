package com.rxfuel.rxfuelsample.ui.repoList

import com.rxfuel.rxfuel.RxFuelEvent

/**
 * Created by salah on 2/2/18.
 */
sealed class RepoListEvent : RxFuelEvent {
    object InitialEvent : RepoListEvent() {
        override val isLocal: Boolean
            get() = true
    }

    data class Search(val query : String, override val isLocal: Boolean = false) : RepoListEvent()
}