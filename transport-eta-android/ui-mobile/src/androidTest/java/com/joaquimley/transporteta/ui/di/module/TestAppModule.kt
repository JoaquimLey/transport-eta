package com.joaquimley.transporteta.ui.di.module

import android.app.Application
import android.content.Context
import com.joaquimley.transporteta.domain.executor.PostExecutionThread
import com.joaquimley.transporteta.domain.repository.FavoritesRepository
import com.joaquimley.transporteta.ui.UiThread
import com.joaquimley.transporteta.ui.di.component.ControllerSubComponent
import com.joaquimley.transporteta.ui.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import org.mockito.Mockito.mock


@Module(subcomponents = [
    ControllerSubComponent::class
])
class TestAppModule {

    @Provides
    @PerApplication
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @PerApplication
    internal fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread {
        return uiThread
    }

    @Provides
    @PerApplication
    internal fun provideBufferooRepository(): FavoritesRepository {
        return mock(FavoritesRepository::class.java)
    }


}