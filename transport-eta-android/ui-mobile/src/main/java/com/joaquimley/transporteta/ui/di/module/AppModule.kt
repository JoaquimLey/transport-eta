package com.joaquimley.transporteta.ui.di.module

import android.app.Application
import android.content.Context
import com.joaquimley.data.executor.ThreadExecutorImpl
import com.joaquimley.transporteta.domain.executor.PostExecutionThread
import com.joaquimley.transporteta.domain.executor.ThreadExecutor
import com.joaquimley.transporteta.ui.UiThread
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
    internal fun provideThreadExecutor(): ThreadExecutor {
        return ThreadExecutorImpl()
    }

    @Provides
    @PerApplication
    internal fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread {
        return uiThread
    }

    @Provides
    @PerApplication
    fun provideContext(app: Application): Context {
        return app
    }
}