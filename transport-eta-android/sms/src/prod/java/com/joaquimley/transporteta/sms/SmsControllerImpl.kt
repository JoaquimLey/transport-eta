package com.joaquimley.transporteta.sms

import android.telephony.SmsManager
import android.util.Log
import com.joaquimley.transporteta.sms.model.SmsModel
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SmsControllerImpl @Inject constructor(private val smsBroadcastReceiver: SmsBroadcastReceiver) : SmsController {

    private var broadcastReceiverDisposable: Disposable? = null
    private var smsRequestDisposable: Disposable? = null
    private val smsPublishSubject: PublishSubject<SmsModel> = PublishSubject.create()

    private var busStopCode: Int? = null

    init {
        observeToSmsBroadcastReceiverEvents()
    }

    override fun invalidateRequest() {
        this.busStopCode = null
        smsRequestDisposable?.dispose()
    }

    override fun requestEta(busStopCode: Int): Single<SmsModel> {
        if (this.busStopCode != null) {
            Log.e("SmsController", "requestEta on $busStopCode hasn't finished")
            return Single.error((Throwable("Request in progress")))
        }

        this.busStopCode = busStopCode
        SmsManager.getDefault().sendTextMessage(smsBroadcastReceiver.serviceNumber, null, "C $busStopCode", null, null)
        return Single.create<SmsModel> { emitter ->
            smsRequestDisposable = smsPublishSubject.subscribe({ sms ->
                emitter.onSuccess(sms)
                this.busStopCode = null
            }, { emitter.onError(it) })
        }.doAfterTerminate { smsRequestDisposable?.dispose() }
    }

    private fun observeToSmsBroadcastReceiverEvents() {
        broadcastReceiverDisposable = smsBroadcastReceiver.observeServiceSms()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    smsPublishSubject.onNext(SmsModel(busStopCode ?: -1, it))
                }, { Log.e("SmsController", "Failed smsBroadcastReceiver.observeServiceSms(): ${it.message}") })
    }

    override fun dispose() {
        broadcastReceiverDisposable?.dispose()
        smsRequestDisposable?.dispose()
    }
}


/**

---------------------------------
Send SMS to 3599 with the format:
---------------------------------

C (SPACE) Bus_stop_code
c
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
