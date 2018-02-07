package com.rxfuel.rxfuelsample.ui.repoList

import android.support.v4.app.FragmentActivity
import com.rxfuel.rxfuel.RxFuelViewState
import com.rxfuel.rxfuelsample.model.Repo
import java.util.*
import kotlin.reflect.KClass

/**
 * Created by salah on 2/2/18.
 */

data class RepoListViewState(val loading : Boolean,
                             val repos : List<Repo>,
                             val lastClickedRepo: Repo? = null,
                             val errorMessage : String? = null,
                             val hideKeyboard : Boolean = false,
                             override var navigate: KClass<out FragmentActivity>? = null) : RxFuelViewState {

    companion object {
        fun idle() = RepoListViewState(false, Arrays.asList())
    }

}