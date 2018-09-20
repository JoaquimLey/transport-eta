package com.joaquimley.transporteta.domain.repository

import com.joaquimley.transporteta.domain.model.Transport
import io.reactivex.Completable

/**
 * Interface defining methods for how the data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface TransportRepository {

    fun getTransport(transportId: String)

    fun saveTransport(favorite: Transport): Completable

    fun saveTransports(favoriteList: List<Transport>): Completable

    fun deleteTransport(transport: Transport): Completable

    fun deleteTransports(transport: List<Transport>): Completable
}