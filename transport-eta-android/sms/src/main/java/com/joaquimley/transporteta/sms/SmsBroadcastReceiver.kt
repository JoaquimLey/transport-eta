package com.joaquimley.transporteta.sms

import android.content.BroadcastReceiver
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Singleton

@Singleton
abstract class SmsBroadcastReceiver(val serviceNumber: String, protected val serviceSmsCondition: String)
    : BroadcastReceiver() {

    protected val broadcastServiceSms: PublishSubject<String> = PublishSubject.create()

    fun observeServiceSms(): Observable<String> {
        return broadcastServiceSms
    }
}


