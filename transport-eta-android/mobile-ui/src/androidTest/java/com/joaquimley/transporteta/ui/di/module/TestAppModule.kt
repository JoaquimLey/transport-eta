package com.joaquimley.transporteta.ui.di.module

import android.app.Application
import android.content.Context
import com.joaquimley.transporteta.ui.injection.scope.PerApplication
import com.joaquimley.transporteta.sms.SmsBroadcastReceiver
import com.joaquimley.transporteta.sms.SmsBroadcastReceiverImpl
import com.joaquimley.transporteta.sms.SmsController
import com.joaquimley.transporteta.sms.SmsControllerImpl
import dagger.Module
import dagger.Provides
import org.mockito.Mockito

@Module
class TestAppModule {

    @Provides
    @PerApplication
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @PerApplication
    fun provideSmsController(): SmsController {
        return Mockito.mock(SmsControllerImpl::class.java)
    }


    @Provides
    @PerApplication
    internal fun provideSmsBroadcastReceiver(): SmsBroadcastReceiver {
        return Mockito.mock(SmsBroadcastReceiverImpl::class.java)
    }

}