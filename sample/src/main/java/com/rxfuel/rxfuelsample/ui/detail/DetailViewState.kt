package com.rxfuel.rxfuelsample.ui.detail

import android.support.v4.app.FragmentActivity
import com.rxfuel.rxfuel.RxFuelViewState
import com.rxfuel.rxfuelsample.model.Repo
import kotlin.reflect.KClass

/**
 * Created by salah on 2/2/18.
 */

data class DetailViewState(val repo: Repo? = null,
                           override var navigate: KClass<out FragmentActivity>? = null) : RxFuelViewState {

    companion object {
        fun idle() = DetailViewState()
    }

}