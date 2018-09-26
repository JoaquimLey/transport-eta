package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.domain.interactor.favorites.ClearAllTransportsAsFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.GetFavoritesUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.MarkTransportAsFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.MarkTransportAsNoFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.transport.CancelEtaRequestUseCase
import com.joaquimley.transporteta.domain.interactor.transport.RequestEtaUseCase
import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModelFactory
import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModelFactoryImpl
import com.joaquimley.transporteta.presentation.mapper.TransportMapper
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
    fun provideFavouritesViewModelFactory(
            getFavoritesUseCase: GetFavoritesUseCase,
            markTransportAsFavoriteUseCase: MarkTransportAsFavoriteUseCase,
            markTransportAsNoFavoriteUseCase: MarkTransportAsNoFavoriteUseCase,
            clearAllTransportsAsFavoriteUseCase: ClearAllTransportsAsFavoriteUseCase,
            requestEtaUseCase: RequestEtaUseCase,
            cancelEtaRequestUseCase: CancelEtaRequestUseCase,
            mapper: TransportMapper): FavoritesViewModelFactory {

        return FavoritesViewModelFactoryImpl(getFavoritesUseCase,
                markTransportAsFavoriteUseCase,
                markTransportAsNoFavoriteUseCase,
                clearAllTransportsAsFavoriteUseCase,
                requestEtaUseCase,
                cancelEtaRequestUseCase,
                mapper)
    }
}