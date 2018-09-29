package com.joaquimley.transporteta.domain.usecase.transport

import com.joaquimley.transporteta.domain.executor.PostExecutionThread
import com.joaquimley.transporteta.domain.executor.ThreadExecutor
import com.joaquimley.transporteta.domain.interactor.transport.RequestEtaUseCase
import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.domain.repository.TransportRepository
import com.joaquimley.transporteta.domain.test.factory.TransportFactory
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class RequestEtaUseCaseTest {

    private val robot = Robot()

    private val mockThreadExecutor = mock<ThreadExecutor>()
    private val mockPostExecutionThread = mock<PostExecutionThread>()
    private val transportRepository = mock<TransportRepository>()

    private lateinit var requestEtaUseCase: RequestEtaUseCase

    @Before
    fun setUp() {
        requestEtaUseCase = RequestEtaUseCase(transportRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        // Assemble
        val stubTransportCode = TransportFactory.makeTransport().code
        // Act
        requestEtaUseCase.buildUseCaseObservable(stubTransportCode)
        // Assert
        verify(transportRepository).requestTransportEta(stubTransportCode)
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        // Assemble
        val transport  = robot.stubRequestTransportEta(TransportFactory.makeTransport())
        // Act
        val testObserver = requestEtaUseCase.buildUseCaseObservable(transport.code).test()
        // Assert
        testObserver.assertComplete()
    }


    @Test
    fun buildUseCaseObservableReturnsData() {
        // Assemble
        val transport = robot.stubRequestTransportEta()
        // Act
        val testObserver = requestEtaUseCase.buildUseCaseObservable(transport.code).test()
        // Assert
        testObserver.assertValue(transport)
    }

    inner class Robot {
        fun stubRequestTransportEta(transport: Transport = TransportFactory.makeTransport()): Transport {
            whenever(transportRepository.requestTransportEta(transport.code)).thenReturn(Observable.just(transport))
            return transport
        }
    }

}