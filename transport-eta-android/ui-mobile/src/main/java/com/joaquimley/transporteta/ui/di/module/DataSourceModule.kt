package com.joaquimley.transporteta.ui.di.module

import android.content.Context
import com.joaquimley.transporteta.data.source.FrameworkLocalStorage
import com.joaquimley.transporteta.sharedpreferences.FrameworkLocalStorageImpl
import com.joaquimley.transporteta.ui.di.qualifier.AndroidContext
import com.joaquimley.transporteta.ui.di.scope.PerApplication
import dagger.Module
import dagger.Provides

@Module
class DataSourceModule {

    @Provides
    @PerApplication
    fun provideSharedPreferencesDataSource(@AndroidContext.ApplicationContext applicationContext: Context): FrameworkLocalStorage {
        return FrameworkLocalStorageImpl(applicationContext)
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