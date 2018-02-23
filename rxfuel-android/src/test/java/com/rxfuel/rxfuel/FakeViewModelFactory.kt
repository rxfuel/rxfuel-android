package com.rxfuel.rxfuel

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModel
import com.rxfuel.rxfuel.internal.ViewModelFactory
import com.rxfuel.rxfuel.testData.mockActivity.TestViewModel

class FakeViewModelFactory : ViewModelFactory() {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass : Class<T>): T {
        return TestViewModel() as T
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