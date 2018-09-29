package com.joaquimley.transporteta.domain.usecase.transport

import com.joaquimley.transporteta.domain.executor.PostExecutionThread
import com.joaquimley.transporteta.domain.executor.ThreadExecutor
import com.joaquimley.transporteta.domain.interactor.transport.GetTransportUseCase
import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.domain.repository.TransportRepository
import com.joaquimley.transporteta.domain.test.factory.TransportFactory
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class GetTransportUseCaseTest {

    private val robot = Robot()

    private val mockThreadExecutor = mock<ThreadExecutor>()
    private val mockPostExecutionThread = mock<PostExecutionThread>()
    private val transportRepository = mock<TransportRepository>()

    private lateinit var getTransportUseCase: GetTransportUseCase

    @Before
    fun setUp() {
        getTransportUseCase = GetTransportUseCase(transportRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        // Assemble
        val stubTransportId = TransportFactory.makeTransport().id
        // Act
        getTransportUseCase.buildUseCaseObservable(stubTransportId)
        // Assert
        verify(transportRepository).getTransport(stubTransportId)
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        // Assemble
        val transport  = robot.stubTransportRepositoryGetTransport(TransportFactory.makeTransport())
        // Act
        val testObserver = getTransportUseCase.buildUseCaseObservable(transport.id).test()
        // Assert
        testObserver.assertComplete()
    }


    @Test
    fun buildUseCaseObservableReturnsData() {
        // Assemble
        val transport = robot.stubTransportRepositoryGetTransport()
        // Act
        val testObserver = getTransportUseCase.buildUseCaseObservable(transport.id).test()
        // Assert
        testObserver.assertValue(transport)
    }

    inner class Robot {
        fun stubTransportRepositoryGetTransport(transport: Transport = TransportFactory.makeTransport()): Transport {
            whenever(transportRepository.getTransport(transport.id)).thenReturn(Observable.just(transport))
            return transport
        }
    }

}