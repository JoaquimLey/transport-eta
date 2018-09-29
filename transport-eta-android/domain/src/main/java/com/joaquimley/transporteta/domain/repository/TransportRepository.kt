package com.joaquimley.transporteta.domain.repository

import com.joaquimley.transporteta.domain.model.Transport
import io.reactivex.Completable
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

    fun getTransport(transportId: String): Observable<Transport>

    fun saveTransport(transport: Transport): Completable

    fun saveTransport(transportList: List<Transport>): Completable

    fun deleteTransport(transport: Transport): Completable

    fun deleteTransports(transport: List<Transport>): Completable
}