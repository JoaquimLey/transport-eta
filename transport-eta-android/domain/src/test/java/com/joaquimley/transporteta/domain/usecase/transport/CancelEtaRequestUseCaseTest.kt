package com.joaquimley.transporteta.domain.usecase.transport

import com.joaquimley.transporteta.domain.executor.PostExecutionThread
import com.joaquimley.transporteta.domain.executor.ThreadExecutor
import com.joaquimley.transporteta.domain.interactor.transport.CancelEtaRequestUseCase
import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.domain.repository.TransportRepository
import com.joaquimley.transporteta.domain.test.factory.TransportFactory
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test

class CancelEtaRequestUseCaseTest {

    private val robot = Robot()

    private val mockThreadExecutor = mock<ThreadExecutor>()
    private val mockPostExecutionThread = mock<PostExecutionThread>()
    private val mockTransportRepository = mock<TransportRepository>()

    private lateinit var cancelEtaRequestUseCase: CancelEtaRequestUseCase

    @Before
    fun setUp() {
        cancelEtaRequestUseCase = CancelEtaRequestUseCase(mockTransportRepository,
                mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        // Assemble
        val transport = TransportFactory.makeTransport()
        // Act
        cancelEtaRequestUseCase.buildUseCaseObservable(transport.code)
        // Assert
        verify(mockTransportRepository).cancelTransportEtaRequest(transport.code)
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        // Assemble
        val transport = robot.stubTransportRepositoryMarkAsFavorite()
        // Act
        val testObserver = cancelEtaRequestUseCase.buildUseCaseObservable(transport.code).test()
        // Assert
        testObserver.assertComplete()
    }

    inner class Robot {

        fun stubTransportRepositoryMarkAsFavorite(transport: Transport = TransportFactory.makeTransport(),
                                                  completable: Completable = Completable.complete()): Transport {
            whenever(mockTransportRepository.cancelTransportEtaRequest(transport.code)).thenReturn(completable)
            return transport
        }
    }
}