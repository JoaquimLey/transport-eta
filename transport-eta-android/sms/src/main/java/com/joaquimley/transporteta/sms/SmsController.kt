package com.joaquimley.transporteta.sms

import com.joaquimley.transporteta.sms.model.SmsModel
import io.reactivex.Observable
import io.reactivex.Single

interface SmsController {

    fun dispose()

    fun invalidateRequest()

    fun requestEta(busStopCode: Int): Single<SmsModel>
}