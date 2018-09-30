package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.domain.repository.FavoritesRepository
import com.joaquimley.data.FavoritesRepositoryImpl
import com.joaquimley.transporteta.domain.repository.TransportRepository
import com.joaquimley.data.TransportRepositoryImpl
import com.joaquimley.transporteta.ui.di.scope.PerApplication
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    @PerApplication
    fun provideFavoritesRepository(): FavoritesRepository {
        return FavoritesRepositoryImpl()
    }

    @Provides
    @PerApplication
    fun provideTransportRepository(): TransportRepository {
        return TransportRepositoryImpl()
    }


}