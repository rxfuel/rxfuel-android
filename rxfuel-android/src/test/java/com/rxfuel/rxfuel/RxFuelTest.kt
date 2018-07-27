package com.rxfuel.rxfuel

import android.support.v4.app.FragmentActivity
import com.rxfuel.rxfuel.internal.InternalSubjects.initialEventSubject
import com.rxfuel.rxfuel.testData.mockActivity.*
import com.rxfuel.rxfuel.testData.mockProcessorModule.MockProcessor
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule
import org.junit.rules.ExpectedException
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.Robolectric

/**
 * RxFuel unit test
 */
@RunWith(RobolectricTestRunner::class)
class RxFuelTest {

    @Rule
    @JvmField
    var thrown = ExpectedException.none()!!

    @Before
    fun setup(){
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline()}
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxFuel.registerProcessorModule(MockProcessor)
    }

    @Test
    fun testThrowsViewNotImplementedException() {
        val rxFuel = RxFuel(Robolectric.setupActivity(FragmentActivity::class.java))
        thrown.expect(RxFuelException::class.java)
        thrown.expectMessage("RxFuelView not implemented")
        rxFuel.bind(MockViewModel())
        rxFuel.unbind()
    }

    @Test
    fun verifyRenderCalledWithExpectedViewState() {
        val activityController = Robolectric.buildActivity(MockActivity::class.java).create().start()
        val testActivity = spy(activityController.get())
        val rxFuel = spy(RxFuel(testActivity))

        `when`(rxFuel.getViewModeFactory()).thenReturn(FakeViewModelFactory.instance)
        `when`(testActivity.events()).thenReturn(Observable.merge(
                Observable.just("First Event").map { MockEvent.SampleEvent1(it) },
                Observable.just("Second Event").map { MockEvent.SampleEvent1(it) },
                Observable.just("Third Event").map { MockEvent.SampleEvent2(it) }
        ))
        rxFuel.bind(MockViewModel())

        verify(testActivity).render(MockViewState("idle state"))
        verify(testActivity).render(MockViewState("First Event -> ViewState"))
        verify(testActivity).render(MockViewState("Second Event -> ViewState"))
        verify(testActivity).render(MockViewState("Third Event -> Action -> Result -> ViewState"))
        rxFuel.unbind()
        activityController.destroy()
    }

    @Test
    fun verifyInitialEventIsEmitted(){
        val activityController = Robolectric.buildActivity(MockActivity::class.java).create().start()
        val testActivity = spy(activityController.get())
        val rxFuel = spy(RxFuel(testActivity))
        `when`(rxFuel.getViewModeFactory()).thenReturn(FakeViewModelFactory.instance)
        val testViewModel = MockViewModel()

        initialEventSubject.onNext(MockEvent.SampleEvent1("Initial Event"))
        rxFuel.bind(testViewModel)

        verify(testActivity).render(MockViewState("Initial Event -> ViewState"))
        rxFuel.unbind()
        activityController.destroy()
    }

    @Test
    fun verifyLastStateIsReRenderedOnConfigurationChange(){
        val activityController = Robolectric.buildActivity(MockActivity::class.java).create().start()
        val testActivity = spy(activityController.get())
        val rxFuel = RxFuel(testActivity)
        `when`(testActivity.events()).thenReturn(Observable.merge(
                Observable.just("First Event").map { MockEvent.SampleEvent1(it) },
                Observable.just("Second Event").map { MockEvent.SampleEvent1(it) },
                Observable.just("Third Event").map { MockEvent.SampleEvent2(it) }
        ))
        rxFuel.bind(MockViewModel())

        verify(testActivity).render(MockViewState("idle state"))
        verify(testActivity).render(MockViewState("First Event -> ViewState"))
        verify(testActivity).render(MockViewState("Second Event -> ViewState"))

        rxFuel.unbind()
        activityController.configurationChange()
        rxFuel.bind(MockViewModel())

        verify(testActivity, times(2))
                .render(MockViewState("Third Event -> Action -> Result -> ViewState"))
        rxFuel.unbind()
        activityController.destroy()
    }

    @Test
    fun verifyOnlyIdleStateRenderedWhenEventIsNull(){
        val activityController = Robolectric.buildActivity(MockActivity::class.java).create().start()
        val testActivity = spy(activityController.get())
        val rxFuel = spy(RxFuel(testActivity))
        `when`(rxFuel.getViewModeFactory()).thenReturn(FakeViewModelFactory.instance)
        rxFuel.bind(MockViewModel())

        verify(testActivity, times(1)).render(com.nhaarman.mockitokotlin2.any())

        rxFuel.unbind()
        activityController.destroy()
    }
}