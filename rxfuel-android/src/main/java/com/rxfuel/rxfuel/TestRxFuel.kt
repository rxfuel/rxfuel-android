package com.rxfuel.rxfuel

import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers

/**
 * Helper class to unit test RxFuel ViewModels
 *
 * @param viewModel ViewModel to test
 */
class TestRxFuelViewModel<E : RxFuelEvent,VS : RxFuelViewState>(private val viewModel: RxFuelViewModel<E,VS>){

    /**
     * Registers processor modules used by the viewModel.
     *
     */
    fun withProcessorModules(vararg modules: RxFuelProcessorModule) : TestRxFuelViewModel<E,VS> {
        RxFuel.registerProcessorModule(*modules)
        return this
    }

    /**
     * Sets Android Main Thread scheduler and IO schedulers to trampoline.
     * This will run all your processes synchronously.
     */
    fun synchronously() : TestRxFuelViewModel<E,VS> {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline()}
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        return this
    }

    /**
     * Assert view states in order using test observer.
     *
     * @return TestObserver to assert view states.
     */
    fun getObserver() : TestObserver<VS> {
        return viewModel.states().test()
    }

    /**
     * Pass events to test.
     *
     * @param events vararg of all events to test.
     */
    fun events(vararg events: E) {
        viewModel.processEvents(Observable.fromArray(*events))
    }

    /**
     * Resets schedulers.
     */
    fun destroy() {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
    }
}