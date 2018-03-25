package com.joaquimley.transporteta

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.provider.Telephony
import android.widget.Toast
import com.joaquimley.transporteta.sms.SMS_CONDITION
import com.joaquimley.transporteta.sms.SMS_SERVICE_NUMBER
import com.joaquimley.transporteta.sms.SmsController
import com.joaquimley.transporteta.sms.SmsModel


/**
 * Created by joaquimley on 25/03/2018.
 */
class App : Application() {

    val smsController = SmsController()

    private val mMessageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
                var smsBody = ""
                var smsSender = ""
                for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    smsSender = smsMessage.displayOriginatingAddress
                    smsBody += smsMessage.messageBody
                }

                if (smsSender == SMS_SERVICE_NUMBER && smsBody.startsWith(SMS_CONDITION)) {
                    smsController.serviceSms.onNext(SmsModel(smsBody))
//                    Toast.makeText(context, "BroadcastReceiver caught conditional SMS: " + smsBody, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        registerReceiver(mMessageReceiver, IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))
    }

    companion object {
        lateinit var instance: App
    }
}