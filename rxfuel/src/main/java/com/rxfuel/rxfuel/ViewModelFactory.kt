package com.rxfuel.rxfuel

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import java.util.HashMap

/**
 * Created by salah on 24/1/18.
 */

class ViewModelFactory : ViewModelProvider.Factory {

    private val viewModelHashMap = HashMap<Class<out ViewModel>, ViewModel>()

    fun registerViewModel(viewModel: ViewModel) {
        if (!viewModelHashMap.containsKey(viewModel.javaClass)) {
            viewModelHashMap[viewModel.javaClass] = viewModel
        }
    }

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