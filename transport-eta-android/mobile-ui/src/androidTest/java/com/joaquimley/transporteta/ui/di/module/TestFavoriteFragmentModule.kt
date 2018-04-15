package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.sms.SmsController
import com.joaquimley.transporteta.ui.test.FavoritesViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class TestFavoriteFragmentModule {

    @Provides
    fun provideFavouritesViewModelFactory(smsController: SmsController): FavoritesViewModelFactory {
        return FavoritesViewModelFactory(smsController)
    }
}