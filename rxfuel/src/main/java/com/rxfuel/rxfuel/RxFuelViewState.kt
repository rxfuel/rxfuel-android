package com.rxfuel.rxfuel

import android.support.v4.app.FragmentActivity
import kotlin.reflect.KClass

/**
 * Created by salah on 23/1/18.
 */

interface RxFuelViewState {
    var navigate : KClass<out FragmentActivity>?
}