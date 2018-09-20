package com.joaquimley.transporteta.ui.di.module

import android.app.Application
import android.content.Context
import com.joaquimley.transporteta.ui.di.component.SmsControllerSubComponent
import com.joaquimley.transporteta.ui.di.scope.PerApplication
import dagger.Module
import dagger.Provides

/**
 * Module used to provide application-level dependencies.
 */
@Module(subcomponents = [
    SmsControllerSubComponent::class
])
class AppModule {

    @Provides
    @PerApplication
    fun provideContext(app: Application): Context {
        return app
    }
}