package com.joaquimley.transporteta.data.source

import com.joaquimley.transporteta.data.model.TransportEntity
import io.reactivex.Completable
import io.reactivex.Single

interface FrameworkLocalStorage {

    fun saveTransport(transportEntity: TransportEntity): Completable

    fun deleteTransport(transportEntityId: String): Completable

    fun getTransport(transportEntityId: String): Single<TransportEntity>

    fun getAll(): Single<List<TransportEntity>>

    fun clearAll(): Completable
}