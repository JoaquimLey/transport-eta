package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModelFactory
import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModelFactoryImpl
import com.joaquimley.transporteta.sms.SmsController
import com.joaquimley.transporteta.ui.home.favorite.FavoritesViewModelProvider
import dagger.Module
import dagger.Provides

@Module
class HomeViewModelModule {

//    @Provides
//    fun provideViewModelProvider(viewModelFactory: FavoritesViewModelFactory): FavoritesViewModelProvider {
//        return FavoritesViewModelProvider(viewModelFactory)
//    }

    @Provides
    fun provideFavouritesViewModelFactory(smsController: SmsController): FavoritesViewModelFactory {
        return FavoritesViewModelFactoryImpl(smsController)
    }
}