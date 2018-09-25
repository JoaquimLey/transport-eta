package com.joaquimley.transporteta.presentation.home.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.joaquimley.transporteta.domain.interactor.favorites.ClearAllTransportsAsFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.GetFavoritesUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.MarkTransportAsFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.MarkTransportAsNoFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.transport.CancelEtaRequestUseCase
import com.joaquimley.transporteta.domain.interactor.transport.RequestEtaUseCase
import com.joaquimley.transporteta.presentation.data.Resource
import com.joaquimley.transporteta.presentation.mapper.TransportMapper
import com.joaquimley.transporteta.presentation.model.TransportView
import com.joaquimley.transporteta.presentation.util.extensions.error
import com.joaquimley.transporteta.presentation.util.extensions.loading
import com.joaquimley.transporteta.presentation.util.extensions.success
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

/**
 * Created by joaquimley on 28/03/2018.
 */
internal class FavoritesViewModelImpl @Inject internal constructor(getFavoritesUseCase: GetFavoritesUseCase,
                                                                   markTransportAsFavoriteUseCase: MarkTransportAsFavoriteUseCase,
                                                                   markTransportAsNoFavoriteUseCase: MarkTransportAsNoFavoriteUseCase,
                                                                   clearAllTransportsAsFavoriteUseCase: ClearAllTransportsAsFavoriteUseCase,
                                                                   requestEtaUseCase: RequestEtaUseCase,
                                                                   cancelEtaRequestUseCase: CancelEtaRequestUseCase,
                                                                   mapper: TransportMapper)
	: FavoritesViewModel(getFavoritesUseCase,
		markTransportAsFavoriteUseCase,
		markTransportAsNoFavoriteUseCase,
		clearAllTransportsAsFavoriteUseCase,
		requestEtaUseCase,
		cancelEtaRequestUseCase,
		mapper) {

	private val compositeDisposable = CompositeDisposable()

	private val acceptingRequestsLiveData = MutableLiveData<Boolean>()
	private val favouritesLiveData = MutableLiveData<Resource<List<TransportView>>>()

	init {
		fetchFavorites()
	}

	override fun onCleared() {
		super.onCleared()
		compositeDisposable.dispose()
	}

	override fun getFavorites(): LiveData<Resource<List<TransportView>>> {
		return favouritesLiveData
	}

	override fun onRefresh() {
		fetchFavorites()
	}

	override fun onEtaRequested(transportView: TransportView) {
		requestEtaUseCase.execute(transportView.code)
	}

	override fun onEtaRequestCanceled() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun isAcceptingRequests(): LiveData<Boolean> {
		return acceptingRequestsLiveData
	}

	override fun markAsFavorite(transportView: TransportView, isFavorite: Boolean) {
		compositeDisposable.add(if (isFavorite) {
			markTransportAsFavoriteUseCase.execute(mapper.toModel(transportView))
		} else {
			markTransportAsNoFavoriteUseCase.execute(mapper.toModel(transportView))
		}.subscribe({
			// Do something when done, update uiModel or should the repository do that?
		}, {
			// Handle error
		}))
	}

	override fun removeAllFavorites() {
		compositeDisposable.add(
				clearAllTransportsAsFavoriteUseCase.execute(null)
						.doOnSubscribe { favouritesLiveData.loading() }
						.subscribe({
						}, {

						})
		)
	}

	private fun fetchFavorites() {
		debugStuff()
		compositeDisposable.add(
				getFavoritesUseCase.execute()
						.doOnSubscribe { favouritesLiveData.loading() }
						.subscribe({
							favouritesLiveData.success(mapper.toView(it))
						}, {
							favouritesLiveData.error(it)
						}))
	}

	@Deprecated("Remove after debug not needed")
	private fun debugStuff() {
		val currentValue = ArrayList<TransportView>()
		currentValue.add(TransportView("Hello ${System.currentTimeMillis()}", 1337, "This is mock data 1", Random().nextBoolean()))
		currentValue.add(TransportView("Hello ${System.currentTimeMillis()}", 1338, "This is mock data 2", Random().nextBoolean()))
		currentValue.add(TransportView("Hello ${System.currentTimeMillis()}", 1339, "This is mock data 3", Random().nextBoolean()))
		currentValue.add(TransportView("Hello ${System.currentTimeMillis()}", 1330, "This is mock data 4", Random().nextBoolean()))
		currentValue.add(TransportView("Hello ${System.currentTimeMillis()}", 1331, "This is mock data 5", Random().nextBoolean()))
		currentValue.add(TransportView("Hello ${System.currentTimeMillis()}", 1332, "This is mock data 6", Random().nextBoolean()))

		favouritesLiveData.postValue(Resource.success(currentValue))
	}
}