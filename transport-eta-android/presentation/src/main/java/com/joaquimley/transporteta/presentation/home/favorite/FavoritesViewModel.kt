package com.joaquimley.transporteta.presentation.home.favorite

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.joaquimley.transporteta.presentation.data.Resource
import com.joaquimley.transporteta.presentation.model.FavoriteView
import com.joaquimley.transporteta.sms.SmsController

abstract class FavoritesViewModel(val smsController: SmsController): ViewModel() {

    abstract fun getFavorites(): LiveData<Resource<List<FavoriteView>>>

    abstract fun getAcceptingRequests(): LiveData<Boolean>

    abstract fun retry()

    abstract fun cancelEtaRequest()

    abstract fun onEtaRequested(favourite: FavoriteView)
}