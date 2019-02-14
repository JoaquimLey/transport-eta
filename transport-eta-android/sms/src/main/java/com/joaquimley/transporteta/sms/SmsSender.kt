package com.joaquimley.transporteta.sms

import android.telephony.SmsManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SmsSender @Inject constructor(private val androidSmsManager: SmsManager) {

    // TODO Improve with intents: https://github.com/JoaquimLey/transport-eta/issues/87    
    fun send(serviceNumber: String, busStopCode: Int) {
        androidSmsManager.sendTextMessage(serviceNumber, null, "C $busStopCode", null, null)

    }
}