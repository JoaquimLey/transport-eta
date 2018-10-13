package com.joaquimley.transporteta.sharedpreferences

import com.joaquimley.transporteta.data.model.TransportEntity
import com.joaquimley.transporteta.data.source.FrameworkLocalStorage
import io.reactivex.Completable
import io.reactivex.Single

class FrameworkLocalStorageImpl: FrameworkLocalStorage {
    override fun saveTransport(transportEntity: TransportEntity): Completable {
        return Completable.complete()
    }

    override fun deleteTransport(transportEntityId: String): Completable {
        return Completable.complete()
    }

    override fun getTransport(transportEntityId: String): Single<TransportEntity> {
        return Single.just(TransportEntity("hi", "mock",2, "el", true,"bus"))
    }

    override fun getAll(): Single<List<TransportEntity>> {
        val list = mutableListOf<TransportEntity>()
        list.add(TransportEntity("hi", "mock",2, "latestEta 12324", true,"bus"))
        list.add(TransportEntity("there", "mock",23, "latestEta 123", true,"bus"))
        return Single.just(list)
    }

    override fun clearAll(): Completable {
        return Completable.complete()
    }
}