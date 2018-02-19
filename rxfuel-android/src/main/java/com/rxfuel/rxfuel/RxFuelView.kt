package com.rxfuel.rxfuel

import android.app.Activity
import io.reactivex.Observable

/**
 * Interface to be implemented with [Activity]
 * Takes in events that will be handled by ViewModel.
 *
 * @param E the type of Event in this context.
 * @param VS the type of ViewState in this context.
 * @author Salah (nh.salah@gmail.com)
 */
interface RxFuelView<E : RxFuelEvent, in VS : RxFuelViewState> {

    /**
     * All events in the view must be merged and returned in this function.
     * Return null if no events to be handled
     *
     * @return Observable of all events in the view
     */
    fun events(): Observable<E>?

    /**
     * Subscriber of main RxFuel observable.
     * Called with latest ViewState upon state change or re-subscription due to config changes
     *
     * @param state render the view using this state
     */
    fun render(state: VS)
}