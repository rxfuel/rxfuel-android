package com.rxfuel.rxfuel

class TestViewModel(processor: TestProcessor?) : RxFuelViewModel<TestEvent, TestViewState>(processor) {
    override var idleState: TestViewState
        get() = TestViewState("idle state")
        set(value) {}

    override fun eventToResult(event: TestEvent): TestResult {
        return when(event){
            is TestEvent.SampleEvent1 -> TestResult.SampleResult(event.text + " -> Result")
            TestEvent.NavigateEvent -> TestResult.NavigateResult
            else -> TestResult.SampleResult("")
        }
    }

    override fun eventToAction(event: TestEvent): TestAction {
        return when(event){
            is TestEvent.SampleEvent2 -> TestAction(event.text+ " -> Action")
            else -> TestAction("")
        }
    }

    override fun resultToViewState(previousState: TestViewState, result: TestResult): TestViewState {
        return when(result){
            is TestResult.SampleResult -> previousState.copy(text = result.text)
            TestResult.NavigateResult -> previousState.copy(navigate = TestActivity::class)
        }
    }
}