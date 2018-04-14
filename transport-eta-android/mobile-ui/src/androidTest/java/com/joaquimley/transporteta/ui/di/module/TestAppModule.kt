package com.joaquimley.transporteta.ui.di.module

import android.app.Application
import android.content.Context
import com.joaquimley.transporteta.sms.SmsBroadcastReceiver
import com.joaquimley.transporteta.sms.SmsBroadcastReceiverImpl
import com.joaquimley.transporteta.sms.SmsController
import com.joaquimley.transporteta.sms.SmsControllerImpl
import com.joaquimley.transporteta.ui.injection.scope.PerApplication
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
    fun provideSmsController(smsBroadcastReceiver: SmsBroadcastReceiver): SmsController {
        return SmsControllerImpl(smsBroadcastReceiver)
    }


    @Provides
    @PerApplication
    internal fun provideSmsBroadcastReceiver(): SmsBroadcastReceiver {
        return SmsBroadcastReceiverImpl("1337", "TEST@CONDITION")
    }
}