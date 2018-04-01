package com.joaquimley.transporteta.di.module

import com.joaquimley.transporteta.home.favorite.FavoritesViewModelFactory
import com.joaquimley.transporteta.sms.SmsController
import dagger.Module
import dagger.Provides

@Module
class FavouriteFragmentModule {

    @Provides
    fun provideFavouritesViewModelFactory(smsController: SmsController): FavoritesViewModelFactory {
        return FavoritesViewModelFactory(smsController)
    }
}