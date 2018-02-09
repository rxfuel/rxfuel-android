package com.rxfuel.rxfuel

import android.support.v4.app.FragmentActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule
import org.junit.rules.ExpectedException
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
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
        initMocks(this)
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
    fun verifyLastStateIsReRenderedOnActivityRecreation(){
        val activityController = Robolectric.buildActivity(TestActivity::class.java).create().start()
        val testActivity = spy(activityController.get())
        val rxFuel = RxFuel(testActivity)
        rxFuel.bind(TestViewModel(TestProcessor()))
        verify(testActivity).render(TestViewState("idle state"))
        verify(testActivity).render(TestViewState("First Event -> Result"))
        verify(testActivity).render(TestViewState("Second Event -> Result"))

        rxFuel.unbind()
        activityController.restart()
        rxFuel.bind(TestViewModel(TestProcessor()))

        verify(testActivity, times(2)).render(TestViewState("Third Event -> Action -> Result"))
    }
}