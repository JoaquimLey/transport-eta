package com.joaquimley.transporteta.sms

import android.telephony.SmsManager
import android.util.Log
import com.joaquimley.transporteta.sms.model.SmsModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SmsController @Inject constructor(private val smsBroadcastReceiver: SmsBroadcastReceiver) {

    val serviceSms: PublishSubject<SmsModel> = PublishSubject.create()

    var busStopCode: Int = 0
    private val disposable: Disposable?

    init {
        // TODO: There might be an issue with race condition (busStopCode)
        disposable = smsBroadcastReceiver.broadcastServiceSms
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { serviceSms.onNext(SmsModel(busStopCode, it)) }
    }

    fun observeIncomingSms(): Observable<SmsModel> {
        return serviceSms
    }

    fun requestEta(busStopCode: Int) {
        this.busStopCode = busStopCode
        SmsManager.getDefault().sendTextMessage(smsBroadcastReceiver.serviceNumber, null, "C $busStopCode", null, null)
        Log.e("SmsController", "requestEta $busStopCode")
    }

    fun dispose() {
        disposable?.dispose()
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
