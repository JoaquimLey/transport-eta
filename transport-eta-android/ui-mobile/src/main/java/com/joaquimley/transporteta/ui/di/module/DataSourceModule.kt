package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.data.source.FrameworkLocalStorage
import com.joaquimley.transporteta.sharedpreferences.FrameworkLocalStorageImpl
import com.joaquimley.transporteta.ui.di.scope.PerApplication
import dagger.Module
import dagger.Provides

@Module
class DataSourceModule {

    @Provides
    @PerApplication
    fun provideSharedPreferencesDataSource(): FrameworkLocalStorage {
        return FrameworkLocalStorageImpl()
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