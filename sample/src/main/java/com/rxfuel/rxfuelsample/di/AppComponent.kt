package com.rxfuel.rxfuelsample.di

import com.rxfuel.rxfuelsample.App

import javax.inject.Singleton
import dagger.Component

import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Singleton
@Component(modules = [
    (ApplicationModule::class),
    (AndroidSupportInjectionModule::class),
    (NetworkModule::class),
    (ActivityBindingModule::class)
])
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}
