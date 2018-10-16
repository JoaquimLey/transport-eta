package com.joaquimley.transporteta.ui.di.module

import android.content.Context
import android.content.SharedPreferences
import com.joaquimley.transporteta.sharedpreferences.mapper.SharedPrefTransportMapper
import com.joaquimley.transporteta.ui.di.qualifier.AndroidContext
import com.joaquimley.transporteta.data.source.FrameworkLocalStorage
import com.joaquimley.transporteta.sharedpreferences.FrameworkLocalStorageImpl
import com.joaquimley.transporteta.ui.di.scope.PerApplication
import dagger.Module
import dagger.Provides

@Module
class DataSourceModule {

    companion object {
        private const val SHARED_PREFERENCES_NAME = "com.joaquimley.transporteta.sharedpreferences"
    }

    @Provides
    @PerApplication
    fun provideSharedPreferences(@AndroidContext.ApplicationContext applicationContext: Context): SharedPreferences {
        return applicationContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @PerApplication
    fun provideSharedPreferencesDataSource(sharedPreferences: SharedPreferences,
                                           sharedPrefTransportMapper: SharedPrefTransportMapper): FrameworkLocalStorage {
        return FrameworkLocalStorageImpl(sharedPreferences, sharedPrefTransportMapper)
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