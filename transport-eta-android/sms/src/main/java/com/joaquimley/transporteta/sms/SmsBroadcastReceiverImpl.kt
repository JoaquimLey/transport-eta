package com.joaquimley.transporteta.sms

import android.content.Context
import android.content.Intent
import android.provider.Telephony
import javax.inject.Inject

/**
 * A broadcast receiver who listens for incoming SMS
 */

class SmsBroadcastReceiverImpl @Inject constructor(serviceNumber: String, serviceSmsCondition: String)
    : SmsBroadcastReceiver(serviceNumber, serviceSmsCondition) {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            var smsBody = ""
            var smsSender = ""
            for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                smsSender = smsMessage.displayOriginatingAddress
                smsBody += smsMessage.messageBody
            }

            if (smsSender == serviceNumber && smsBody.startsWith(serviceSmsCondition)) {
                broadcastServiceSms.onNext(smsBody)
            }
        }
    }
}