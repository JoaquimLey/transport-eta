package com.joaquimley.transporteta.domain.interactor.transport

import com.joaquimley.transporteta.domain.executor.PostExecutionThread
import com.joaquimley.transporteta.domain.executor.ThreadExecutor
import com.joaquimley.transporteta.domain.interactor.base.ObservableUseCase
import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.domain.repository.TransportRepository
import io.reactivex.Observable
import javax.inject.Inject

class RequestEtaUseCase @Inject constructor(private val transportRepository: TransportRepository,
											threadExecutor: ThreadExecutor,
											postExecutionThread: PostExecutionThread) :
		ObservableUseCase<Transport, Int>(threadExecutor, postExecutionThread) {
	/**
	 * Builds a [Observable] which will be used when the current [ObservableUseCase] is executed.
	 */
	public override fun buildUseCaseObservable(params: Int): Observable<Transport> {
		return transportRepository.requestTransportEta(params)
	}
}