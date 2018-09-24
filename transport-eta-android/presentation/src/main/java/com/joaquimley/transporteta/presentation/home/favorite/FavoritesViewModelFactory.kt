package com.joaquimley.transporteta.presentation.home.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.joaquimley.transporteta.domain.interactor.favorites.ClearAllTransportsAsFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.GetFavoritesUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.MarkTransportAsFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.MarkTransportAsNoFavoriteUseCase
import com.joaquimley.transporteta.presentation.mapper.TransportMapper

abstract class FavoritesViewModelFactory(private val getFavoritesUseCase: GetFavoritesUseCase,
										 private val markTransportAsFavoriteUseCase: MarkTransportAsFavoriteUseCase,
										 private val markTransportAsNoFavoriteUseCase: MarkTransportAsNoFavoriteUseCase,
										 private val clearAllTransportsAsFavoriteUseCase: ClearAllTransportsAsFavoriteUseCase,
										 private val transportMapper: TransportMapper) : ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		return create() as T
	}

	fun create(): FavoritesViewModel {
		return create(getFavoritesUseCase,
				markTransportAsFavoriteUseCase,
				markTransportAsNoFavoriteUseCase,
				clearAllTransportsAsFavoriteUseCase,
				transportMapper)
	}

	abstract fun create(getFavoritesUseCase: GetFavoritesUseCase,
						markTransportAsFavoriteUseCase: MarkTransportAsFavoriteUseCase,
						markTransportAsNotNoFavoriteUseCase: MarkTransportAsNoFavoriteUseCase,
						clearAllTransportsAsFavoriteUseCase: ClearAllTransportsAsFavoriteUseCase,
						transportMapper: TransportMapper): FavoritesViewModel
}