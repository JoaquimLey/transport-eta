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

/**
 * Created by joaquimley on 28/03/2018.
 */
internal class FavoritesViewModelImpl(getFavoritesUseCase: GetFavoritesUseCase,
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
        compositeDisposable.dispose()
        super.onCleared()
    }

    override fun onRefresh() {
        fetchFavorites()
    }

    override fun getFavorites(isForceRefresh: Boolean): LiveData<Resource<List<TransportView>>> {
        if (isForceRefresh) {
            fetchFavorites()
        }
        return favouritesLiveData
    }

    override fun isAcceptingRequests(): LiveData<Boolean> {
        return acceptingRequestsLiveData
    }

    override fun onEtaRequested(transportView: TransportView) {
        compositeDisposable.add(requestEtaUseCase.execute(transportView.code).subscribe({
            // TODO Handle the response (still not sure if it should return a model or just the answer
        }, {
            // TODO handle request error
        }))
    }

    override fun onEtaRequestCanceled() {
        compositeDisposable.add(cancelEtaRequestUseCase.execute(null)
                .subscribe({
                    // TODO Handle eta request cancelled (push something to the UI boie)
                }, {
                    // TODO Handle error
                }))
    }

    override fun markAsFavorite(transportView: TransportView, isFavorite: Boolean) {
        compositeDisposable.add(if (isFavorite) {
            markTransportAsFavoriteUseCase.execute(mapper.toModel(transportView))
        } else {
            markTransportAsNoFavoriteUseCase.execute(mapper.toModel(transportView))
        }.subscribe({
            // TODO Do something when done, update uiModel or should the repository do that?
        }, {
            // TODO Handle error
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
        compositeDisposable.add(
                getFavoritesUseCase.execute(null)
                        .doOnSubscribe { favouritesLiveData.loading() }
                        .subscribe({
                            favouritesLiveData.success(mapper.toView(it))
                        }, {
                            favouritesLiveData.error(it)
                        }))
    }
}


//    @Deprecated("Remove after debug not needed")
//    private fun debugStuff() {
//        val debugData = ArrayList<Transport>()
//        debugData.add(Transport("Hello ${System.currentTimeMillis()}", "ZeManel", 1337, "This is mock data 1", Random().nextBoolean(), "bus"))
//        debugData.add(Transport("Hello ${System.currentTimeMillis()}", "ZeManel", 1338, "This is mock data 2", Random().nextBoolean(), "bus"))
//        debugData.add(Transport("Hello ${System.currentTimeMillis()}", "ZeManel", 1339, "This is mock data 3", Random().nextBoolean(), "bus"))
//        debugData.add(Transport("Hello ${System.currentTimeMillis()}", "ZeManel", 1330, "This is mock data 4", Random().nextBoolean(), "bus"))
//        debugData.add(Transport("Hello ${System.currentTimeMillis()}", "ZeManel", 1331, "This is mock data 5", Random().nextBoolean(), "bus"))
//        debugData.add(Transport("Hello ${System.currentTimeMillis()}", "ZeManel", 1332, "This is mock data 6", Random().nextBoolean(), "bus"))
//
////        favouritesLiveData.success(mapper.toView(debugData.toList()))
//    }