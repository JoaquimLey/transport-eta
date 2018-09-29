package com.joaquimley.transporteta.domain.usecase.transport.favorites

import com.joaquimley.transporteta.domain.executor.PostExecutionThread
import com.joaquimley.transporteta.domain.executor.ThreadExecutor
import com.joaquimley.transporteta.domain.interactor.transport.favorites.ClearAllTransportsAsFavoriteUseCase
import com.joaquimley.transporteta.domain.repository.FavoritesRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test

class ClearAllTransportsAsFavoriteUseCaseTest {

    private val robot = Robot()

    private val mockThreadExecutor = mock<ThreadExecutor>()
    private val mockPostExecutionThread = mock<PostExecutionThread>()
    private val favoritesRepository = mock<FavoritesRepository>()

    private lateinit var clearAllTransportsAsFavoriteUseCase: ClearAllTransportsAsFavoriteUseCase

    @Before
    fun setUp() {
        clearAllTransportsAsFavoriteUseCase = ClearAllTransportsAsFavoriteUseCase(favoritesRepository,
                mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        // Assemble
        robot.stubTransportRepositoryClearFavorites(Completable.complete())
        // Act
        val testObserver = clearAllTransportsAsFavoriteUseCase.buildUseCaseObservable(null).test()
        // Assert
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        clearAllTransportsAsFavoriteUseCase.buildUseCaseObservable(null)
        verify(favoritesRepository).clearFavorites()
    }

    inner class Robot {
        fun stubTransportRepositoryClearFavorites(completable: Completable) {
            whenever(favoritesRepository.clearFavorites()).thenReturn(completable)
        }
    }


}