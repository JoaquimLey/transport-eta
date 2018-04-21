package com.joaquimley.transporteta.presentation.home.favorite

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.joaquimley.transporteta.presentation.model.FavoriteView
import com.joaquimley.transporteta.sms.SmsController
import com.joaquimley.transporteta.presentation.data.Resource

abstract class FavoritesViewModel(val smsController: SmsController): ViewModel() {

    abstract fun getFavourites(): LiveData<Resource<List<FavoriteView>>>

    abstract fun retry()

    abstract fun onEtaRequested(favourite: FavoriteView)
}