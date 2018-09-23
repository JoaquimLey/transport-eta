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

	private lateinit var clearAllTransportsAsFavoriteUseCase: ClearAllTransportsAsFavoriteUseCase

	private lateinit var mockThreadExecutor: ThreadExecutor
	private lateinit var mockPostExecutionThread: PostExecutionThread
	private lateinit var favoritesRepository: FavoritesRepository

	@Before
	fun setUp() {
		mockThreadExecutor = mock()
		mockPostExecutionThread = mock()
		favoritesRepository = mock()
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