package com.joaquimley.transporteta.domain.interactor.transport

import com.joaquimley.transporteta.domain.executor.PostExecutionThread
import com.joaquimley.transporteta.domain.executor.ThreadExecutor
import com.joaquimley.transporteta.domain.interactor.base.SingleUseCase
import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.domain.repository.FavoritesRepository
import io.reactivex.Single
import javax.inject.Inject

class RequestEtaUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository,
											threadExecutor: ThreadExecutor,
											postExecutionThread: PostExecutionThread) :
		SingleUseCase<Transport, Int>(threadExecutor, postExecutionThread) {
	/**
	 * Builds a [Single] which will be used when the current [SingleUseCase] is executed.
	 */
	override fun buildUseCaseObservable(params: Int): Single<Transport> {

	}

	public override fun buildUseCaseObservable(params: Transport): Single<List<Transport>> {
		return favoritesRepository.getAll()
	}
}