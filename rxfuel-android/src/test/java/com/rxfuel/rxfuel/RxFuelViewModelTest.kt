package com.rxfuel.rxfuel

import com.rxfuel.rxfuel.testData.mockActivity.MockEvent
import com.rxfuel.rxfuel.testData.mockActivity.MockViewState
import com.rxfuel.rxfuel.testData.mockActivity.MockViewModel
import com.rxfuel.rxfuel.testData.mockProcessorModule.MockProcessor
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class RxFuelViewModelTest {

    private val testViewModel = MockViewModel()

    private var testObserver : TestObserver<MockViewState> = testViewModel.states().test()

    @Before
    fun setup() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline()}
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxFuel.registerProcessorModule(MockProcessor)
    }

    @Test
    fun verifyMainObservable() {
        testViewModel.processEvents(
                Observable.merge(
                            Observable.just(MockEvent.SampleEvent1("First Event")),
                            Observable.just(MockEvent.SampleEvent2("Second Event"))
                        )
        )
        testObserver.assertValueAt(0, { state -> state.text == "idle state" })
        testObserver.assertValueAt(1, { state -> state.text == "First Event -> ViewState" })
        testObserver.assertValueAt(2, { state -> state.text == "Second Event -> Action -> Result -> ViewState" })
        testObserver.assertNoErrors()
    }
}