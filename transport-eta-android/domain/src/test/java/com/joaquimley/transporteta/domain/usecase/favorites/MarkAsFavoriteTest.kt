package com.joaquimley.transporteta.domain.usecase.favorites

import com.joaquimley.transporteta.domain.executor.PostExecutionThread
import com.joaquimley.transporteta.domain.executor.ThreadExecutor
import com.joaquimley.transporteta.domain.interactor.favorites.MarkTransportAsFavoriteUseCase
import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.domain.repository.FavoritesRepository
import com.joaquimley.transporteta.domain.test.factory.TransportFactory
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test

class MarkAsFavoriteTest {

    private val mockThreadExecutor = mock<ThreadExecutor>()
    private val mockPostExecutionThread = mock<PostExecutionThread>()
    private val favoritesRepository = mock<FavoritesRepository>()

    private lateinit var markTransportAsFavoriteUseCase: MarkTransportAsFavoriteUseCase

    @Before
    fun setUp() {
        markTransportAsFavoriteUseCase = MarkTransportAsFavoriteUseCase(favoritesRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        val transport = TransportFactory.makeTransport()
        markTransportAsFavoriteUseCase.buildUseCaseObservable(transport)
        verify(favoritesRepository).markAsFavorite(transport)
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        val transport = TransportFactory.makeTransport()
        stubTransportRepositoryMarkAsFavorite(transport, Completable.complete())
        val testObserver = markTransportAsFavoriteUseCase.buildUseCaseObservable(transport).test()
        testObserver.assertComplete()
    }

    private fun stubTransportRepositoryMarkAsFavorite(transport: Transport, completable: Completable) {
        whenever(favoritesRepository.markAsFavorite(transport)).thenReturn(completable)
    }

}