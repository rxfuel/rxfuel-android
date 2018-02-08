package com.rxfuel.rxfuel

class TestViewModel(processor: TestProcessor?) : RxFuelViewModel<TestEvent,TestAction,TestResult,TestViewState>(processor) {
    override var idleState: TestViewState
        get() = TestViewState("idle state")
        set(value) {}

    override fun eventToResult(event: TestEvent): TestResult {
        return when(event){
            is TestEvent.SampleEvent1 -> TestResult(event.text + " -> Result")
            else -> TestResult("")
        }
    }

    override fun eventToAction(event: TestEvent): TestAction {
        return when(event){
            is TestEvent.SampleEvent2 -> TestAction(event.text+ " -> Action")
            else -> TestAction("")
        }
    }

    override fun resultToViewState(previousState: TestViewState, result: TestResult): TestViewState {
        return TestViewState(text = result.text)
    }
}