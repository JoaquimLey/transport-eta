package com.joaquimley.transporteta.domain.usecase.favorites

import com.joaquimley.transporteta.domain.executor.PostExecutionThread
import com.joaquimley.transporteta.domain.executor.ThreadExecutor
import com.joaquimley.transporteta.domain.interactor.favorites.MarkTransportAsNoFavoriteUseCase
import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.domain.repository.FavoritesRepository
import com.joaquimley.transporteta.domain.test.factory.TransportFactory
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test

class MarkAsNotFavoriteTest {

    private val mockThreadExecutor = mock<ThreadExecutor>()
    private val mockPostExecutionThread = mock<PostExecutionThread>()
    private val favoritesRepository = mock<FavoritesRepository>()

    private lateinit var markTransportAsNoFavoriteUseCase: MarkTransportAsNoFavoriteUseCase

    @Before
    fun setUp() {
        markTransportAsNoFavoriteUseCase = MarkTransportAsNoFavoriteUseCase(favoritesRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        val transport = TransportFactory.makeTransport()
        markTransportAsNoFavoriteUseCase.buildUseCaseObservable(transport)
        verify(favoritesRepository).removeAsFavorite(transport)
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        val transport = TransportFactory.makeTransport()
        stubTransportRepositoryMarkAsFavorite(transport, Completable.complete())
        val testObserver = markTransportAsNoFavoriteUseCase.buildUseCaseObservable(transport).test()
        testObserver.assertComplete()
    }

    private fun stubTransportRepositoryMarkAsFavorite(transport: Transport, completable: Completable) {
        whenever(favoritesRepository.removeAsFavorite(transport)).thenReturn(completable)
    }

}