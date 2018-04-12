package com.joaquimley.transporteta.ui.test

import com.joaquimley.transporteta.ui.sms.SmsBroadcastReceiver
import com.joaquimley.transporteta.ui.sms.SmsController
import com.joaquimley.transporteta.ui.sms.model.SmsModel
import com.joaquimley.transporteta.ui.test.factory.ui.DataFactory
import io.reactivex.Observable
import javax.inject.Inject

class SmsControllerMock @Inject constructor(private val smsBroadcastReceiver: SmsBroadcastReceiver) : SmsController {
    override fun dispose() {
    }

    override fun observeIncomingSms(): Observable<SmsModel> {
        SmsModel()
    }

    override fun requestEta(busStopCode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}