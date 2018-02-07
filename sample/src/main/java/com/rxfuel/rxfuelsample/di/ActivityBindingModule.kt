package com.rxfuel.rxfuelsample.di

/**
 * Created by salah on 30/12/17.
 */

import com.rxfuel.rxfuelsample.ui.detail.DetailActivity
import com.rxfuel.rxfuelsample.ui.detail.DetailModule
import com.rxfuel.rxfuelsample.ui.repoList.RepoListModule
import com.rxfuel.rxfuelsample.ui.repoList.RepoListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(RepoListModule::class)])
    abstract fun repoListActivity(): RepoListActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(DetailModule::class)])
    abstract fun detailActivity(): DetailActivity

}