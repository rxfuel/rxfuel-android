package com.rxfuel.rxfuelsample.di

import android.app.Application
import android.content.Context

import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {

    @Binds
    internal abstract fun bindContext(application: Application): Context

}