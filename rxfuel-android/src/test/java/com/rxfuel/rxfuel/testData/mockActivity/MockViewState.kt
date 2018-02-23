package com.rxfuel.rxfuel.testData.mockActivity

import android.support.v4.app.FragmentActivity
import com.rxfuel.rxfuel.RxFuelViewState
import kotlin.reflect.KClass

data class MockViewState(val text : String, override var navigate: KClass<out FragmentActivity>? = null) : RxFuelViewState