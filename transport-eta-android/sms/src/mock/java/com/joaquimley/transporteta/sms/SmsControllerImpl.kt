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
class SmsControllerImpl @Inject constructor(private val smsBroadcastReceiver: SmsBroadcastReceiver) : SmsController {

    private val serviceSms: PublishSubject<SmsModel> = PublishSubject.create()
    private var busStopCode: Int = 0

    private val disposable: Disposable?

    init {
        // TODO: There might be an issue with race condition (busStopCode)
        // TODO create a queue of requests and handle each synchronously
        disposable = smsBroadcastReceiver.observeServiceSms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ serviceSms.onNext(SmsModel(busStopCode, it)) },
                        { Log.e("SmsController", "Failed smsBroadcastReceiver.observeServiceSms(): ${it.message}")})
    }

    override fun observeIncomingSms(): Observable<SmsModel> {
        return serviceSms
    }

    override fun requestEta(busStopCode: Int) {
        this.busStopCode = busStopCode
        SmsManager.getDefault().sendTextMessage(smsBroadcastReceiver.serviceNumber, null, "C $busStopCode", null, null)
        Log.d("SmsController", "requestEta $busStopCode")
    }

    override fun dispose() {
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
