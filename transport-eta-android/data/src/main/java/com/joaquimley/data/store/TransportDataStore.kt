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

    fun getAll(): Flowable<List<TransportEntity>>

    fun getTransport(transportId: String): Observable<TransportEntity>

    fun saveTransport(transport: TransportEntity): Completable

    fun saveTransport(transportList: List<TransportEntity>): Completable

    fun deleteTransport(transport: TransportEntity): Completable

    fun deleteTransport(transport: List<TransportEntity>): Completable

    // TODO Should these be in the [SmSController] instead?  [RequestEtaUseCase]
    fun requestTransportEta(transportCode: Int): Observable<TransportEntity>

    // TODO Should these be in the [SmSController] instead ? [RequestEtaUseCase]
    fun cancelTransportEtaRequest(transportCode: Int?): Completable
}