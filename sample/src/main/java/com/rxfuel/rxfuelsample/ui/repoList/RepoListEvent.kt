package com.rxfuel.rxfuelsample.ui.repoList

import com.rxfuel.rxfuel.RxFuelEvent

/**
 * Created by salah on 2/2/18.
 */
sealed class RepoListEvent : RxFuelEvent {
    object InitialEvent : RepoListEvent()
    data class Search(val query : String) : RepoListEvent()
}