package com.joaquimley.transporteta.di.module

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.joaquimley.transporteta.di.component.SmsControllerSubComponent
import com.joaquimley.transporteta.ui.injection.scope.PerApplication
import dagger.Module
import dagger.Provides

/**
 * Module used to provide dependencies at an application-level.
 */
@Module(subcomponents = arrayOf(
        SmsControllerSubComponent::class
))
class AppModule {

    @Provides
    @PerApplication
    fun provideContext(app: Application): Context {
        return app
    }

    @Provides
    @PerApplication
    internal fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelProvider.NewInstanceFactory()
    }
}