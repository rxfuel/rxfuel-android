package com.rxfuel.rxfuel

import android.support.v4.app.FragmentActivity
import kotlin.reflect.KClass

/**
 * Interface to implement ViewState. ViewState ares used to render the UI.
 *
 * @author Salah (nh.salah@gmail.com)
 */
interface RxFuelViewState {

    /**
     * Implementation should override property [navigate]. Set it null by default.
     * Change the value of [navigate] to destination activity class to handle navigation.
     * A new ViewState will be trigger with [navigate] = null immediately after rendering the ViewState with navigation.
     */
    var navigate : KClass<out FragmentActivity>?
}