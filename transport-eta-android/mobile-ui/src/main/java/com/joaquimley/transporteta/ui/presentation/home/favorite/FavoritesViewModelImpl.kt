package com.joaquimley.transporteta.ui.presentation.home.favorite

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.joaquimley.transporteta.sms.SmsController
import com.joaquimley.transporteta.ui.model.FavoriteView
import com.joaquimley.transporteta.ui.model.data.Resource
import com.joaquimley.transporteta.ui.model.data.ResourceState
import javax.inject.Inject

/**
 * Created by joaquimley on 28/03/2018.
 */
class FavoritesViewModelImpl @Inject internal constructor(smsController: SmsController) : FavoritesViewModel(smsController) {

    private val favouritesLiveData = MutableLiveData<Resource<List<FavoriteView>>>()

    init {
        getSms()
    }

    override fun getFavourites(): LiveData<Resource<List<FavoriteView>>> {
        return favouritesLiveData
    }

    override fun retry() {
        // TODO make request to local database for favorites

    }

    override fun onEtaRequested(favourite: FavoriteView) {
        requestEta(favourite.code)
    }


    private fun requestEta(code: Int) {
        favouritesLiveData.postValue(Resource(ResourceState.LOADING))
        smsController.requestEta(code)
    }


    private fun getSms() {
        smsController.observeIncomingSms().subscribe ({
            favouritesLiveData.postValue(Resource(ResourceState.LOADING))


            // TODO: remove mocked up shenenigans Make it more 🎨
            val currentValue = ArrayList<FavoriteView>()
            currentValue.add(FavoriteView(it.code, it.message))

//            val currentValue = favouritesLiveData.value?.data?.toMutableList()
//                    ?: emptyList<FavoriteView>()
//            currentValue.toMutableList().add(FavoriteView(it.code, it.message))
            favouritesLiveData.postValue(Resource(ResourceState.SUCCESS, currentValue))

        }, {favouritesLiveData.postValue(Resource(ResourceState.ERROR, null, it.message)) })
    }

}