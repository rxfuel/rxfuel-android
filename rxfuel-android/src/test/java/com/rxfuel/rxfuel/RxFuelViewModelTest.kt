package com.rxfuel.rxfuel

import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class RxFuelViewModelTest {

    private val testViewModel = TestViewModel(TestProcessor())

    private var testObserver : TestObserver<TestViewState> = testViewModel.states().test()

    @Before
    fun setup() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline()}
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun verifyMainObservable() {
        testViewModel.processEvents(
                Observable.merge(
                            Observable.just(TestEvent.SampleEvent1("First Event")),
                            Observable.just(TestEvent.SampleEvent2("Second Event"))
                        )
        )
        testObserver.assertValueAt(0, { state -> state.text == "idle state" })
        testObserver.assertValueAt(1, { state -> state.text == "First Event -> Result" })
        testObserver.assertValueAt(2, { state -> state.text == "Second Event -> Action -> Result" })
        testObserver.assertNoErrors()
        testObserver.assertComplete()
    }
}