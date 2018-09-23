package com.joaquimley.transporteta.domain.interactor.favorites

import com.joaquimley.transporteta.domain.executor.PostExecutionThread
import com.joaquimley.transporteta.domain.executor.ThreadExecutor
import com.joaquimley.transporteta.domain.interactor.base.FlowableUseCase
import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.domain.repository.FavoritesRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository,
											  threadExecutor: ThreadExecutor,
											  postExecutionThread: PostExecutionThread) :
		FlowableUseCase<List<Transport>, Void?>(threadExecutor, postExecutionThread) {

	public override fun buildUseCaseObservable(params: Void?): Flowable<List<Transport>> {
		return favoritesRepository.getAll()
	}
}