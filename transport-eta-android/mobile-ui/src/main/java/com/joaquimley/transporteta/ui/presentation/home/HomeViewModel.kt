package com.joaquimley.transporteta.ui.presentation.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.joaquimley.transporteta.ui.presentation.NavigationType

/**
 * Created by joaquimley on 24/03/2018.
 */
class HomeViewModel: ViewModel() {

    private val selectedScreenLiveData: MutableLiveData<NavigationType> = MutableLiveData()

    init {
        setInitialScreen()
    }

    fun observeSelectedScreen(): LiveData<NavigationType> {
        return selectedScreenLiveData
    }

    fun setSelectedScreen(navigationType: NavigationType) {
        if (selectedScreenLiveData.value != navigationType) {
            // TODO Track -> e.g. navigationType.name clicked
            selectedScreenLiveData.postValue(navigationType)
        }
    }

    private fun setInitialScreen() {
        // Possibly get from shared preferences, A/B testing etc.?
        selectedScreenLiveData.postValue(NavigationType.FAVOURITES)
    }

}