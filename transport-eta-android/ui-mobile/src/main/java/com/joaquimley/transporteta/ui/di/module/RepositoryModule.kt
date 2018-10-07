package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.domain.repository.FavoritesRepository
import com.joaquimley.data.FavoritesRepositoryImpl
import com.joaquimley.transporteta.domain.repository.TransportRepository
import com.joaquimley.data.TransportRepositoryImpl
import com.joaquimley.data.mapper.TransportMapper
import com.joaquimley.data.store.TransportDataStore
import com.joaquimley.transporteta.ui.di.scope.PerApplication
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    @PerApplication
    fun provideFavoritesRepository(transportDataStore: TransportDataStore, transportMapper: TransportMapper): FavoritesRepository {
        return FavoritesRepositoryImpl(transportDataStore, transportMapper)
    }

    @Provides
    @PerApplication
    fun provideTransportRepository(transportDataStore: TransportDataStore, transportMapper: TransportMapper): TransportRepository {
        return TransportRepositoryImpl(transportDataStore,transportMapper)
    }


}