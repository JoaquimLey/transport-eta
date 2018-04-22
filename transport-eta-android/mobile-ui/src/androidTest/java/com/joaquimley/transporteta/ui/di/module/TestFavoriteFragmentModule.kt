package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModelFactory
import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModelFactoryImpl
import dagger.Module
import dagger.Provides
import org.mockito.Mockito.mock

@Module
class TestFavoriteFragmentModule {

    companion object {
        @JvmStatic val favoritesViewModelsFactory = mock(FavoritesViewModelFactory::class.java)
    }

    @Provides
    fun provideFavoritesViewModelFactory(): FavoritesViewModelFactory {
        return favoritesViewModelsFactory
    }
}