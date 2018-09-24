package com.joaquimley.transporteta.presentation.home.favorite

import com.joaquimley.transporteta.domain.interactor.favorites.ClearAllTransportsAsFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.GetFavoritesUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.MarkTransportAsFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.MarkTransportAsNoFavoriteUseCase
import com.joaquimley.transporteta.presentation.mapper.TransportMapper

class FavoritesViewModelFactoryImpl(getFavoritesUseCase: GetFavoritesUseCase,
									markTransportAsFavoriteUseCase: MarkTransportAsFavoriteUseCase,
									markTransportAsNoFavoriteUseCase: MarkTransportAsNoFavoriteUseCase,
									clearAllTransportsAsFavoriteUseCase: ClearAllTransportsAsFavoriteUseCase,
									transportMapper: TransportMapper)
	: FavoritesViewModelFactory(getFavoritesUseCase,
		markTransportAsFavoriteUseCase,
		markTransportAsNoFavoriteUseCase,
		clearAllTransportsAsFavoriteUseCase,
		transportMapper) {
	override fun create(getFavoritesUseCase: GetFavoritesUseCase,
						markTransportAsFavoriteUseCase: MarkTransportAsFavoriteUseCase,
						markTransportAsNotNoFavoriteUseCase: MarkTransportAsNoFavoriteUseCase,
						clearAllTransportsAsFavoriteUseCase: ClearAllTransportsAsFavoriteUseCase,
						transportMapper: TransportMapper): FavoritesViewModel {

		return FavoritesViewModelImpl(getFavoritesUseCase,
				markTransportAsFavoriteUseCase,
				markTransportAsNotNoFavoriteUseCase,
				clearAllTransportsAsFavoriteUseCase,
				transportMapper)
	}
}