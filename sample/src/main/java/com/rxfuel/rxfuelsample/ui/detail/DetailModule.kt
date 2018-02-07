package com.rxfuel.rxfuelsample.ui.detail

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Created by salah on 2/2/18.
 */

@Module
abstract class DetailModule {

    @Binds
    @Singleton
    abstract fun provideDetailViewModel(viewModel: DetailViewModel): ViewModel
}