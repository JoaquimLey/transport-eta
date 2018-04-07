package com.joaquimley.transporteta.sms

import com.joaquimley.transporteta.sms.model.SmsModel
import io.reactivex.Observable

interface SmsController {
    fun observeIncomingSms(): Observable<SmsModel>

    fun requestEta(busStopCode: Int)

    fun dispose()
}