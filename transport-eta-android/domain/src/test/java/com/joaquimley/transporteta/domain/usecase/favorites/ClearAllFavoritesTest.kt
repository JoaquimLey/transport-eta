package com.joaquimley.transporteta.domain.usecase.favorites

import com.joaquimley.transporteta.domain.executor.PostExecutionThread
import com.joaquimley.transporteta.domain.executor.ThreadExecutor
import com.joaquimley.transporteta.domain.interactor.favorites.ClearAllTransportsAsFavoriteUseCase
import com.joaquimley.transporteta.domain.repository.FavoritesRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test

class ClearAllFavoritesTest {

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
    fun buildUseCaseObservableCallsRepository() {
        clearAllTransportsAsFavoriteUseCase.buildUseCaseObservable(null)
        verify(favoritesRepository).clearFavorites()
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubTransportRepositoryClearFavorites(Completable.complete())
        val testObserver = clearAllTransportsAsFavoriteUseCase.buildUseCaseObservable(null).test()
        testObserver.assertComplete()
    }


    private fun stubTransportRepositoryClearFavorites(completable: Completable) {
        whenever(favoritesRepository.clearFavorites()).thenReturn(completable)
    }
}