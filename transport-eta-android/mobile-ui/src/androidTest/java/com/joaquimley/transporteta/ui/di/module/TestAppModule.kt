package com.joaquimley.transporteta.ui.di.module

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.joaquimley.transporteta.sms.SmsBroadcastReceiver
import com.joaquimley.transporteta.sms.SmsBroadcastReceiverImpl
import com.joaquimley.transporteta.sms.SmsController
import com.joaquimley.transporteta.sms.SmsControllerImpl
import com.joaquimley.transporteta.ui.injection.scope.PerApplication
import com.joaquimley.transporteta.ui.test.FavoritesViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class TestAppModule {

    @Provides
    @PerApplication
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @PerApplication
    internal fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelProvider.NewInstanceFactory()
    }
}