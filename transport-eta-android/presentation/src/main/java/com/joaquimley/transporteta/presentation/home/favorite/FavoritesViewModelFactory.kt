package com.joaquimley.transporteta.presentation.home.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.joaquimley.transporteta.sms.SmsController

abstract class FavoritesViewModelFactory(private val smsController: SmsController) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return create() as T
    }

    fun create(): FavoritesViewModel {
        return create(smsController)
    }

    abstract fun create(smsController: SmsController): FavoritesViewModel
}