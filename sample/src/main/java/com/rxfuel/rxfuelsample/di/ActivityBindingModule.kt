package com.rxfuel.rxfuelsample.di

/**
 * Created by salah on 30/12/17.
 */

import com.rxfuel.rxfuelsample.ui.repoList.RepoListModule
import com.rxfuel.rxfuelsample.ui.repoList.RepoListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = arrayOf(RepoListModule::class))
    abstract fun repoListActivity(): RepoListActivity

}