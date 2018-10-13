package com.joaquimley.transporteta.data.store

import com.joaquimley.transporteta.data.model.TransportEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

interface TransportDataStore {

    fun markAsFavorite(transportEntity: TransportEntity): Completable

    fun removeAsFavorite(transportEntity: TransportEntity): Completable

    fun getAllFavorites(): Flowable<List<TransportEntity>>

    fun clearAllFavorites(): Completable

    fun saveTransport(transportEntity: TransportEntity): Completable

    fun deleteTransport(transportEntityId: String): Completable

    fun getTransport(transportEntityId: String): Observable<TransportEntity>

    fun getAll(): Flowable<List<TransportEntity>>


    // TODO Should these be in the [SmSController] instead?  [RequestEtaUseCase]
    fun requestTransportEta(transportCode: Int): Observable<TransportEntity>

    // TODO Should these be in the [SmSController] instead ? [RequestEtaUseCase]
    fun cancelTransportEtaRequest(transportCode: Int?): Completable
}