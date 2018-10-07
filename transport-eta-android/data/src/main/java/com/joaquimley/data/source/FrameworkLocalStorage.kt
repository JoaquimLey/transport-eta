package com.joaquimley.data.source

import com.joaquimley.data.model.TransportEntity
import io.reactivex.Completable
import io.reactivex.Single

interface FrameworkLocalStorage {

    fun saveTransport(transportEntity: TransportEntity): Completable

    fun deleteTransport(transportEntityId: String): Completable

    fun getTransport(transportEntityId: String): Single<TransportEntity>

    fun getAll(): Single<List<TransportEntity>>

    fun clearAll(): Completable
}