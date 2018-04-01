package com.joaquimley.transporteta.home.favorite

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.joaquimley.transporteta.sms.SmsController

class FavoritesViewModelFactory(private val smsController: SmsController): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(smsController) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}