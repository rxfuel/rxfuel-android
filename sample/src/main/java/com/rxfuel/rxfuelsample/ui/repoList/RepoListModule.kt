package com.rxfuel.rxfuelsample.ui.repoList

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Created by salah on 2/2/18.
 */

@Module
abstract class RepoListModule {

    @Binds
    @Singleton
    abstract fun provideMainViewModel(viewModel: RepoListViewModel): ViewModel
}