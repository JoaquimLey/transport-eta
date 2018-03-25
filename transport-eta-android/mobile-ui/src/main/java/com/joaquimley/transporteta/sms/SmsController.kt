package com.joaquimley.transporteta.sms

import android.telephony.SmsManager
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

const val SMS_CONDITION = "SMS@Carris"
const val SMS_SERVICE_NUMBER = "3599"

fun String.isValidPhoneNumber(phoneNumber: String): Boolean {
    return android.util.Patterns.PHONE.matcher(phoneNumber).matches()
}

class SmsController {

    val serviceSms: PublishSubject<SmsModel> = PublishSubject.create()

    fun observeIncomingSms(): Observable<SmsModel> {
        return serviceSms
    }

    fun requestEta(busStopCode: Int) {
        SmsManager.getDefault().sendTextMessage(SMS_SERVICE_NUMBER, null, "C $busStopCode", null, null)
    }
}


class SmsModel(val message: String)

/**

SMS@Carris
759 ORIENTE
::14:52 [02m]::
793 ROMA-AREEIRO
::14:54 [04m]::
31B V.FORM.
::15:03 [13m]::
759 ORIENTE
::15:18 [28m]::

Para consultar tempos de espera (em minutos), relativamente a todos os veículos que passam numa determinada paragem:
Deverá digitar C (espaço) Código da Paragem e enviar para o 3599 (custo de mensagem escrita normal);
Para consultar tempos de espera dos próximos três veículos de uma carreira, numa determinada paragem:
Deverá digitar C (espaço) Código da Paragem (espaço) Nº da Carreira e enviar para o 3599 (custo de mensagem escrita normal)

 */
