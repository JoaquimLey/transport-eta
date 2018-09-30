package com.joaquimley.data

import com.joaquimley.data.mapper.TransportMapper
import com.joaquimley.data.store.TransportDataStore
import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.domain.repository.FavoritesRepository
import io.reactivex.Completable
import io.reactivex.Flowable

class FavoritesRepositoryImpl(private val transportDataStore: TransportDataStore,
                              private val mapper: TransportMapper) : FavoritesRepository {

    override fun markAsFavorite(transport: Transport): Completable {
        return transportDataStore.markAsFavorite(mapper.toEntity(transport))
    }

    override fun removeAsFavorite(transport: Transport): Completable {
        return transportDataStore.removeAsFavorite(mapper.toEntity(transport))
    }

    override fun getAll(): Flowable<List<Transport>> {
        return transportDataStore.getAllFavorites().map { mapper.toModel(it) }
    }

    override fun clearAll(): Completable {
        return transportDataStore.clearAllFavorites()
    }

}