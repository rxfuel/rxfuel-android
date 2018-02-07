package com.rxfuel.rxfuelsample.ui.main

import android.support.v4.app.FragmentActivity
import com.rxfuel.rxfuel.RxFuelViewState
import java.util.*
import kotlin.reflect.KClass

/**
 * Created by salah on 2/2/18.
 */

data class MainViewState(val inFlight : Boolean,
                         val repos : List<String>,
                         val errorMessage : String? = null,
                         override var navigate: KClass<out FragmentActivity>? = null) : RxFuelViewState {
    companion object {
        fun idle() = MainViewState(false, Arrays.asList("Hello", "L"))
    }
}