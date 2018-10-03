package com.joaquimley.transporteta.domain.repository

import com.joaquimley.transporteta.domain.model.Transport
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Interface defining methods for how the data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface TransportRepository {

    fun requestTransportEta(transportCode: Int): Observable<Transport>

    fun cancelTransportEtaRequest(transportCode: Int?): Completable

    fun saveTransport(transport: Transport): Completable

    fun deleteTransport(transportId: String): Completable

    fun getTransport(transportId: String): Observable<Transport>

    fun getAll(): Flowable<List<Transport>>
}