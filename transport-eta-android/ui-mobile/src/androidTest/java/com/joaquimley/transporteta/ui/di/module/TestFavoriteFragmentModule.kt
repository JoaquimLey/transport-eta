package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModelFactory
import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModelFactoryImpl
import com.joaquimley.transporteta.sms.SmsController
import com.joaquimley.transporteta.ui.home.favorite.FavoritesViewModelProvider
import dagger.Module
import dagger.Provides
import org.mockito.Mockito.mock

@Module
class TestFavoriteFragmentModule {

    companion object {
//        @JvmStatic val favoritesViewModelProvider = mock(FavoritesViewModelProvider::class.java)
        @JvmStatic val favoritesViewModelFactory: FavoritesViewModelFactory = mock(FavoritesViewModelFactory::class.java)
    }
//
//    @Provides
//    @PerApplication
//    fun provideFavoritesViewModelProvider(): FavoritesViewModelProvider {
//        return mock(FavoritesViewModelProvider::class.java)
//    }

//    @Provides
//    fun provideViewModelProvider(viewModelFactory: FavoritesViewModelFactory): FavoritesViewModelProvider {
//        return FavoritesViewModelProvider(viewModelFactory)
//        return favoritesViewModelProvider
//    }

    @Provides
    fun provideFavoritesViewModelFactory(smsController: SmsController): FavoritesViewModelFactory {
        return FavoritesViewModelFactoryImpl(smsController)
//        return favoritesViewModelFactory
    }

}