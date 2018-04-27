package com.joaquimley.transporteta.ui.di.module

import android.app.Application
import android.content.Context
import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModelFactory
import com.joaquimley.transporteta.ui.home.favorite.FavoritesViewModelProvider
import com.joaquimley.transporteta.ui.injection.scope.PerApplication
import dagger.Module
import dagger.Provides
import org.mockito.Mockito.mock

@Module
class TestAppModule {

    @Provides
    fun provideFavoritesViewModelProvider(favoritesViewModelFactory: FavoritesViewModelFactory): FavoritesViewModelProvider {
        return mock(FavoritesViewModelProvider::class.java)
    }

    @Provides
    @PerApplication
    fun provideContext(application: Application): Context {
        return application
    }
}