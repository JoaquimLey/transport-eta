package com.joaquimley.transporteta.ui.home.favorite

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.joaquimley.transporteta.sms.SmsController

class FavoritesViewModelFactory(private val smsController: SmsController): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModelImpl::class.java)) {
            return FavoritesViewModelImpl(smsController) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}