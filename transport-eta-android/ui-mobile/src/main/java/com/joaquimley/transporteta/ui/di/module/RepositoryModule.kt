package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.domain.repository.FavoritesRepository
import com.joaquimley.transporteta.domain.repository.MockFavoritesRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideFavoritesRepository(): FavoritesRepository {
        return MockFavoritesRepository()
    }


}