package com.joaquimley.transporteta.presentation.home.favorite

import com.joaquimley.transporteta.sms.SmsController

class FavoritesViewModelFactoryImpl(smsController: SmsController) : FavoritesViewModelFactory(smsController) {

    override fun create(smsController: SmsController): FavoritesViewModel {
        return FavoritesViewModelImpl(smsController)
    }
}