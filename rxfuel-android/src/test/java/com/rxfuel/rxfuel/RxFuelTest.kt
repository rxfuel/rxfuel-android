package com.rxfuel.rxfuel

import android.support.v4.app.FragmentActivity
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
    }

    @Test
    fun testThrowsViewNotImplementedException() {
        val rxFuel = RxFuel(Robolectric.setupActivity(FragmentActivity::class.java))
        thrown.expect(RxFuelException::class.java)
        thrown.expectMessage("RxFuelView not implemented")
        rxFuel.bind(TestViewModel(null))
        rxFuel.unbind()
    }

    @Test
    fun verifyRenderCalledWithExpectedViewState() {
        val activityController = Robolectric.buildActivity(TestActivity::class.java).create().start()
        val testActivity = spy(activityController.get())
        val rxFuel = spy(RxFuel(testActivity))

        `when`(rxFuel.getViewModeFactory()).thenReturn(FakeViewModelFactory.instance)
        `when`(testActivity.events()).thenReturn(Observable.merge(
                Observable.just("First Event").map { TestEvent.SampleEvent1(it) },
                Observable.just("Second Event").map { TestEvent.SampleEvent1(it) },
                Observable.just("Third Event").map { TestEvent.SampleEvent2(it) }
        ))
        rxFuel.bind(TestViewModel(TestProcessor()))

        verify(testActivity).render(TestViewState("idle state"))
        verify(testActivity).render(TestViewState("First Event -> Result"))
        verify(testActivity).render(TestViewState("Second Event -> Result"))
        verify(testActivity).render(TestViewState("Third Event -> Action -> Result"))
        rxFuel.unbind()
        activityController.destroy()
    }

    @Test
    fun verifyInitialEventIsEmitted(){
        val testActivity = spy(Robolectric.buildActivity(TestActivity::class.java).create().get())
        val rxFuel = spy(RxFuel(testActivity))
        `when`(rxFuel.getViewModeFactory()).thenReturn(FakeViewModelFactory.instance)
        val testViewModel = TestViewModel(TestProcessor())

        RxFuel.initialEventSubject.onNext(TestEvent.SampleEvent1("Initial Event"))
        rxFuel.bind(testViewModel)

        verify(testActivity).render(TestViewState("Initial Event -> Result"))
        rxFuel.unbind()
    }

    @Test
    fun verifyLastStateIsReRenderedOnConfigurationChange(){
        val activityController = Robolectric.buildActivity(TestActivity::class.java).create().start()
        val testActivity = spy(activityController.get())
        val rxFuel = RxFuel(testActivity)
        `when`(testActivity.events()).thenReturn(Observable.merge(
                Observable.just("First Event").map { TestEvent.SampleEvent1(it) },
                Observable.just("Second Event").map { TestEvent.SampleEvent1(it) },
                Observable.just("Third Event").map { TestEvent.SampleEvent2(it) }
        ))
        rxFuel.bind(TestViewModel(TestProcessor()))

        verify(testActivity).render(TestViewState("idle state"))
        verify(testActivity).render(TestViewState("First Event -> Result"))
        verify(testActivity).render(TestViewState("Second Event -> Result"))

        rxFuel.unbind()
        activityController.configurationChange()
        rxFuel.bind(TestViewModel(TestProcessor()))

        verify(testActivity, times(2)).render(TestViewState("Third Event -> Action -> Result"))
        rxFuel.unbind()
        activityController.destroy()
    }

    @Test
    fun verifyNullNavigateStateIsRenderedAfterNavigation(){
        val activityController = Robolectric.buildActivity(TestActivity::class.java).create().start()
        val testActivity = spy(activityController.get())
        val rxFuel = spy(RxFuel(testActivity))
        `when`(rxFuel.getViewModeFactory()).thenReturn(FakeViewModelFactory.instance)

        `when`(testActivity.events())
                .thenReturn(
                        Observable.just(TestEvent.NavigateEvent).map { TestEvent.NavigateEvent }
                )
        rxFuel.bind(TestViewModel(TestProcessor()))

        //@TODO update verification
        com.nhaarman.mockito_kotlin.verify(testActivity, times(3)).render(TestViewState(text = "idle state"))
        rxFuel.unbind()
    }

    @Test
    fun verifyOnlyIdleStateRenderedWhenEventIsNull(){
        val activityController = Robolectric.buildActivity(TestActivity::class.java).create().start()
        val testActivity = spy(activityController.get())
        val rxFuel = spy(RxFuel(testActivity))
        `when`(rxFuel.getViewModeFactory()).thenReturn(FakeViewModelFactory.instance)
        rxFuel.bind(TestViewModel(TestProcessor()))

        verify(testActivity, times(1)).render(com.nhaarman.mockito_kotlin.any())

        rxFuel.unbind()
    }
}