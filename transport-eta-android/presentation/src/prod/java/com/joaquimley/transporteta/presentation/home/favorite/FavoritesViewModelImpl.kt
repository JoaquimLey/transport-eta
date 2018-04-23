package com.joaquimley.transporteta.presentation.home.favorite

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.joaquimley.transporteta.presentation.data.Resource
import com.joaquimley.transporteta.presentation.model.FavoriteView
import com.joaquimley.transporteta.sms.SmsController
import com.joaquimley.transporteta.ui.model.data.ResourceState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * Created by joaquimley on 28/03/2018.
 */
class FavoritesViewModelImpl @Inject constructor(smsController: SmsController) : FavoritesViewModel(smsController) {

    private val favouritesLiveData = MutableLiveData<Resource<List<FavoriteView>>>()

    init {
        val currentValue = ArrayList<FavoriteView>()
        currentValue.add(FavoriteView(1337, "This is a test"))

        favouritesLiveData.postValue(Resource(ResourceState.SUCCESS, currentValue))
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
        val dis = smsController.requestEta(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    // TODO Mapper from SmsModel to FavoriteViewObject
                    val theObject = FavoriteView(it.code, it.message)

                    val currentValue: MutableList<FavoriteView> = favouritesLiveData.value?.data?.toMutableList()
                            ?: mutableListOf()

                    var index = -1
                    for (view in currentValue.withIndex()) {
                        if (view.value.code == theObject.code) {
                            index = view.index
                            break
                        }
                    }
                    if (index != -1) {
                        currentValue[index] = theObject
                    } else {
                        currentValue.add(theObject)
                    }
                    // TODO (possible caching this to local storage at this point)

                    Log.e("FavoritesViewModelImpl", "Success received with $it")

                    favouritesLiveData.postValue(Resource(ResourceState.SUCCESS, currentValue))
                }, { favouritesLiveData.postValue(Resource(ResourceState.ERROR, null, it.message)) })
    }
}