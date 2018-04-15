package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.ui.home.favorite.FavoritesViewModelFactory
import com.joaquimley.transporteta.sms.SmsController
import dagger.Module
import dagger.Provides

@Module
class FavoriteFragmentModule {

    @Provides
    fun provideFavouritesViewModelFactory(smsController: SmsController): FavoritesViewModelFactory {
        return FavoritesViewModelFactory(smsController)
    }
}