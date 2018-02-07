package com.rxfuel.rxfuelsample.ui.main

import com.rxfuel.rxfuel.RxFuelViewModel

/**
 * Created by salah on 2/2/18.
 */

class MainViewModel(mainProcessor : MainProcessor) : RxFuelViewModel<MainEvent, MainAction, MainResult, MainViewState>(mainProcessor) {

    override var idleState: MainViewState = MainViewState.idle()
        get() = field
        set(value) {}

    override var initialEvent: MainEvent = MainEvent.InitialEvent
        get() = field
        set(value) { field = value}

    override var isInitialEventLocal: Boolean
        get() = true
        set(value) {}

    override fun eventToResult(event: MainEvent): MainResult {
        return MainResult.InitialResult
    }

    override fun eventToAction(event: MainEvent): MainAction {
        return when(event){
            is MainEvent.Search -> MainAction.SearchAction(event.query)
            else -> MainAction.IdleAction
        }
    }

    override fun resultToViewState(previousState: MainViewState, result: MainResult): MainViewState {
        return when(result) {
            is MainResult.SearchResult -> previousState.copy(repos = result.repos)
            else -> idleState
        }
    }
}