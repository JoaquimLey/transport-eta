package com.joaquimley.transporteta.sms

import com.joaquimley.transporteta.sms.model.SmsModel
import io.reactivex.Observable
import io.reactivex.Single

interface SmsController {
//    fun observeIncomingSms(): Observable<SmsModel>

    fun requestEta(busStopCode: Int): Single<SmsModel>
//    fun requestEta(busStopCode: Int): Observable<SmsModel>

    fun dispose()
}