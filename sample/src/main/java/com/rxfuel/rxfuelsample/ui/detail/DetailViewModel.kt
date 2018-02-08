package com.rxfuel.rxfuelsample.ui.detail

import com.rxfuel.rxfuel.RxFuelViewModel
import javax.inject.Inject

class DetailViewModel @Inject constructor() : RxFuelViewModel<DetailEvent, DetailAction, DetailResult, DetailViewState>() {

    override var idleState: DetailViewState
        get() = DetailViewState.idle()
        set(value) {}

    override fun eventToResult(event: DetailEvent): DetailResult {
        return when(event){
            is DetailEvent.DisplayRepoEvent -> DetailResult.DisplayRepoResult(event.repo)
        }
    }

    override fun eventToAction(event: DetailEvent): DetailAction {
        return DetailAction()
    }

    override fun resultToViewState(previousState: DetailViewState, result: DetailResult): DetailViewState {
        return when(result) {
            is DetailResult.DisplayRepoResult -> previousState.copy(repo = result.repo)
        }
    }

}