package com.joaquimley.transporteta.sms

import android.telephony.SmsManager
import android.util.Log
import com.joaquimley.transporteta.sms.model.SmsModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class SmsControllerImpl @Inject constructor(private val smsBroadcastReceiver: SmsBroadcastReceiver) : SmsController {

    init {

    }

    override fun dispose() {

    }

    override fun invalidateRequest() {

    }

    override fun requestEta(busStopCode: Int): Single<SmsModel> {
        return Single.just(SmsModel(busStopCode, "MockData"))
    }
}


/**

---------------------------------
Send SMS to 3599 with the format:
---------------------------------

C (SPACE) Bus_stop_code

---------------------
Sms response example:
---------------------

SMS@Carris
759 ORIENTE
::14:52 [02m]::
793 ROMA-AREEIRO
::14:54 [04m]::
31B V.FORM.
::15:03 [13m]::
759 ORIENTE
::15:18 [28m]::

----------------

Para consultar tempos de espera (em minutos), relativamente a todos os veículos que passam numa determinada paragem:
Deverá digitar C (espaço) Código da Paragem e enviar para o 3599 (custo de mensagem escrita normal);

Para consultar tempos de espera dos próximos três veículos de uma carreira, numa determinada paragem:
Deverá digitar C (espaço) Código da Paragem (espaço) Nº da Carreira e enviar para o 3599 (custo de mensagem escrita normal)

 */
