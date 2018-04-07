package com.joaquimley.transporteta.home.favorite

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.joaquimley.transporteta.model.FavoriteView
import com.joaquimley.transporteta.model.data.Resource
import com.joaquimley.transporteta.model.data.ResourceState
import com.joaquimley.transporteta.sms.SmsController

/**
 * Created by joaquimley on 28/03/2018.
 */
class FavoritesViewModel(private val smsController: SmsController) : ViewModel() {

    private val favouritesLiveData = MutableLiveData<Resource<List<FavoriteView>>>()

    init {
        getSms()
    }

    private fun getSms() {
        smsController.observeIncomingSms().subscribe ({
//            Log.e("FavoritesViewModel", "SmsController received sms $it")
            favouritesLiveData.postValue(Resource(ResourceState.LOADING))
            // TODO: remove mocked up shenenigans Make it more 🎨
            val currentValue = ArrayList<FavoriteView>()
            currentValue.add(FavoriteView(it.code, it.message))

//            val currentValue = favouritesLiveData.value?.data?.toMutableList()
//                    ?: emptyList<FavoriteView>()
//            currentValue.toMutableList().add(FavoriteView(it.code, it.message))

            favouritesLiveData.postValue(Resource(ResourceState.SUCCESS, currentValue))
        }, {throwable -> })
    }

    fun getFavourites(): LiveData<Resource<List<FavoriteView>>> {
        return favouritesLiveData
    }

    fun retry() {
        // TODO make request to local database for favorites

    }

    fun onEtaRequested(favourite: FavoriteView) {
        requestEta(favourite.code)
    }


    private fun requestEta(code: Int) {
        favouritesLiveData.postValue(Resource(ResourceState.LOADING))
        smsController.requestEta(code)
    }

}