package com.joaquimley.transporteta.domain.usecase.favorites

import com.joaquimley.transporteta.domain.executor.PostExecutionThread
import com.joaquimley.transporteta.domain.executor.ThreadExecutor
import com.joaquimley.transporteta.domain.interactor.favorites.GetFavoritesUseCase
import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.domain.repository.FavoritesRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Flowable
import com.joaquimley.transporteta.domain.test.factory.TransportFactory
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test

class GetFavoritesTest {

    private lateinit var getFavoritesUseCase: GetFavoritesUseCase

    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread
    private lateinit var favoritesRepository: FavoritesRepository

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        favoritesRepository = mock()
        getFavoritesUseCase = GetFavoritesUseCase(favoritesRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        getFavoritesUseCase.buildUseCaseObservable()
        verify(favoritesRepository).getAll()
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubTransportRepositoryGetFavorites(Flowable.just(TransportFactory.makeTransportList(2, true)))
        val testObserver = getFavoritesUseCase.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val favoriteTransports = TransportFactory.makeTransportList(2)
        stubTransportRepositoryGetFavorites(Flowable.just(favoriteTransports))
        val testObserver = getFavoritesUseCase.buildUseCaseObservable().test()
        testObserver.assertValue(favoriteTransports)
    }

    private fun stubTransportRepositoryGetFavorites(single: Flowable<List<Transport>>) {
        whenever(favoritesRepository.getAll()).thenReturn(single)
    }

}