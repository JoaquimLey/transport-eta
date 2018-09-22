package com.joaquimley.transporteta.presentation.home.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.joaquimley.transporteta.domain.interactor.favorites.ClearAllTransportsAsFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.GetFavoritesUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.MarkTransportAsFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.UnmarkTransportAsFavoriteUseCase
import com.joaquimley.transporteta.presentation.data.Resource
import com.joaquimley.transporteta.presentation.mapper.TransportMapper
import com.joaquimley.transporteta.presentation.model.TransportView

abstract class FavoritesViewModel(getFavoritesUseCase: GetFavoritesUseCase,
                                  markTransportAsFavoriteUseCase: MarkTransportAsFavoriteUseCase,
                                  unmarkTransportAsFavoriteUseCase: UnmarkTransportAsFavoriteUseCase,
                                  clearAllTransportsAsFavoriteUseCase: ClearAllTransportsAsFavoriteUseCase,
                                  transportMapper: TransportMapper) : ViewModel() {
//abstract class FavoritesViewModel(val smsController: SmsController): ViewModel() {

    abstract fun getFavorites(): LiveData<Resource<List<TransportView>>>

    abstract fun isAcceptingRequests(): LiveData<Boolean>

    abstract fun retry()

    abstract fun onCancelEtaRequest()

    abstract fun onEtaRequested(favourite: TransportView)
}