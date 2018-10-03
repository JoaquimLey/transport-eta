package com.joaquimley.data.store

import com.joaquimley.data.model.TransportEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

interface TransportDataStore {

    fun markAsFavorite(transport: TransportEntity): Completable

    fun removeAsFavorite(transport: TransportEntity): Completable

    fun getAllFavorites(): Flowable<List<TransportEntity>>

    fun clearAllFavorites(): Completable

    fun saveTransport(transport: TransportEntity): Completable

    fun deleteTransport(transport: String): Completable

    fun getTransport(transportId: String): Observable<TransportEntity>

    fun getAll(): Flowable<List<TransportEntity>>


    // TODO Should these be in the [SmSController] instead?  [RequestEtaUseCase]
    fun requestTransportEta(transportCode: Int): Observable<TransportEntity>

    // TODO Should these be in the [SmSController] instead ? [RequestEtaUseCase]
    fun cancelTransportEtaRequest(transportCode: Int?): Completable
}