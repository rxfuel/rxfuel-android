package com.rxfuel.rxfuelsample.ui.repoList

import com.rxfuel.rxfuel.RxFuelViewModel
import javax.inject.Inject

/**
 * Created by salah on 2/2/18.
 */

class RepoListViewModel @Inject constructor(mainProcessor : RepoListProcessor) : RxFuelViewModel<RepoListEvent, RepoListAction, RepoListResult, RepoListViewState>(mainProcessor) {

    override var idleState: RepoListViewState
        get() = RepoListViewState.idle()
        set(value) {}

    override fun eventToResult(event: RepoListEvent): RepoListResult {
        return when(event){
            is RepoListEvent.RepoClick -> RepoListResult.RepoClickResult(event.repo)
            else -> RepoListResult.InitialResult
        }
    }

    override fun eventToAction(event: RepoListEvent): RepoListAction {
        return when(event){
            is RepoListEvent.Search -> RepoListAction.SearchAction(event.query)
            else -> RepoListAction.IdleAction
        }
    }

    override fun resultToViewState(previousState: RepoListViewState, result: RepoListResult): RepoListViewState {
        return when(result) {
            is RepoListResult.SearchResult.Success -> previousState.copy(repos = result.repos, loading = false, hideKeyboard = false)
            RepoListResult.SearchResult.InFlight -> previousState.copy(loading = true, hideKeyboard = true)
            is RepoListResult.SearchResult.Failure -> previousState.copy(errorMessage = result.errorMessage, loading = false, hideKeyboard = false)
            is RepoListResult.RepoClickResult -> previousState.copy(navigate = RepoListActivity::class, lastClickedRepo = result.repo)
            else -> idleState
        }
    }
}