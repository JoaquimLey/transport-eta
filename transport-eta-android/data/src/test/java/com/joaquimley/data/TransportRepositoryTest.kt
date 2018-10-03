package com.joaquimley.data

import com.joaquimley.data.factory.DataFactory
import com.joaquimley.data.factory.TransportFactory
import com.joaquimley.data.mapper.TransportMapper
import com.joaquimley.data.model.TransportEntity
import com.joaquimley.data.store.TransportDataStore
import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.domain.repository.TransportRepository
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test

class TransportRepositoryTest {

    private val robot = Robot()

    private val mockTransportDataStore = mock<TransportDataStore>()
    private val mockMapper = mock<TransportMapper>()

    private lateinit var transportRepository: TransportRepository

    @Before
    fun setUp() {
        transportRepository = TransportRepositoryImpl(mockTransportDataStore, mockMapper)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun requestTransportEtaCompletes() {
        // Assemble
        val stubCode = 13
        val transport = TransportFactory.makeTransport(code = stubCode)
        val transportEntity = TransportFactory.makeTransportEntity(code = stubCode)
        robot.stubTransportMapperToModel(transportEntity, transport)
        robot.stubDataStoreRequestTransportEtaSuccess(transportEntity)
        // Act
        val testObserver = transportRepository.requestTransportEta(stubCode).test()
        // Assert
        testObserver.assertComplete()
    }

    @Test
    fun requestTransportEtaCallsCorrectDataStoreMethod() {
        // Assemble
        val stubCode = 13
        val transport = TransportFactory.makeTransport(code = stubCode)
        val transportEntity = TransportFactory.makeTransportEntity(code = stubCode)
        robot.stubTransportMapperToModel(transportEntity, transport)
        robot.stubDataStoreRequestTransportEtaSuccess(transportEntity)
        // Act
        transportRepository.requestTransportEta(stubCode)
        // Assert
        verify(mockTransportDataStore, times(1)).requestTransportEta(stubCode)
    }

    @Test
    fun requestTransportEtaReturnsCorrectData() {
        // Assemble
        val stubCode = 13
        val transport = TransportFactory.makeTransport(code = stubCode)
        val transportEntity = TransportFactory.makeTransportEntity(code = stubCode)
        robot.stubTransportMapperToModel(transportEntity, transport)
        robot.stubDataStoreRequestTransportEtaSuccess(transportEntity)
        // Act
        val returnedData = transportRepository.requestTransportEta(stubCode).test()
        // Assert
        returnedData.assertResult(transport)
    }

    @Test
    fun requestTransportEtaFailsThrowsException() {
        // Assemble
        val stubCode = 13
        val transport = TransportFactory.makeTransport(code = stubCode)
        val transportEntity = TransportFactory.makeTransportEntity(code = stubCode)
        robot.stubTransportMapperToModel(transportEntity, transport)

        val errorMessage = "Testing request eta fails"
        robot.stubDataStoreRequestTransportEtaFailure(errorMessage)
        // Act
        val testObservable = transportRepository.requestTransportEta(stubCode).test()
        // Assert
        testObservable.assertFailureAndMessage(Throwable::class.java, errorMessage)
    }

    @Test
    fun cancelTransportEtaRequestCompletes() {
        // Assemble
        val stubCode = 13
        val transport = TransportFactory.makeTransport(code = stubCode)
        val transportEntity = TransportFactory.makeTransportEntity(code = stubCode)
        robot.stubTransportMapperToEntity(transport, transportEntity)
        robot.stubDataStoreCancelRequestTransportEtaSuccess(transportEntity)
        // Act
        val testObserver = transportRepository.cancelTransportEtaRequest(stubCode).test()
        // Assert
        testObserver.assertComplete()

    }

    @Test
    fun cancelTransportEtaRequestCallsCorrectDataStoreMethod() {
        // Assemble
        val stubCode = 13
        val transport = TransportFactory.makeTransport(code = stubCode)
        val transportEntity = TransportFactory.makeTransportEntity(code = stubCode)
        robot.stubTransportMapperToModel(transportEntity, transport)
        robot.stubDataStoreCancelRequestTransportEtaSuccess(transportEntity)
        // Act
        transportRepository.cancelTransportEtaRequest(stubCode)
        // Assert
        verify(mockTransportDataStore, times(1)).cancelTransportEtaRequest(stubCode)

    }

    @Test
    fun cancelTransportEtaRequestFailsThrowsException() {
        // Assemble
        val stubCode = 13
        val transport = TransportFactory.makeTransport(code = stubCode)
        val transportEntity = TransportFactory.makeTransportEntity(code = stubCode)
        robot.stubTransportMapperToModel(transportEntity, transport)

        val errorMessage = "Testing cancelEtaFailed"
        robot.stubDataStoreCancelRequestTransportEtaFailure(errorMessage)
        // Act
        val testObserver = transportRepository.cancelTransportEtaRequest(stubCode).test()
        // Assert
        testObserver.assertFailureAndMessage(Throwable::class.java, errorMessage)
    }

    @Test
    fun getTransportCompletes() {
        // Assemble
        val stubTransportId = DataFactory.randomUuid()
        val transport = TransportFactory.makeTransport(id = stubTransportId)
        val transportEntity = TransportFactory.makeTransportEntity(id = stubTransportId)
        robot.stubTransportMapperToModel(transportEntity, transport)

        robot.stubDataStoreGetTransportSuccess(stubTransportId, transportEntity)
        // Act
        val testObservable = transportRepository.getTransport(stubTransportId).test()
        // Assert
        testObservable.assertComplete()
    }

    @Test
    fun getTransportCallsCorrectDataStoreMethod() {
        // Assemble
        val stubTransportId = DataFactory.randomUuid()
        val transport = TransportFactory.makeTransport(id = stubTransportId)
        val transportEntity = TransportFactory.makeTransportEntity(id = stubTransportId)
        robot.stubTransportMapperToModel(transportEntity, transport)

        robot.stubDataStoreGetTransportSuccess(stubTransportId, transportEntity)
        // Act
        transportRepository.getTransport(stubTransportId).test()
        // Assert
        verify(mockTransportDataStore, times(1)).getTransport(stubTransportId)
    }

    @Test
    fun getTransportReturnsCorrectData() {
        // Assemble
        val stubTransportId = DataFactory.randomUuid()
        val transport = TransportFactory.makeTransport(id = stubTransportId)
        val transportEntity = TransportFactory.makeTransportEntity(id = stubTransportId)
        robot.stubTransportMapperToModel(transportEntity, transport)
        robot.stubDataStoreGetTransportSuccess(stubTransportId, transportEntity)
        // Act
        val returnedData = transportRepository.getTransport(stubTransportId).test()
        // Assert
        returnedData.assertResult(transport)
    }

    @Test
    fun getTransportFailsThrowsException() {
        // Assemble
        val stubTransportId = DataFactory.randomUuid()
        val transport = TransportFactory.makeTransport(id = stubTransportId)
        val transportEntity = TransportFactory.makeTransportEntity(id = stubTransportId)
        robot.stubTransportMapperToModel(transportEntity, transport)

        val errorMessage = "Testing cancelEtaFailed"
        robot.stubDataStoreGetTransportFailure(errorMessage)
        // Act
        val testObserver = transportRepository.getTransport(stubTransportId).test()
        // Assert
        testObserver.assertFailureAndMessage(Throwable::class.java, errorMessage)
    }

    @Test
    fun saveTransportCompletes() {
        // Assemble
        val stubCode = 13
        val transport = TransportFactory.makeTransport(code = stubCode)
        val transportEntity = TransportFactory.makeTransportEntity(code = stubCode)
        robot.stubTransportMapperToEntity(transport, transportEntity)
        robot.stubDataStoreSaveTransportSuccess(transportEntity)
        // Act
        val testObserver = transportRepository.saveTransport(transport).test()
        // Assert
        testObserver.assertComplete()

    }

    @Test
    fun saveTransportCallsCorrectDataStoreMethod() {
        // Assemble
        val stubCode = 13
        val transport = TransportFactory.makeTransport(code = stubCode)
        val transportEntity = TransportFactory.makeTransportEntity(code = stubCode)
        robot.stubTransportMapperToEntity(transport, transportEntity)
        robot.stubDataStoreSaveTransportSuccess(transportEntity)
        // Act
        transportRepository.saveTransport(transport).test()
        // Assert
        verify(mockTransportDataStore, times(1)).saveTransport(transportEntity)

    }

    @Test
    fun saveTransportFailsThrowsException() {
        // Assemble
        val stubCode = 13
        val transport = TransportFactory.makeTransport(code = stubCode)
        val transportEntity = TransportFactory.makeTransportEntity(code = stubCode)
        robot.stubTransportMapperToEntity(transport, transportEntity)

        val errorMessage = "Testing cancelEtaFailed"
        robot.stubDataStoreSaveTransportFailure(errorMessage)
        // Act
        val testObserver = transportRepository.saveTransport(transport).test()
        // Assert
        testObserver.assertFailureAndMessage(Throwable::class.java, errorMessage)
    }

    @Test
    fun deleteTransportCompletes() {
        // Assemble
        val stubTransportId = DataFactory.randomUuid()
        val transport = TransportFactory.makeTransport(id = stubTransportId)
        val transportEntity = TransportFactory.makeTransportEntity(id = stubTransportId)
        robot.stubTransportMapperToEntity(transport, transportEntity)
        robot.stubDataStoreSaveDeleteSuccess(transportEntity)
        // Act
        val testObserver = transportRepository.deleteTransport(stubTransportId).test()
        // Assert
        testObserver.assertComplete()
    }

    @Test
    fun deleteTransportCallsCorrectDataStoreMethod() {
        // Assemble
        val stubTransportId = DataFactory.randomUuid()
        val transport = TransportFactory.makeTransport(id = stubTransportId)
        val transportEntity = TransportFactory.makeTransportEntity(id = stubTransportId)
        robot.stubTransportMapperToEntity(transport, transportEntity)
        robot.stubDataStoreSaveDeleteSuccess(transportEntity)
        // Act
        transportRepository.deleteTransport(stubTransportId).test()
        // Assert
        verify(mockTransportDataStore, times(1)).deleteTransport(stubTransportId)
    }

    @Test
    fun deleteTransportFailsThrowsException() {
        // Assemble
        val stubTransportId = DataFactory.randomUuid()
        val transport = TransportFactory.makeTransport(id = stubTransportId)
        val transportEntity = TransportFactory.makeTransportEntity(id = stubTransportId)
        robot.stubTransportMapperToEntity(transport, transportEntity)

        val errorMessage = "Testing cancelEtaFailed"
        robot.stubDataStoreSaveDeleteFailure(errorMessage)
        // Act
        val testObserver = transportRepository.deleteTransport(stubTransportId).test()
        // Assert
        testObserver.assertFailureAndMessage(Throwable::class.java, errorMessage)
    }


    @Test
    fun getAllCompletes() {
        // Assemble
        val transportList = TransportFactory.makeTransportList(5)
        val transportEntityList = TransportFactory.makeTransportEntityList(5)
        robot.stubTransportMapperToModel(transportEntityList, transportList)

        robot.stubDataStoreGetAllSuccess(transportEntityList)
        // Act
        val testObservable = transportRepository.getAll().test()
        // Assert
        testObservable.assertComplete()
    }

    @Test
    fun getAllCallsCorrectDataStoreMethod() {
        // Assemble
        val transportList = TransportFactory.makeTransportList(5)
        val transportEntityList = TransportFactory.makeTransportEntityList(5)
        robot.stubTransportMapperToModel(transportEntityList, transportList)

        robot.stubDataStoreGetAllSuccess(transportEntityList)
        // Act
        transportRepository.getAll()
        // Assert
        verify(mockTransportDataStore, times(1)).getAll()
    }

    @Test
    fun getAllReturnsCorrectData() {
        /// Assemble
        val transportList = TransportFactory.makeTransportList(5)
        val transportEntityList = TransportFactory.makeTransportEntityList(5)
        robot.stubTransportMapperToModel(transportEntityList, transportList)
        robot.stubDataStoreGetAllSuccess(transportEntityList)
        // Act
        val returnedData = transportRepository.getAll().test()
        // Assert
        returnedData.assertResult(transportList)
    }

    @Test
    fun getAllFailsThrowsException() {
        /// Assemble
        val transportList = TransportFactory.makeTransportList(5)
        val transportEntityList = TransportFactory.makeTransportEntityList(5)
        robot.stubTransportMapperToModel(transportEntityList, transportList)

        val errorMessage = "Testing cancelEtaFailed"
        robot.stubDataStoreGetAllFailure(errorMessage)
        // Act
        val testObserver = transportRepository.getAll().test()
        // Assert
        testObserver.assertFailureAndMessage(Throwable::class.java, errorMessage)
    }

    inner class Robot {

        fun stubDataStoreSaveDeleteSuccess(transportEntity: TransportEntity = TransportFactory.makeTransportEntity(), completable: Completable = Completable.complete()): TransportEntity {
            whenever(mockTransportDataStore.deleteTransport(transportEntity.id)).then { completable }
            return transportEntity
        }

        fun stubDataStoreSaveDeleteFailure(message: String = DataFactory.randomUuid()): Throwable {
            val throwable = Throwable(message)
            whenever(mockTransportDataStore.deleteTransport(any())).then { Completable.error(throwable) }
            return throwable
        }

        fun stubDataStoreSaveTransportSuccess(transportEntity: TransportEntity = TransportFactory.makeTransportEntity(), completable: Completable = Completable.complete()): TransportEntity {
            whenever(mockTransportDataStore.saveTransport(transportEntity)).then { completable }
            return transportEntity
        }

        fun stubDataStoreSaveTransportFailure(message: String = DataFactory.randomUuid()): Throwable {
            val throwable = Throwable(message)
            whenever(mockTransportDataStore.saveTransport(any())).then { Completable.error(throwable) }
            return throwable
        }

        fun stubDataStoreGetTransportSuccess(id: String = DataFactory.randomUuid(), transportEntity: TransportEntity = TransportFactory.makeTransportEntity(id = id), observable: Observable<TransportEntity> = Observable.just(transportEntity)): TransportEntity {
            whenever(mockTransportDataStore.getTransport(id)).then { observable }
            return transportEntity
        }

        fun stubDataStoreGetTransportFailure(message: String = DataFactory.randomUuid()): Throwable {
            val throwable = Throwable(message)
            whenever(mockTransportDataStore.getTransport(any())).then { Observable.error<TransportEntity>(throwable) }
            return throwable
        }

        fun stubDataStoreRequestTransportEtaSuccess(transportEntity: TransportEntity = TransportFactory.makeTransportEntity(), observable: Observable<TransportEntity> = Observable.just(transportEntity)): TransportEntity {
            whenever(mockTransportDataStore.requestTransportEta(transportEntity.code)).then { observable }
            return transportEntity
        }

        fun stubDataStoreRequestTransportEtaFailure(message: String = DataFactory.randomUuid()): Throwable {
            val throwable = Throwable(message)
            whenever(mockTransportDataStore.requestTransportEta(any())).then { Observable.error<TransportEntity>(throwable) }
            return throwable
        }

        fun stubDataStoreCancelRequestTransportEtaSuccess(transportEntity: TransportEntity = TransportFactory.makeTransportEntity(), completable: Completable = Completable.complete()): TransportEntity {
            whenever(mockTransportDataStore.cancelTransportEtaRequest(transportEntity.code)).then { completable }
            return transportEntity
        }

        fun stubDataStoreCancelRequestTransportEtaFailure(message: String = DataFactory.randomUuid()): Throwable {
            val throwable = Throwable(message)
            whenever(mockTransportDataStore.cancelTransportEtaRequest(any())).then { Completable.error(throwable) }
            return throwable
        }

        fun stubDataStoreGetAllSuccess(transportEntityList: List<TransportEntity>) {
            whenever(mockTransportDataStore.getAll()).then { Flowable.just(transportEntityList) }
        }

        fun stubDataStoreGetAllFailure(message: String = DataFactory.randomUuid()): Throwable {
            val throwable = Throwable(message)
            whenever(mockTransportDataStore.getAll()).then { Flowable.error<TransportEntity>(throwable) }
            return throwable
        }

        fun stubTransportMapperToEntity(transport: Transport, transportEntity: TransportEntity) {
            whenever(mockMapper.toEntity(transport)).then { transportEntity }
        }

        fun stubTransportMapperToModel(transportEntityList: List<TransportEntity>, transportList: List<Transport>) {
            whenever(mockMapper.toModel(transportEntityList)).then { transportList }
        }

        fun stubTransportMapperToModel(transportEntity: TransportEntity, transport: Transport) {
            whenever(mockMapper.toModel(transportEntity)).then { transport }
        }
    }
}