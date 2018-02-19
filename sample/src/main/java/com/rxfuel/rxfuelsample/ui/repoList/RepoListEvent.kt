package com.rxfuel.rxfuelsample.ui.repoList

import com.rxfuel.rxfuel.EventScope
import com.rxfuel.rxfuel.RxFuelEvent
import com.rxfuel.rxfuel.Scope
import com.rxfuel.rxfuelsample.model.Repo

sealed class RepoListEvent : RxFuelEvent {

    @EventScope(Scope.UI)
    object InitialEvent : RepoListEvent()

    @EventScope(Scope.DOMAIN)
    data class Search(val query : String) : RepoListEvent()

    @EventScope(Scope.UI)
    data class RepoClick(val repo : Repo) : RepoListEvent()
}