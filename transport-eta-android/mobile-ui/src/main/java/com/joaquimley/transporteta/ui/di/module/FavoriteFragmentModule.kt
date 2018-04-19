package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.sms.SmsController
import com.joaquimley.transporteta.ui.presentation.home.favorite.FavoritesViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class FavoriteFragmentModule {

    @Provides
    fun provideFavouritesViewModelFactory(smsController: SmsController): FavoritesViewModelFactory {
        return FavoritesViewModelFactory(smsController)
    }
}