package com.joaquimley.transporteta.presentation.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * Created by joaquimley on 24/03/2018.
 */
abstract class HomeViewModel : ViewModel() {

    protected val selectedScreenLiveData: MutableLiveData<NavigationType> = MutableLiveData()

    abstract fun observeSelectedScreen(): LiveData<NavigationType>

    abstract fun setSelectedScreen(navigationType: NavigationType)

    init {
        setInitialScreen()
    }

    private fun setInitialScreen() {
        // Possibly get from shared preferences, A/B testing etc.?
        selectedScreenLiveData.postValue(NavigationType.FAVOURITES)
    }
}