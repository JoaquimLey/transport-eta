package com.joaquimley.transporteta.ui.test

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.joaquimley.transporteta.sms.SmsController
import com.joaquimley.transporteta.ui.home.favorite.FavoritesViewModel
import com.joaquimley.transporteta.ui.model.FavoriteView
import com.joaquimley.transporteta.ui.model.data.Resource
import com.joaquimley.transporteta.ui.testing.factory.TestFactoryFavoriteView

class MockFavoritesViewModel(smsController: SmsController): FavoritesViewModel(smsController) {

    private val favouritesLiveData = MutableLiveData<Resource<List<FavoriteView>>>()

    fun pushData(data: List<FavoriteView>) {
        favouritesLiveData.postValue(Resource.success(data))
    }

    fun getCurrentdata(): Resource<List<FavoriteView>>? {
        return favouritesLiveData.value
    }

    override fun getFavourites(): LiveData<Resource<List<FavoriteView>>> {
        pushData(TestFactoryFavoriteView.generateFavoriteViewList())
        return favouritesLiveData
    }

    override fun retry() {
        // TODO make request to local database for favorites

    }

    override fun onEtaRequested(favourite: FavoriteView) {
        requestEta(favourite.code)
    }

    private fun requestEta(code: Int) {

    }
}