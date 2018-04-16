package com.joaquimley.transporteta.ui.di.module

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.joaquimley.transporteta.ui.injection.scope.PerApplication
import dagger.Module
import dagger.Provides

@Module
class TestAppModule {

    @Provides
    @PerApplication
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @PerApplication
    internal fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelProvider.NewInstanceFactory()
    }
}