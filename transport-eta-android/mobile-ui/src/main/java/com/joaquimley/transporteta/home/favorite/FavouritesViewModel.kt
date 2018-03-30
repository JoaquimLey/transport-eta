package com.joaquimley.transporteta.home.favorite

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.joaquimley.transporteta.model.FavouriteView
import com.joaquimley.transporteta.model.data.Resource
import com.joaquimley.transporteta.sms.SmsController

/**
 * Created by joaquimley on 28/03/2018.
 */
class FavouritesViewModel(val smsController: SmsController) : ViewModel() {

    val favouritesLiveData = MutableLiveData<Resource<List<FavouriteView>>>()

    fun getFavourites(): LiveData<Resource<List<FavouriteView>>> {
        return favouritesLiveData
    }

    fun retry() {
        // TODO

    }

    fun onEtaRequested(favourite: FavouriteView) {
        // TODO make the request to the SMS controller
    }

}