package com.rxfuel.rxfuelsample.ui.detail

import com.rxfuel.rxfuel.RxFuelAction
import com.rxfuel.rxfuel.RxFuelResult
import com.rxfuel.rxfuel.RxFuelViewModel
import javax.inject.Inject

class DetailViewModel @Inject constructor() : RxFuelViewModel<DetailEvent, DetailViewState>() {
    override var initialState: DetailViewState
        get() = DetailViewState.idle()
        set(value) {}

    override fun eventToAction(event: DetailEvent) : RxFuelAction {
        return RxFuelAction.NO_ACTION
    }

    override fun resultToViewState(previousState: DetailViewState, result: RxFuelResult): DetailViewState {
        return DetailViewState.idle()
    }

    override fun eventToViewState(previousState: DetailViewState, event: DetailEvent): DetailViewState {
        return when(event) {
            is DetailEvent.DisplayRepoEvent -> previousState.copy(repo = event.repo)
        }
    }

    override fun stateAfterNavigation(navigationState: DetailViewState) : DetailViewState {
        return navigationState.copy(navigate = null)
    }

}