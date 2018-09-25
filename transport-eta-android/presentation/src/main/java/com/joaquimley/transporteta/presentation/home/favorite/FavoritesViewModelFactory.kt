package com.joaquimley.transporteta.presentation.home.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.joaquimley.transporteta.domain.interactor.favorites.ClearAllTransportsAsFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.GetFavoritesUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.MarkTransportAsFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.MarkTransportAsNoFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.transport.CancelEtaRequestUseCase
import com.joaquimley.transporteta.domain.interactor.transport.RequestEtaUseCase
import com.joaquimley.transporteta.presentation.mapper.TransportMapper

abstract class FavoritesViewModelFactory(private val getFavoritesUseCase: GetFavoritesUseCase,
                                         private val markTransportAsFavoriteUseCase: MarkTransportAsFavoriteUseCase,
                                         private val markTransportAsNoFavoriteUseCase: MarkTransportAsNoFavoriteUseCase,
                                         private val clearAllTransportsAsFavoriteUseCase: ClearAllTransportsAsFavoriteUseCase,
                                         private val requestEtaUseCase: RequestEtaUseCase,
                                         private val cancelEtaRequestUseCase: CancelEtaRequestUseCase,
                                         private val mapper: TransportMapper) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return create() as T
    }

    fun create(): FavoritesViewModel {
        return create(getFavoritesUseCase,
                markTransportAsFavoriteUseCase,
                markTransportAsNoFavoriteUseCase,
                clearAllTransportsAsFavoriteUseCase,
                requestEtaUseCase,
                cancelEtaRequestUseCase,
                mapper)
    }

    abstract fun create(getFavoritesUseCase: GetFavoritesUseCase,
                        markTransportAsFavoriteUseCase: MarkTransportAsFavoriteUseCase,
                        markTransportAsNotNoFavoriteUseCase: MarkTransportAsNoFavoriteUseCase,
                        clearAllTransportsAsFavoriteUseCase: ClearAllTransportsAsFavoriteUseCase,
                        requestEtaUseCase: RequestEtaUseCase,
                        cancelEtaRequestUseCase: CancelEtaRequestUseCase,
                        transportMapper: TransportMapper): FavoritesViewModel
}