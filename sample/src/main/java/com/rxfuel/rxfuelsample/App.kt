package com.rxfuel.rxfuelsample

import com.rxfuel.rxfuelsample.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Created by salah on 2/2/18.
 */

class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
                .builder()
                .create(this@App)
    }
}