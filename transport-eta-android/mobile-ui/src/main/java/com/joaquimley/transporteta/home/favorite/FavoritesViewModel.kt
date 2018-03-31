package com.joaquimley.transporteta.home.favorite

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.joaquimley.transporteta.model.FavouriteView
import com.joaquimley.transporteta.model.data.Resource
import com.joaquimley.transporteta.model.data.ResourceState
import com.joaquimley.transporteta.sms.SmsController

/**
 * Created by joaquimley on 28/03/2018.
 */
class FavoritesViewModel(val smsController: SmsController) : ViewModel() {

    val favouritesLiveData = MutableLiveData<Resource<List<FavouriteView>>>()

    init {
        smsController.serviceSms.subscribe {
            Log.e("FavoritesViewModel", "SmsController received sms $it")

            // TODO: Make it more 🎨
            val currentValue = ArrayList<FavouriteView>()
            currentValue.add(FavouriteView(it.code, it.message))

//            val currentValue = favouritesLiveData.value?.data?.toMutableList()
//                    ?: emptyList<FavouriteView>()
//            currentValue.toMutableList().add(FavouriteView(it.code, it.message))

            favouritesLiveData.setValue(Resource(ResourceState.SUCCESS, currentValue))
        }
    }

    fun getFavourites(): LiveData<Resource<List<FavouriteView>>> {
        return favouritesLiveData
    }

    fun retry() {
        // TODO make request to local database for favorites

    }

    fun onEtaRequested(favourite: FavouriteView) {
        requestEta(favourite.code)
    }


    private fun requestEta(code: Int) {
        smsController.requestEta(code)
    }

}