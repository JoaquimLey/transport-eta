package com.joaquimley.transporteta.presentation.home.favorite

import com.joaquimley.transporteta.domain.interactor.transport.favorites.ClearAllTransportsAsFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.transport.favorites.GetFavoritesUseCase
import com.joaquimley.transporteta.domain.interactor.transport.favorites.MarkTransportAsFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.transport.favorites.MarkTransportAsNoFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.transport.CancelEtaRequestUseCase
import com.joaquimley.transporteta.domain.interactor.transport.RequestEtaUseCase
import com.joaquimley.transporteta.presentation.mapper.TransportMapper
import javax.inject.Inject

class FavoritesViewModelFactoryImpl @Inject constructor(getFavoritesUseCase: GetFavoritesUseCase,
                                                                 markTransportAsFavoriteUseCase: MarkTransportAsFavoriteUseCase,
                                                                 markTransportAsNotNoFavoriteUseCase: MarkTransportAsNoFavoriteUseCase,
                                                                 clearAllTransportsAsFavoriteUseCase: ClearAllTransportsAsFavoriteUseCase,
                                                                 requestEtaUseCase: RequestEtaUseCase,
                                                                 cancelEtaRequestUseCase: CancelEtaRequestUseCase,
                                                                 transportMapper: TransportMapper)
    : FavoritesViewModelFactory(getFavoritesUseCase,
        markTransportAsFavoriteUseCase,
        markTransportAsNotNoFavoriteUseCase,
        clearAllTransportsAsFavoriteUseCase,
        requestEtaUseCase,
        cancelEtaRequestUseCase,
        transportMapper) {

    override fun create(getFavoritesUseCase: GetFavoritesUseCase,
                        markTransportAsFavoriteUseCase: MarkTransportAsFavoriteUseCase,
                        markTransportAsNotNoFavoriteUseCase: MarkTransportAsNoFavoriteUseCase,
                        clearAllTransportsAsFavoriteUseCase: ClearAllTransportsAsFavoriteUseCase,
                        requestEtaUseCase: RequestEtaUseCase,
                        cancelEtaRequestUseCase: CancelEtaRequestUseCase,
                        transportMapper: TransportMapper): FavoritesViewModel {

        return FavoritesViewModelImpl(getFavoritesUseCase,
                markTransportAsFavoriteUseCase,
                markTransportAsNotNoFavoriteUseCase,
                clearAllTransportsAsFavoriteUseCase,
                requestEtaUseCase,
                cancelEtaRequestUseCase,
                transportMapper)
    }
}