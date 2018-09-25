package com.joaquimley.transporteta.presentation.home.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.joaquimley.transporteta.domain.interactor.favorites.ClearAllTransportsAsFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.GetFavoritesUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.MarkTransportAsFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.MarkTransportAsNoFavoriteUseCase
import com.joaquimley.transporteta.presentation.data.Resource
import com.joaquimley.transporteta.presentation.mapper.TransportMapper
import com.joaquimley.transporteta.presentation.model.TransportView
import com.joaquimley.transporteta.presentation.util.extensions.error
import com.joaquimley.transporteta.presentation.util.extensions.success
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*
import javax.inject.Inject

/**
 * Created by joaquimley on 28/03/2018.
 */
internal class FavoritesViewModelImpl @Inject internal constructor(getFavoritesUseCase: GetFavoritesUseCase,
																   markTransportAsFavoriteUseCase: MarkTransportAsFavoriteUseCase,
																   markTransportAsNoFavoriteUseCase: MarkTransportAsNoFavoriteUseCase,
																   clearAllTransportsAsFavoriteUseCase: ClearAllTransportsAsFavoriteUseCase,
																   mapper: TransportMapper)
	: FavoritesViewModel(getFavoritesUseCase,
		markTransportAsFavoriteUseCase,
		markTransportAsNoFavoriteUseCase,
		clearAllTransportsAsFavoriteUseCase,
		mapper) {

	private val compositeDisposable = CompositeDisposable()
	private var smsRequestDisposable: Disposable? = null

	private val acceptingRequestsLiveData = MutableLiveData<Boolean>()
	private val favouritesLiveData = MutableLiveData<Resource<List<TransportView>>>()

	init {
		debugStuff()
	}

	override fun onCleared() {
		super.onCleared()
		smsRequestDisposable?.dispose()
		compositeDisposable.dispose()
	}

	override fun onRefresh() {
		fetchFavorites()
	}

	override fun onCancelEtaRequest() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun onEtaRequested(favourite: TransportView) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun isAcceptingRequests(): LiveData<Boolean> {
		return acceptingRequestsLiveData
	}

	override fun markAsFavorite(transportView: TransportView, isFavorite: Boolean): LiveData<Resource<List<TransportView>>> {
		compositeDisposable.add(if (isFavorite) {
			markTransportAsFavoriteUseCase.buildUseCaseObservable(mapper.toModel(transportView))
		} else {
			markTransportAsNoFavoriteUseCase.buildUseCaseObservable(mapper.toModel(transportView))
		}.subscribe({

		}, {

		}))
	}

	override fun getFavorites(): LiveData<Resource<List<TransportView>>> {
		fetchFavorites()
		return favouritesLiveData
	}

	override fun removeAllFavorites(): LiveData<Resource<List<TransportView>>> {
		compositeDisposable.add(clearAllTransportsAsFavoriteUseCase.buildUseCaseObservable(null).subscribe( {

		}, {

		}))
	}

	private fun fetchFavorites() {
		compositeDisposable.add(getFavoritesUseCase.buildUseCaseObservable().subscribe({
			favouritesLiveData.success(mapper.toView(it))
		},{
			favouritesLiveData.error(it)
		}))
	}
//
//	override fun getFavorites(): LiveData<Resourbce<List<TransportView>>> {
//		return favouritesLiveData
//	}
//
//	override fun isAcceptingRequests(): LiveData<Boolean> {
//		return acceptingRequestsLiveData
//	}
//
//	override fun onRefresh() {
//		// TODO make request to local database for favorites
//
//	}
//
//	override fun onCancelEtaRequest() {
//		smsRequestDisposable?.dispose()
//		smsController.invalidateRequest()
//		acceptingRequestsLiveData.postValue(true)
//	}
//
//	override fun onEtaRequested(favourite: TransportView) {
//		requestEta(favourite.code)
//	}
//
//	private fun requestEta(code: Int) {
//		smsRequestDisposable = smsController.requestEta(code)
//				.subscribeOn(Schedulers.io())
//				.observeOn(AndroidSchedulers.mainThread())
//				.doOnSubscribe { acceptingRequestsLiveData.postValue(false) }
//				.doAfterTerminate { acceptingRequestsLiveData.postValue(true) }
//				.subscribe({
//					// TODO Mapper from SmsModel to FavoriteViewObject
//					val newFavoriteView = TransportView(it.code, it.message, it.message, true)
//					val data = getCurrentData().toMutableList()
//
//					var index = -1
//					for (view in data.withIndex()) {
//						if (view.value.code == newFavoriteView.code) {
//							index = view.index
//							break
//						}
//					}
//
//					if (index != -1) {
//						data[index] = newFavoriteView
//					} else {
//						data.add(newFavoriteView)
//					}
//					// TODO Possible caching this to local storage at this point (when mapper is used)
//					// TODO And have favouritesLiveData actually bound to the cache instead of posting like this
//
//					favouritesLiveData.postValue(Resource.success(data))
//				}, { favouritesLiveData.postValue(Resource.error(it.message.orEmpty())) })
//	}
//
//	private fun getCurrentData(): List<TransportView> {
//		return favouritesLiveData.value?.data ?: emptyList()
//	}


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