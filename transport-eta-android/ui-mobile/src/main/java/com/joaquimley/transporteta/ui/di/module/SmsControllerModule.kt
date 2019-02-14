package com.joaquimley.transporteta.ui.di.module

import android.telephony.SmsManager
import com.joaquimley.transporteta.sms.*
import com.joaquimley.transporteta.ui.di.qualifier.SmsServiceInfo
import com.joaquimley.transporteta.ui.di.scope.PerApplication
import dagger.Module
import dagger.Provides

@Module
class SmsControllerModule {

    companion object {
        // We should change this depending on flavor or city (currently hardcoded for Lisbon)
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
    internal fun provideSmsSender(): com.joaquimley.transporteta.sms.SmsSender {
        return SmsSender(SmsManager.getDefault())
    }

    @Provides
    @PerApplication
    internal fun provideSmsController(smsBroadcastReceiver: SmsBroadcastReceiver, smsSender: SmsSender): SmsController {
        return SmsControllerImpl(smsBroadcastReceiver, smsSender)
    }
}
