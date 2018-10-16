package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.data.source.FrameworkLocalStorage
import com.joaquimley.transporteta.data.store.TransportDataStore
import com.joaquimley.transporteta.data.store.TransportDataStoreImpl
import com.joaquimley.transporteta.ui.di.scope.PerApplication
import dagger.Module
import dagger.Provides

@Module
class DataStoreModule {

    @Provides
    @PerApplication
    fun provideSharedPreferencesDataSource(frameworkLocalStorage: FrameworkLocalStorage): TransportDataStore {
        return TransportDataStoreImpl(frameworkLocalStorage)
    }

//    @Provides
//    @PerApplication
//    fun provideCacheDataSource(): CacheDataSource {
//        return
//    }

//    @Provides
//    @PerApplication
//    fun provideRemoteDataSource(): TransportRepository {
//        return TransportRepositoryImpl()
//    }


}