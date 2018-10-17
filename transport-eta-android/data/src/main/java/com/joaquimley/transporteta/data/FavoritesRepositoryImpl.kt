package com.joaquimley.transporteta.data

import com.joaquimley.transporteta.data.mapper.DataTransportMapper
import com.joaquimley.transporteta.data.store.TransportDataStore
import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.domain.repository.FavoritesRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepositoryImpl @Inject constructor(private val transportDataStore: TransportDataStore,
                                                  private val mapper: DataTransportMapper) : FavoritesRepository {

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