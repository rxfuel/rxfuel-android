package com.rxfuel.rxfuel

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModel

class FakeViewModelFactory : ViewModelFactory() {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass : Class<T>): T {
        return TestViewModel(TestProcessor()) as T
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: FakeViewModelFactory? = null

        val instance: FakeViewModelFactory
            get() {
                if (INSTANCE == null) {
                    INSTANCE = FakeViewModelFactory()
                }
                return INSTANCE!!
            }
    }
}