package com.joaquimley.transporteta.domain.repository

import com.joaquimley.transporteta.domain.model.Transport
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Interface defining methods for how the data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface FavoritesRepository {

    fun markAsFavorite(transport: Transport): Completable

    fun removeAsFavorite(transport: Transport): Completable

    fun getAll(): Flowable<List<Transport>>

    fun clearAll(): Completable
}