package com.joaquimley.transporteta.presentation.home.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joaquimley.transporteta.presentation.data.Resource
import com.joaquimley.transporteta.presentation.model.FavoriteView
import com.joaquimley.transporteta.sms.SmsController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by joaquimley on 28/03/2018.
 */
class FavoritesViewModelImpl @Inject constructor(smsController: SmsController): FavoritesViewModel(smsController) {

    private var smsRequestDisposable: Disposable? = null
    private val acceptingRequestsLiveData = MutableLiveData<Boolean>()
    private val favouritesLiveData = MutableLiveData<Resource<List<FavoriteView>>>()

    init {
        val currentValue = ArrayList<FavoriteView>()
        currentValue.add(FavoriteView(1337, "This is mock data 1"))
        currentValue.add(FavoriteView(1338, "This is mock data 2"))
        currentValue.add(FavoriteView(1339, "This is mock data 3"))
        currentValue.add(FavoriteView(1330, "This is mock data 4"))
        currentValue.add(FavoriteView(1331, "This is mock data 5"))
        currentValue.add(FavoriteView(1332, "This is mock data 6"))

        favouritesLiveData.postValue(Resource.success(currentValue))
    }

    override fun onCleared() {
        super.onCleared()
        smsRequestDisposable?.dispose()
    }

    override fun getFavorites(): LiveData<Resource<List<FavoriteView>>> {
        return favouritesLiveData
    }

    override fun isAcceptingRequests(): LiveData<Boolean> {
        return acceptingRequestsLiveData
    }

    override fun retry() {
        // TODO make request to local database for favorites

    }

    override fun cancelEtaRequest() {
        smsRequestDisposable?.dispose()
        smsController.invalidateRequest()
        acceptingRequestsLiveData.postValue(true)
    }

    override fun onEtaRequested(favourite: FavoriteView) {
        requestEta(favourite.code)
    }

    private fun requestEta(code: Int) {
        smsRequestDisposable = smsController.requestEta(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { acceptingRequestsLiveData.postValue(false) }
                .doAfterTerminate { acceptingRequestsLiveData.postValue(true) }
                .subscribe({
                    // TODO Mapper from SmsModel to FavoriteViewObject
                    val newFavoriteView = FavoriteView(it.code, it.message, it.message, true)
                    val data = getCurrentData().toMutableList()

                    var index = -1
                    for (view in data.withIndex()) {
                        if (view.value.code == newFavoriteView.code) {
                            index = view.index
                            break
                        }
                    }

                    if (index != -1) {
                        data[index] = newFavoriteView
                    } else {
                        data.add(newFavoriteView)
                    }
                    // TODO Possible caching this to local storage at this point (when mapper is used)
                    // TODO And have favouritesLiveData actually bound to the cache instead of posting like this

                    favouritesLiveData.postValue(Resource.success(data))
                }, { favouritesLiveData.postValue(Resource.error(it.message.orEmpty())) })
    }

    private fun getCurrentData(): List<FavoriteView> {
        return favouritesLiveData.value?.data ?: emptyList()
    }
}