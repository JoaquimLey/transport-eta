package com.joaquimley.transporteta.di.module

import com.joaquimley.transporteta.home.favorite.FavouritesViewModelFactory
import com.joaquimley.transporteta.sms.SmsController
import dagger.Module
import dagger.Provides

@Module
class FavouriteFragmentModule {

    @Provides
    fun provideFavouritesViewModelFactory(smsController: SmsController): FavouritesViewModelFactory {
        return FavouritesViewModelFactory(smsController)
    }
}