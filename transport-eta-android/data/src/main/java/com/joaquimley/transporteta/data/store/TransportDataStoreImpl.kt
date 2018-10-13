package com.joaquimley.transporteta.data.store

import com.joaquimley.transporteta.data.model.TransportEntity
import com.joaquimley.transporteta.data.source.FrameworkLocalStorage
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

class TransportDataStoreImpl(private val frameworkLocalStorage: FrameworkLocalStorage): TransportDataStore {

    override fun markAsFavorite(transportEntity: TransportEntity): Completable {
        return Completable.error(NotImplementedError("Won't be ready for v1.0"))
    }

    override fun removeAsFavorite(transportEntity: TransportEntity): Completable {
        return Completable.error(NotImplementedError("Won't be ready for v1.0"))
    }

    override fun getAllFavorites(): Flowable<List<TransportEntity>> {
        return frameworkLocalStorage.getAll().toFlowable()
    }

    override fun clearAllFavorites(): Completable {
        return Completable.error(NotImplementedError("Won't be ready for v1.0"))
    }

    override fun saveTransport(transportEntity: TransportEntity): Completable {
        return frameworkLocalStorage.saveTransport(transportEntity)
    }

    override fun deleteTransport(transportEntityId: String): Completable {
        return frameworkLocalStorage.deleteTransport(transportEntityId)
    }

    override fun getTransport(transportEntityId: String): Observable<TransportEntity> {
        return  frameworkLocalStorage.getTransport(transportEntityId).toObservable()
    }

    override fun getAll(): Flowable<List<TransportEntity>> {
        return frameworkLocalStorage.getAll().toFlowable()
    }

    // TODO Should these be in the [SmSController] instead?  [RequestEtaUseCase]
    override fun requestTransportEta(transportCode: Int): Observable<TransportEntity> {
        return Observable.error<TransportEntity>(NotImplementedError("Should these be in the [SmSController] instead?"))
    }

    // TODO Should these be in the [SmSController] instead?  [RequestEtaUseCase]
    override fun cancelTransportEtaRequest(transportCode: Int?): Completable {
        return Completable.error(NotImplementedError("Should these be in the [SmSController] instead?"))
    }
}