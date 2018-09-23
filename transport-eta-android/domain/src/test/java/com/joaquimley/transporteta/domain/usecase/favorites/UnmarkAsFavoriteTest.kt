package com.joaquimley.transporteta.domain.usecase.favorites

import com.joaquimley.transporteta.domain.executor.PostExecutionThread
import com.joaquimley.transporteta.domain.executor.ThreadExecutor
import com.joaquimley.transporteta.domain.interactor.favorites.UnmarkTransportAsFavoriteUseCase
import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.domain.repository.FavoritesRepository
import com.joaquimley.transporteta.domain.test.factory.TransportFactory
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test

class UnmarkAsFavoriteTest {

    private lateinit var unmarkTransportAsFavoriteUseCase: UnmarkTransportAsFavoriteUseCase

    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread
    private lateinit var favoritesRepository: FavoritesRepository

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        favoritesRepository = mock()
        unmarkTransportAsFavoriteUseCase = UnmarkTransportAsFavoriteUseCase(favoritesRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        val transport = TransportFactory.makeTransport()
        unmarkTransportAsFavoriteUseCase.buildUseCaseObservable(transport)
        verify(favoritesRepository).removeAsFavorite(transport)
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        val transport = TransportFactory.makeTransport()
        stubTransportRepositoryMarkAsFavorite(transport, Completable.complete())
        val testObserver = unmarkTransportAsFavoriteUseCase.buildUseCaseObservable(transport).test()
        testObserver.assertComplete()
    }

    private fun stubTransportRepositoryMarkAsFavorite(transport: Transport, completable: Completable) {
        whenever(favoritesRepository.removeAsFavorite(transport)).thenReturn(completable)
    }

}