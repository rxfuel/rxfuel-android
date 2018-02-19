package com.rxfuel.rxfuelsample

import com.rxfuel.rxfuel.RxFuel
import com.rxfuel.rxfuelsample.data.api.ApiProcessorModule
import com.rxfuel.rxfuelsample.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

class App : DaggerApplication() {

    @Inject
    lateinit var apiProcessorModule : ApiProcessorModule

    override fun onCreate() {
        super.onCreate()
        RxFuel.registerProcessorModule(apiProcessorModule)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
                .builder()
                .create(this@App)
    }
}