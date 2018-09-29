package com.joaquimley.transporteta.domain.usecase.transport.favorites

import com.joaquimley.transporteta.domain.executor.PostExecutionThread
import com.joaquimley.transporteta.domain.executor.ThreadExecutor
import com.joaquimley.transporteta.domain.interactor.transport.favorites.MarkTransportAsFavoriteUseCase
import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.domain.repository.FavoritesRepository
import com.joaquimley.transporteta.domain.test.factory.TransportFactory
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test

class MarkAsFavoriteUseCaseTest {

    private val robot = Robot()

    private val mockThreadExecutor = mock<ThreadExecutor>()
    private val mockPostExecutionThread = mock<PostExecutionThread>()
    private val favoritesRepository = mock<FavoritesRepository>()

    private lateinit var markTransportAsFavoriteUseCase: MarkTransportAsFavoriteUseCase

    @Before
    fun setUp() {
        markTransportAsFavoriteUseCase = MarkTransportAsFavoriteUseCase(favoritesRepository,
                mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        // Assemble
        val transport = TransportFactory.makeTransport()
        // Act
        markTransportAsFavoriteUseCase.buildUseCaseObservable(transport)
        // Assert
        verify(favoritesRepository).markAsFavorite(transport)
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        // Assemble
        val transport = robot.stubTransportRepositoryMarkAsFavorite()
        // Act
        val testObserver = markTransportAsFavoriteUseCase.buildUseCaseObservable(transport).test()
        // Assert
        testObserver.assertComplete()
    }

    inner class Robot {

        fun stubTransportRepositoryMarkAsFavorite(transport: Transport = TransportFactory.makeTransport(),
                                                  completable: Completable = Completable.complete()): Transport {
            whenever(favoritesRepository.markAsFavorite(transport)).thenReturn(completable)
            return transport
        }
    }
}