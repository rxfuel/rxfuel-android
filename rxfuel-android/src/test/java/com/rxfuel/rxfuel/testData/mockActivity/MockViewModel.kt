package com.rxfuel.rxfuel.testData.mockActivity

import com.rxfuel.rxfuel.*
import com.rxfuel.rxfuel.testData.mockProcessorModule.MockAction
import com.rxfuel.rxfuel.testData.mockProcessorModule.MockResult

class MockViewModel : RxFuelViewModel<MockEvent, MockViewState>() {

    override var initialState: MockViewState
        get() = MockViewState("idle state")
        set(value) {}

    override fun eventToAction(event: MockEvent) : RxFuelAction {
        return when(event){
            is MockEvent.SampleEvent2 -> MockAction.Action1(event.text+ " -> Action")
            else -> MockAction.FakeAction
        }
    }

    override fun resultToViewState(previousState: MockViewState, result: RxFuelResult): MockViewState {
        return when(result) {
            is MockResult.Result1 -> previousState.copy(text = result.text+ " -> ViewState")
            else -> previousState.copy(text = "ELSE")
        }
    }

    override fun eventToViewState(previousState: MockViewState, event: MockEvent): MockViewState {
        return when(event) {
            is MockEvent.SampleEvent1 -> previousState.copy(text = event.text+ " -> ViewState")
            MockEvent.NavigateEvent -> previousState.copy(navigate = MockActivity::class)
            else -> previousState.copy(text = "ELSE")
        }
    }

    override fun stateAfterNavigation(navigationState: MockViewState): MockViewState {
        return navigationState.copy(navigate = null)
    }

}