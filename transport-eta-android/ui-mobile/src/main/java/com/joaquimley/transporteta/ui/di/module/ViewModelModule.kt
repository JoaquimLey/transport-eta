package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.domain.interactor.transport.CancelEtaRequestUseCase
import com.joaquimley.transporteta.domain.interactor.transport.RequestEtaUseCase
import com.joaquimley.transporteta.domain.interactor.transport.favorites.ClearAllTransportsAsFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.transport.favorites.GetFavoritesUseCase
import com.joaquimley.transporteta.domain.interactor.transport.favorites.MarkTransportAsFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.transport.favorites.MarkTransportAsNoFavoriteUseCase
import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModelFactory
import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModelFactoryImpl
import com.joaquimley.transporteta.presentation.mapper.TransportMapper
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

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