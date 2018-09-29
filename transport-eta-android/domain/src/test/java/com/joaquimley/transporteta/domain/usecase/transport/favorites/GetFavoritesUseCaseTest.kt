package com.joaquimley.transporteta.domain.usecase.transport.favorites

import com.joaquimley.transporteta.domain.executor.PostExecutionThread
import com.joaquimley.transporteta.domain.executor.ThreadExecutor
import com.joaquimley.transporteta.domain.interactor.transport.favorites.GetFavoritesUseCase
import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.domain.repository.FavoritesRepository
import com.joaquimley.transporteta.domain.test.factory.TransportFactory
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

class GetFavoritesUseCaseTest {

    private val robot = Robot()
    private val mockThreadExecutor = mock<ThreadExecutor>()
    private val mockPostExecutionThread = mock<PostExecutionThread>()
    private val favoritesRepository = mock<FavoritesRepository>()

    private lateinit var getFavoritesUseCase: GetFavoritesUseCase

    @Before
    fun setUp() {
        getFavoritesUseCase = GetFavoritesUseCase(favoritesRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        getFavoritesUseCase.buildUseCaseObservable()
        verify(favoritesRepository).getAll()
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        // Assemble
        robot.stubTransportRepositoryGetFavorites()
        // Act
        val testObserver = getFavoritesUseCase.buildUseCaseObservable().test()
        // Assert
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        // Assemble
        val favoriteTransports = robot.stubTransportRepositoryGetFavorites()
        // Act
        val testObserver = getFavoritesUseCase.buildUseCaseObservable().test()
        // Assert
        testObserver.assertValue(favoriteTransports)
    }

    inner class Robot {
        fun stubTransportRepositoryGetFavorites(transportList: List<Transport> =
                                                        TransportFactory.makeTransportList(2, true))
                : List<Transport> {
            whenever(favoritesRepository.getAll()).thenReturn(Flowable.just(transportList))
            return transportList
        }
    }


}