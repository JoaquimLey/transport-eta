package com.joaquimley.transporteta.presentation.home

import androidx.lifecycle.LiveData

/**
 * Created by joaquimley on 24/03/2018.
 */
class HomeViewModelImpl : HomeViewModel() {

    override fun observeSelectedScreen(): LiveData<NavigationType> {
        return selectedScreenLiveData
    }

    override fun setSelectedScreen(navigationType: NavigationType) {
        if (selectedScreenLiveData.value != navigationType) {
            // TODO Track -> e.g. navigationType.name clicked
            selectedScreenLiveData.postValue(navigationType)
        }
    }
}