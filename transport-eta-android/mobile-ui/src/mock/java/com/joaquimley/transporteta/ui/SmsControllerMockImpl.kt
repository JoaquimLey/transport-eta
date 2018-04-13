package com.joaquimley.transporteta.ui

import com.joaquimley.transporteta.sms.SmsBroadcastReceiver
import com.joaquimley.transporteta.sms.SmsControllerImpl
import com.joaquimley.transporteta.sms.model.SmsModel
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class SmsControllerMockImpl @Inject constructor(smsBroadcastReceiver: SmsBroadcastReceiver) : SmsControllerImpl(smsBroadcastReceiver) {
    override fun dispose() {
    }

    override fun observeIncomingSms(): Observable<SmsModel> {
        return Observable.just(SmsModel(Random().nextInt(), UUID.randomUUID().toString()),
                SmsModel(Random().nextInt(), UUID.randomUUID().toString()),
                SmsModel(Random().nextInt(), UUID.randomUUID().toString()),
                SmsModel(Random().nextInt(), UUID.randomUUID().toString()))
    }

    override fun requestEta(busStopCode: Int) {

    }

}