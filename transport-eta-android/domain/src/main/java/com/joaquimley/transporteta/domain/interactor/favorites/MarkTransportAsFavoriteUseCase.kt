package com.joaquimley.transporteta.domain.interactor.favorites

import com.joaquimley.transporteta.domain.executor.PostExecutionThread
import com.joaquimley.transporteta.domain.executor.ThreadExecutor
import com.joaquimley.transporteta.domain.interactor.base.CompletableUseCase
import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.domain.repository.FavoritesRepository
import io.reactivex.Completable
import javax.inject.Inject

class MarkTransportAsFavoriteUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository,
                                                         threadExecutor: ThreadExecutor,
                                                         postExecutionThread: PostExecutionThread) :
        CompletableUseCase<Transport>(threadExecutor, postExecutionThread) {

    /**
     * Builds a [Completable] which will be used when the current [CompletableUseCase] is executed.
     */
    public override fun buildUseCaseObservable(params: Transport): Completable {
        return favoritesRepository.markAsFavorite(params)
    }
}