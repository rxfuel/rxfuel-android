package com.rxfuel.rxfuelsample.ui.repoList

import com.rxfuel.rxfuel.RxFuelResult
import com.rxfuel.rxfuel.RxFuelViewModel
import com.rxfuel.rxfuelsample.data.api.ApiAction
import com.rxfuel.rxfuelsample.data.api.ApiResult
import com.rxfuel.rxfuelsample.ui.detail.DetailActivity
import javax.inject.Inject

class RepoListViewModel @Inject constructor(): RxFuelViewModel<RepoListEvent, RepoListViewState>() {
    override var idleState: RepoListViewState
        get() = RepoListViewState.idle()
        set(value) {}

    override fun eventToAction(event: RepoListEvent): ApiAction {
        return when(event){
            is RepoListEvent.Search -> ApiAction.SearchAction(event.query)
            else -> ApiAction.IdleAction
        }
    }

    override fun resultToViewState(previousState: RepoListViewState, result: RxFuelResult): RepoListViewState {
        return when(result) {
            is ApiResult.SearchResult.Success -> previousState.copy(repos = result.repos, loading = false, hideKeyboard = false)
            ApiResult.SearchResult.InFlight -> previousState.copy(loading = true, hideKeyboard = true)
            is ApiResult.SearchResult.Failure -> previousState.copy(errorMessage = result.errorMessage, loading = false, hideKeyboard = false)
            else -> idleState
        }
    }

    override fun eventToViewState(previousState: RepoListViewState, event: RepoListEvent): RepoListViewState {
        return when(event) {
            is RepoListEvent.RepoClick -> previousState.copy(navigate = DetailActivity::class, lastClickedRepo = event.repo)
            else -> idleState
        }
    }

    override fun stateAfterNavigation(previousState: RepoListViewState) : RepoListViewState {
        return previousState.copy(navigate = null)
    }

}