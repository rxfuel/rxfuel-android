package com.rxfuel.rxfuelsample.ui.repoList

import com.rxfuel.rxfuel.RxFuelAction

/**
 * Created by salah on 2/2/18.
 */

sealed class RepoListAction : RxFuelAction {
    object IdleAction : RepoListAction()
    data class SearchAction(val query : String) : RepoListAction()
}