package com.joaquimley.transporteta.domain.repository

import com.joaquimley.transporteta.domain.model.Transport
import io.reactivex.Completable
import io.reactivex.Flowable

class MockFavoritesRepository: FavoritesRepository {
    override fun markAsFavorite(favorite: Transport): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeAsFavorite(transport: Transport): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): Flowable<List<Transport>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearFavorites(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}