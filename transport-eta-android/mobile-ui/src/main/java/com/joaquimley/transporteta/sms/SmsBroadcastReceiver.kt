package com.joaquimley.transporteta.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.joaquimley.transporteta.sms.model.SmsModel
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A broadcast receiver who listens for incoming SMS
 */

@Singleton
class SmsBroadcastReceiver @Inject constructor(val serviceNumber: String, private val serviceSmsCondition: String)
    : BroadcastReceiver() {

    val broadcastServiceSms: PublishSubject<SmsModel> = PublishSubject.create()

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            var smsBody = ""
            var smsSender = ""
            for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                smsSender = smsMessage.displayOriginatingAddress
                smsBody += smsMessage.messageBody
            }

            if (smsSender == serviceNumber && smsBody.startsWith(serviceSmsCondition)) {
                broadcastServiceSms.onNext(SmsModel(smsBody))
            }
        }
    }
}