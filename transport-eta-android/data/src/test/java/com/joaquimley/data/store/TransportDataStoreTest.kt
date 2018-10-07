package com.joaquimley.data.store

import com.joaquimley.data.factory.DataFactory
import com.joaquimley.data.factory.TransportFactory
import com.joaquimley.data.model.TransportEntity
import com.joaquimley.data.source.FrameworkLocalStorage
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test

class TransportDataStoreTest {

    private val robot = Robot()

    private val mockFrameworkLocalStorage = mock<FrameworkLocalStorage>()

    private lateinit var transportDataStore: TransportDataStore

    @Before
    fun setUp() {
        transportDataStore = TransportDataStoreImpl(mockFrameworkLocalStorage)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun markAsFavoriteThrowsNotYetImplemented() {
        // Assemble

        // Act
        val result = transportDataStore.markAsFavorite(TransportFactory.makeTransportEntity()).test()
        // Assert
        result.assertError(NotImplementedError::class.java)
    }

    @Test
    fun removeAsFavoriteThrowsNotYetImplemented() {
        // Assemble

        // Act
        val result = transportDataStore.removeAsFavorite(TransportFactory.makeTransportEntity()).test()
        // Assert
        result.assertError(NotImplementedError::class.java)
    }

    @Test
    fun getAllFavoritesThrowsNotYetImplemented() {
        // Assemble

        // Act
        val result = transportDataStore.getAllFavorites().test()
        // Assert
        result.assertError(NotImplementedError::class.java)
    }


    @Test
    fun clearAllFavoritesThrowsNotYetImplemented() {
        // Assemble

        // Act
        val result = transportDataStore.clearAllFavorites().test()
        // Assert
        result.assertError(NotImplementedError::class.java)
    }

    @Test
    fun requestTransportEtaThrowsNotYetImplemented() {
        // Assemble

        // Act
        val result = transportDataStore.requestTransportEta(DataFactory.randomInt()).test()
        // Assert
        result.assertError(NotImplementedError::class.java)
    }


    @Test
    fun cancelTransportEtaRequestThrowsNotYetImplemented() {
        // Assemble

        // Act
        val result = transportDataStore.cancelTransportEtaRequest(DataFactory.randomInt()).test()
        // Assert
        result.assertError(NotImplementedError::class.java)
    }


    /**
     * Should use SharedPreferences for v1.0 only
     */
    @Test
    fun saveTransportCompletes() {
        // Assemble
        val stubbedModel = robot.stubFrameworkLocalStorageSaveTransportSuccess()
        // Act
        val testObserver = transportDataStore.saveTransport(stubbedModel).test()
        // Assert
        testObserver.assertComplete()
    }

    /**
     * Should use SharedPreferences for v1.0 only
     */
    @Test
    fun saveTransportCallsCorrectMethodOnFrameworkLocalStorage() {
        // Assemble
        val stubbedModel = robot.stubFrameworkLocalStorageSaveTransportSuccess()
        // Act
        transportDataStore.saveTransport(stubbedModel)
        // Assert
        verify(mockFrameworkLocalStorage, times(1)).saveTransport(stubbedModel)
    }

    /**
     * Should use SharedPreferences for v1.0 only
     */
    @Test
    fun saveTransportFailsThrowsException() {
        // Assemble
        val errorMessage = "Testing Save transport fails"
        robot.stubFrameworkLocalStorageSaveTransportFails(errorMessage)
        // Act
        val testObserver = transportDataStore.saveTransport(TransportFactory.makeTransportEntity()).test()
        // Assert
        testObserver.assertErrorMessage(errorMessage)
    }


    /**
     * Should use SharedPreferences for v1.0 only
     */
    @Test
    fun deleteTransportCompletes() {
        // Assemble
        val stubbedId = robot.stubFrameworkLocalStorageDeleteTransportSuccess()
        // Act
        val testObserver = transportDataStore.deleteTransport(stubbedId).test()
        // Assert
        testObserver.assertComplete()
    }

    /**
     * Should use SharedPreferences for v1.0 only
     */
    @Test
    fun deleteTransportCallsCorrectMethodOnFrameworkLocalStorage() {
        // Assemble
        val stubbedId = robot.stubFrameworkLocalStorageDeleteTransportSuccess()
        // Act
        transportDataStore.deleteTransport(stubbedId)
        // Assert
        verify(mockFrameworkLocalStorage, times(1)).deleteTransport(stubbedId)
    }

    /**
     * Should use SharedPreferences for v1.0 only
     */
    @Test
    fun deleteTransportFailsThrowsException() {
        // Assemble
        val errorMessage = "Testing Delete transport fails"
        robot.stubFrameworkLocalStorageDeleteTransportFails(errorMessage)
        // Act
        val testObserver = transportDataStore.deleteTransport(DataFactory.randomUuid()).test()
        // Assert
        testObserver.assertErrorMessage(errorMessage)
    }


    /**
     * Should use SharedPreferences for v1.0 only
     */
    @Test
    fun getTransportCompletes() {
        // Assemble
        val stubbedTransportEntity = robot.stubFrameworkLocalStorageGetTransportSuccess()
        // Act
        val testObserver = transportDataStore.getTransport(stubbedTransportEntity.id).test()
        // Assert
        testObserver.assertComplete()
    }

    /**
     * Should use SharedPreferences for v1.0 only
     */
    @Test
    fun getTransportCallsCorrectMethodOnFrameworkLocalStorage() {
        // Assemble
        val stubbedTransportEntity = robot.stubFrameworkLocalStorageGetTransportSuccess()
        // Act
        transportDataStore.getTransport(stubbedTransportEntity.id)
        // Assert
        verify(mockFrameworkLocalStorage, times(1)).getTransport(stubbedTransportEntity.id)
    }

    /**
     * Should use SharedPreferences for v1.0 only
     */
    @Test
    fun getTransportFailsThrowsException() {
        // Assemble
        val errorMessage = "Testing Get transport fails"
        robot.stubFrameworkLocalStorageGetTransportFails(errorMessage)
        // Act
        val testObserver = transportDataStore.getTransport(DataFactory.randomUuid()).test()
        // Assert
        testObserver.assertErrorMessage(errorMessage)
    }

    /**
     * Should use SharedPreferences for v1.0 only
     */
    @Test
    fun getAllCompletes() {
        // Assemble
        robot.stubFrameworkLocalStorageGetAllSuccess()
        // Act
        val testObserver = transportDataStore.getAll().test()
        // Assert
        testObserver.assertComplete()
    }

    /**
     * Should use SharedPreferences for v1.0 only
     */
    @Test
    fun getAllCorrectMethodOnFrameworkLocalStorage() {
        // Assemble
        robot.stubFrameworkLocalStorageGetAllSuccess()
        // Act
        transportDataStore.getAll()
        // Assert
        verify(mockFrameworkLocalStorage, times(1)).getAll()
    }

    /**
     * Should use SharedPreferences for v1.0 only
     */
    @Test
    fun getAllFailsThrowsException() {
        // Assemble
        val errorMessage = "Testing Get All fails"
        robot.stubFrameworkLocalStorageGetAllFails(errorMessage)
        // Act
        val testObserver = transportDataStore.getAll().test()
        // Assert
        testObserver.assertErrorMessage(errorMessage)
    }


    inner class Robot {

        fun stubFrameworkLocalStorageGetAllSuccess(transportEntityList: List<TransportEntity> = TransportFactory.makeTransportEntityList(5)): List<TransportEntity> {
            whenever(mockFrameworkLocalStorage.getAll()).then { Single.just(transportEntityList) }
            return transportEntityList
        }

        fun stubFrameworkLocalStorageGetAllFails(message: String = DataFactory.randomUuid()): Throwable {
            val throwable = Throwable(message)
            whenever(mockFrameworkLocalStorage.getAll()).then { Single.error<Throwable>(throwable) }
            return throwable
        }

        fun stubFrameworkLocalStorageGetTransportSuccess(transportEntity: TransportEntity = TransportFactory.makeTransportEntity()): TransportEntity {
            whenever(mockFrameworkLocalStorage.getTransport(transportEntity.id)).then { Single.just(transportEntity) }
            return transportEntity
        }

        fun stubFrameworkLocalStorageGetTransportFails(message: String = DataFactory.randomUuid()): Throwable {
            val throwable = Throwable(message)
            whenever(mockFrameworkLocalStorage.getTransport(any())).then { Single.error<Throwable>(throwable) }
            return throwable
        }

        fun stubFrameworkLocalStorageSaveTransportSuccess(transportEntity: TransportEntity = TransportFactory.makeTransportEntity()): TransportEntity {
            whenever(mockFrameworkLocalStorage.saveTransport(transportEntity)).then { Completable.complete() }
            return transportEntity
        }

        fun stubFrameworkLocalStorageSaveTransportFails(message: String = DataFactory.randomUuid()): Throwable {
            val throwable = Throwable(message)
            whenever(mockFrameworkLocalStorage.saveTransport(any())).then { Completable.error(throwable) }
            return throwable
        }

        fun stubFrameworkLocalStorageDeleteTransportSuccess(transportId: String = DataFactory.randomUuid()): String {
            whenever(mockFrameworkLocalStorage.deleteTransport(transportId)).then { Completable.complete() }
            return transportId
        }

        fun stubFrameworkLocalStorageDeleteTransportFails(message: String = DataFactory.randomUuid()): Throwable {
            val throwable = Throwable(message)
            whenever(mockFrameworkLocalStorage.deleteTransport(any())).then { Completable.error(throwable) }
            return throwable

        }
    }

}