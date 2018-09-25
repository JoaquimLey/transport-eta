package com.joaquimley.transporteta.domain.interactor.transport

import com.joaquimley.transporteta.domain.executor.PostExecutionThread
import com.joaquimley.transporteta.domain.executor.ThreadExecutor
import com.joaquimley.transporteta.domain.interactor.base.CompletableUseCase
import com.joaquimley.transporteta.domain.interactor.base.SingleUseCase
import com.joaquimley.transporteta.domain.repository.TransportRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CancelEtaRequestUseCase @Inject constructor(private val transportRepository: TransportRepository,
												  threadExecutor: ThreadExecutor,
												  postExecutionThread: PostExecutionThread) :
		CompletableUseCase<Int>(threadExecutor, postExecutionThread) {
	/**
	 * Builds a [Single] which will be used when the current [SingleUseCase] is executed.
	 */
	override fun buildUseCaseObservable(params: Int): Completable {
		return transportRepository.cancelTransportEtaRequest(params)
	}
}