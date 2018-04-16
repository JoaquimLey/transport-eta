//package com.joaquimley.transporteta.ui.test
//
//import android.arch.lifecycle.ViewModel
//import android.arch.lifecycle.ViewModelProvider
//import com.joaquimley.transporteta.sms.SmsController
//
//
//class FavoritesViewModelFactory(private val smsController: SmsController): ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(MockFavoritesViewModel::class.java)) {
//            return MockFavoritesViewModel(smsController) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}