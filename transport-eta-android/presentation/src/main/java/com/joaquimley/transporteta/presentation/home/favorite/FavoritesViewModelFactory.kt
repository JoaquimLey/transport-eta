package com.joaquimley.transporteta.presentation.home.favorite

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.joaquimley.transporteta.sms.SmsController

abstract class FavoritesViewModelFactory(private val smsController: SmsController) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass is FavoritesViewModel) {
            return create() as T
        }
        throw IllegalStateException("Wrong class type passed ${modelClass.name}")
    }

    fun create(): FavoritesViewModel {
        return create(smsController)
    }

    abstract fun create(smsController: SmsController): FavoritesViewModel
}