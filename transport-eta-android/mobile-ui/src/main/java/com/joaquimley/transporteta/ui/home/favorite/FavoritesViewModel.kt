package com.joaquimley.transporteta.ui.home.favorite

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.joaquimley.transporteta.sms.SmsController
import com.joaquimley.transporteta.ui.model.FavoriteView
import com.joaquimley.transporteta.ui.model.data.Resource

abstract class FavoritesViewModel(protected val smsController: SmsController): ViewModel() {

    abstract fun getFavourites(): LiveData<Resource<List<FavoriteView>>>

    abstract fun retry()

    abstract fun onEtaRequested(favourite: FavoriteView)
}