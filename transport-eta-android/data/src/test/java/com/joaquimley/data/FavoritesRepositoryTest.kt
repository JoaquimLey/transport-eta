package com.joaquimley.data

import com.joaquimley.data.factory.DataFactory.Factory.randomUuid
import com.joaquimley.data.factory.TransportFactory
import com.joaquimley.data.mapper.TransportMapper
import com.joaquimley.data.model.TransportEntity
import com.joaquimley.data.store.TransportDataStore
import com.joaquimley.transporteta.domain.model.Transport
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Test

class FavoritesRepositoryTest {

    private val robot = Robot()

    private val mockTransportDataStore = mock<TransportDataStore>()
    private val mockMapper = mock<TransportMapper>()

    private lateinit var favoritesRepository: FavoritesRepositoryImpl


    @Before
    fun setUp() {
        favoritesRepository = FavoritesRepositoryImpl(mockTransportDataStore, mockMapper)
    }

    @After
    fun tearDown() {
    }

    // region markAsFavorite

    @Test
    fun markAsFavoriteCompletes() {
        // Assemble
        val transport = TransportFactory.makeTransport()
        val transportEntity = TransportFactory.makeTransportEntity()
        robot.stubTransportMapperToEntity(transport, transportEntity)
        robot.stubDataStoreMarkAsFavoriteSuccess(transportEntity)
        // Act
        val testObserver = favoritesRepository.markAsFavorite(transport).test()
        // Assert
        testObserver.assertComplete()
    }

    @Test
    fun markAsFavoriteCallsCorrectTransportDataStoreMethod() {
        // Assemble
        val transport = TransportFactory.makeTransport()
        val transportEntity = TransportFactory.makeTransportEntity()
        robot.stubTransportMapperToEntity(transport, transportEntity)
        // Act
        favoritesRepository.markAsFavorite(transport)
        // Assert
        verify(mockTransportDataStore, times(1)).markAsFavorite(transportEntity)
    }

    @Test
    fun markAsFavoriteFailsThrowsException() {
        // Assemble
        val transport = TransportFactory.makeTransport()
        val transportEntity = TransportFactory.makeTransportEntity()
        robot.stubTransportMapperToEntity(transport, transportEntity)

        val errorMessage = "Testing mark As Favorite failed"
        robot.stubDataStoreMarkAsFavoriteFails(errorMessage)
        // Act
        val testObserver = favoritesRepository.markAsFavorite(transport).test()
        // Assert
        testObserver.assertFailureAndMessage(Throwable::class.java, errorMessage)
    }

    // endregion markAsFavorite

    // region removeAsFavorite

    @Test
    fun removeAsFavoriteCompletes() {
        // Assemble
        val transport = TransportFactory.makeTransport()
        val transportEntity = TransportFactory.makeTransportEntity()
        robot.stubTransportMapperToEntity(transport, transportEntity)
        robot.stubDataStoreRemoveAsFavoriteSuccess(transportEntity)
        // Act
        val testObserver = favoritesRepository.removeAsFavorite(transport).test()
        // Assert
        testObserver.assertComplete()
    }

    @Test
    fun removeAsFavoriteCallsCorrectTransportDataStoreMethod() {
        // Assemble
        val transport = TransportFactory.makeTransport()
        val transportEntity = TransportFactory.makeTransportEntity()
        robot.stubTransportMapperToEntity(transport, transportEntity)
        // Act
        favoritesRepository.removeAsFavorite(transport)
        // Assert
        verify(mockTransportDataStore, times(1)).removeAsFavorite(transportEntity)
    }

    @Test
    fun removeAsFavoriteFailsThrowsException() {
        // Assemble
        val transport = TransportFactory.makeTransport()
        val transportEntity = TransportFactory.makeTransportEntity()
        robot.stubTransportMapperToEntity(transport, transportEntity)

        val errorMessage = "Testing removeAsFavorite failed"
        robot.stubDataStoreRemoveAsFavoriteFails(errorMessage)
        // Act
        val testObserver = favoritesRepository.removeAsFavorite(transport).test()
        // Assert
        testObserver.assertFailureAndMessage(Throwable::class.java, errorMessage)
    }

    // endregion removeAsFavorite

    // region getAllFavorites

    @Test
    fun getAllFavoritesCompletes() {
        // Assemble
        val transportList = TransportFactory.makeTransportList(5, true)
        val transportEntityList = TransportFactory.makeTransportEntityList(5, true)
        robot.stubTransportMapperToModel(transportEntityList, transportList)
        robot.stubDataStoreGetAllFavoritesSuccess(transportEntityList)
        // Act
        val testObserver = favoritesRepository.getAll().test()
        // Assert
        testObserver.assertComplete()
    }

    @Test
    fun getAllFavoritesCallsCorrectTransportDataStoreMethod() {
        // Assemble
        val transportList = TransportFactory.makeTransportList(5, true)
        val transportEntityList = TransportFactory.makeTransportEntityList(5, true)
        robot.stubTransportMapperToModel(transportEntityList, transportList)
        robot.stubDataStoreGetAllFavoritesSuccess(transportEntityList)
        // Act
        favoritesRepository.getAll()
        // Assert
        verify(mockTransportDataStore, times(1)).getAllFavorites()
    }

    @Test
    fun getAllFavoritesReturnsCorrectData() {
        // Assemble
        val transportList = TransportFactory.makeTransportList(5, true)
        val transportEntityList = TransportFactory.makeTransportEntityList(5, true)
        robot.stubTransportMapperToModel(transportEntityList, transportList)
        robot.stubDataStoreGetAllFavoritesSuccess(transportEntityList)
        // Act
        val returnedData = favoritesRepository.getAll().test()
        // Assert
        returnedData.assertResult(transportList)
    }

    @Test
    fun getAllFavoritesFailsThrowsException() {
        // Assemble
        val transportList = TransportFactory.makeTransportList(5, true)
        val transportEntityList = TransportFactory.makeTransportEntityList(5, true)
        robot.stubTransportMapperToModel(transportEntityList, transportList)

        val errorMessage = "Test getAllFavorites fails"
        robot.stubDataStoreGetAllFavoriteFails(errorMessage)
        // Act
        val testObserver = favoritesRepository.getAll().test()
        // Assert
        testObserver.assertFailureAndMessage(Throwable::class.java, errorMessage)
    }

    // endregion getAllFavorites

    // region clearAll

    @Test
    fun clearAllCompletes() {
        // Assemble
        robot.stubClearAllSuccess()
        // Act
        val testObserver = favoritesRepository.clearAll().test()
        // Assert
        testObserver.assertComplete()
    }

    @Test
    fun clearAllCallsCorrectTransportDataStoreMethod() {
        // Assemble
        robot.stubClearAllSuccess()
        // Act
        favoritesRepository.clearAll().test()
        // Assert
        verify(mockTransportDataStore, times(1)).clearAllFavorites()
    }


    @Test
    fun clearAllFailsThrowsException() {
        // Assemble
        val errorMessage = "Testing clearAll failed"
        robot.stubClearAllFails(errorMessage)
        // Act
        val testObserver = favoritesRepository.clearAll().test()
        // Assert
        testObserver.assertFailureAndMessage(Throwable::class.java, errorMessage)
    }

    // endregion clearAll


    inner class Robot {

        fun stubDataStoreMarkAsFavoriteSuccess(transportEntity: TransportEntity = TransportFactory.makeTransportEntity(false), completable: Completable = Completable.complete()): TransportEntity {
            whenever(mockTransportDataStore.markAsFavorite(transportEntity)).then { completable }
            return transportEntity
        }

        fun stubDataStoreMarkAsFavoriteFails(message: String = randomUuid()): Throwable {
            val throwable = Throwable(message)
            whenever(mockTransportDataStore.markAsFavorite(anyOrNull())).then { Completable.error(throwable) }
            return throwable
        }

        fun stubDataStoreRemoveAsFavoriteSuccess(transportEntity: TransportEntity = TransportFactory.makeTransportEntity(true), completable: Completable = Completable.complete()): TransportEntity {
            whenever(mockTransportDataStore.removeAsFavorite(transportEntity)).then { completable }
            return transportEntity
        }

        fun stubDataStoreRemoveAsFavoriteFails(message: String = randomUuid()): Throwable {
            val throwable = Throwable(message)
            whenever(mockTransportDataStore.removeAsFavorite(anyOrNull())).then { Completable.error(throwable) }
            return throwable
        }

        fun stubDataStoreGetAllFavoritesSuccess(transportEntityList: List<TransportEntity> = TransportFactory.makeTransportEntityList(5, true)): List<TransportEntity> {
            whenever(mockTransportDataStore.getAllFavorites()).then { Flowable.just(transportEntityList) }
            return transportEntityList
        }

        fun stubDataStoreGetAllFavoriteFails(message: String = randomUuid()): Throwable {
            val throwable = Throwable(message)
            whenever(mockTransportDataStore.getAllFavorites()).then { Flowable.error<Transport>(throwable) }
            return throwable
        }

        fun stubClearAllSuccess(completable: Completable = Completable.complete()) {
            whenever(mockTransportDataStore.clearAllFavorites()).then { completable }
        }

        fun stubClearAllFails(message: String = randomUuid()): Throwable {
            val throwable = Throwable(message)
            whenever(mockTransportDataStore.clearAllFavorites()).then { Completable.error(throwable) }
            return throwable
        }

        fun stubTransportMapperToEntity(transport: List<Transport>, transportEntity: List<TransportEntity>) {
            whenever(mockMapper.toEntity(transport)).then { transportEntity }
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