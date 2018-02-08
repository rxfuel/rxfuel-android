package com.rxfuel.rxfuel

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import java.util.HashMap

/**
 * Provides ViewModel instance that is persisted across activity lifecycle.
 *
 * @author Salah (nh.salah@gmail.com)
 */
open class ViewModelFactory : ViewModelProvider.Factory {

    /**
     * [HashMap] of all ViewModel class paired with ViewModel instance
     */
    private val viewModelHashMap = HashMap<Class<out ViewModel>, ViewModel>()

    /**
     * Adds ViewModel to [viewModelHashMap]
     */
    fun registerViewModel(viewModel: ViewModel) {
        if (!viewModelHashMap.containsKey(viewModel.javaClass)) {
            viewModelHashMap[viewModel.javaClass] = viewModel
        }
    }

    /**
     * Provides persisted ViewModel
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        for ((key, value) in viewModelHashMap) {
            if (modelClass.isAssignableFrom(key)) {
                return value as T
            }
        }
        throw IllegalArgumentException("Unknown class name")
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: ViewModelFactory? = null

        val instance: ViewModelFactory
            get() {
                if (INSTANCE == null) {
                    INSTANCE = ViewModelFactory()
                }
                return INSTANCE!!
            }
    }
}