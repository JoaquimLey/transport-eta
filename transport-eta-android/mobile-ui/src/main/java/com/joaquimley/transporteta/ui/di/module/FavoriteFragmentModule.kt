package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModelFactory
import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModelFactoryImpl
import com.joaquimley.transporteta.sms.SmsController
import dagger.Module
import dagger.Provides

@Module
class FavoriteFragmentModule {

    @Provides
    fun provideFavouritesViewModelFactory(smsController: SmsController): FavoritesViewModelFactory {
        return FavoritesViewModelFactoryImpl(smsController)
    }
}