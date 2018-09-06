package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.sms.SmsBroadcastReceiver
import com.joaquimley.transporteta.sms.SmsBroadcastReceiverImpl
import com.joaquimley.transporteta.sms.SmsController
import com.joaquimley.transporteta.sms.SmsControllerImpl
import com.joaquimley.transporteta.ui.di.qualifier.SmsServiceInfo
import com.joaquimley.transporteta.ui.di.scope.PerApplication
import dagger.Module
import dagger.Provides

@Module
class SmsControllerModule {

    companion object {
        const val SMS_SERVICE_NUMBER = "3599"
        const val SMS_CONDITION = "SMS@Carris"
    }

    @Provides
    @SmsServiceInfo.ServiceNumber
    internal fun provideServiceNumber(): String {
        return SMS_SERVICE_NUMBER
    }

    @Provides
    @SmsServiceInfo.ServiceBodyCode
    internal fun provideServiceBodyCode(): String {
        return SMS_CONDITION
    }


    @Provides
    @PerApplication
    internal fun provideSmsBroadcastReceiver(@SmsServiceInfo.ServiceNumber serviceNumber: String,
                                             @SmsServiceInfo.ServiceBodyCode serviceSmsCondition: String): SmsBroadcastReceiver {
        return SmsBroadcastReceiverImpl(serviceNumber, serviceSmsCondition)
    }

    @Provides
    @PerApplication
    internal fun provideSmsController(smsBroadcastReceiver: SmsBroadcastReceiver): SmsController {
        return SmsControllerImpl(smsBroadcastReceiver)
    }
}
