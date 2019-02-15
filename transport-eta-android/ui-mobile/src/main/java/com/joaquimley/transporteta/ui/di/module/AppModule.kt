package com.joaquimley.transporteta.ui.di.module

import android.app.Application
import android.content.Context
import com.joaquimley.transporteta.data.executor.ThreadExecutorImpl
import com.joaquimley.transporteta.domain.executor.PostExecutionThread
import com.joaquimley.transporteta.domain.executor.ThreadExecutor
import com.joaquimley.transporteta.ui.UiThread
import com.joaquimley.transporteta.ui.di.component.ControllerSubComponent
import com.joaquimley.transporteta.ui.di.component.DataSubComponent
import com.joaquimley.transporteta.ui.di.component.RepositorySubComponent
import com.joaquimley.transporteta.ui.di.component.ViewModelSubComponent
import com.joaquimley.transporteta.ui.di.qualifier.AndroidContext
import com.joaquimley.transporteta.ui.di.scope.PerApplication
import dagger.Module
import dagger.Provides

/**
 * Module used to provide application-level dependencies.
 */
@Module(subcomponents = [
//    ControllerSubComponent::class,
//    DataSubComponent::class,
//    RepositorySubComponent::class,
//    ViewModelSubComponent::class
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
    @AndroidContext.ApplicationContext
    fun provideContext(app: Application): Context {
        return app
    }
}