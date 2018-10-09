package com.joaquimley.transporteta.presentation.home.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.joaquimley.transporteta.domain.interactor.transport.favorites.ClearAllTransportsAsFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.transport.favorites.GetFavoritesUseCase
import com.joaquimley.transporteta.domain.interactor.transport.favorites.MarkTransportAsFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.transport.favorites.MarkTransportAsNoFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.transport.CancelEtaRequestUseCase
import com.joaquimley.transporteta.domain.interactor.transport.RequestEtaUseCase
import com.joaquimley.transporteta.presentation.data.Resource
import com.joaquimley.transporteta.presentation.mapper.PresentationTransportMapper
import com.joaquimley.transporteta.presentation.model.TransportView

abstract class FavoritesViewModel(protected val getFavoritesUseCase: GetFavoritesUseCase,
                                  protected val markTransportAsFavoriteUseCase: MarkTransportAsFavoriteUseCase,
                                  protected val markTransportAsNoFavoriteUseCase: MarkTransportAsNoFavoriteUseCase,
                                  protected val clearAllTransportsAsFavoriteUseCase: ClearAllTransportsAsFavoriteUseCase,
                                  protected val requestEtaUseCase: RequestEtaUseCase,
                                  protected val cancelEtaRequestUseCase: CancelEtaRequestUseCase,
                                  protected val mapper: PresentationTransportMapper) : ViewModel() {

    abstract fun isAcceptingRequests(): LiveData<Boolean>

    abstract fun onRefresh()

    abstract fun getFavorites(isForceRefresh: Boolean = false): LiveData<Resource<List<TransportView>>>

    abstract fun onEtaRequested(transportView: TransportView)

    abstract fun onEtaRequestCanceled()

    abstract fun onMarkAsFavorite(transportView: TransportView, isFavorite: Boolean)

    abstract fun removeAllFavorites()
}