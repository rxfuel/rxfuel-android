package com.rxfuel.rxfuelsample.di

import com.rxfuel.rxfuelsample.ui.repoList.RepoListViewModelTest

import javax.inject.Singleton
import dagger.Component

import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by salah on 20/12/17.
 */

@Singleton
@Component(modules = [(ApplicationModule::class), (AndroidSupportInjectionModule::class), (TestNetworkModule::class)])
interface TestComponent : AppComponent {
    fun inject(test: RepoListViewModelTest)
}