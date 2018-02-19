package com.rxfuel.rxfuelsample.ui.detail

import com.rxfuel.rxfuel.RxFuelResult
import com.rxfuel.rxfuel.RxFuelViewModel
import javax.inject.Inject

class DetailViewModel @Inject constructor() : RxFuelViewModel<DetailEvent, DetailViewState>() {

    override var idleState: DetailViewState
        get() = DetailViewState.idle()
        set(value) {}

    override fun eventToAction(event: DetailEvent): DetailAction {
        return DetailAction()
    }

    override fun resultToViewState(previousState: DetailViewState, result: RxFuelResult): DetailViewState {
        return DetailViewState.idle()
    }

    override fun eventToViewState(previousState: DetailViewState, event: DetailEvent): DetailViewState {
        return when(event) {
            is DetailEvent.DisplayRepoEvent -> previousState.copy(repo = event.repo)
        }
    }
}