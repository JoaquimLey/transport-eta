package com.joaquimley.transporteta.home.favorite

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.joaquimley.transporteta.sms.SmsController

class FavouritesViewModelFactory(val smsController: SmsController): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouritesViewModel::class.java)) {
            return FavouritesViewModel(smsController) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}