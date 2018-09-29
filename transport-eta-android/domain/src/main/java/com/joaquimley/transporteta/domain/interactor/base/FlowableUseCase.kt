package com.joaquimley.transporteta.domain.interactor.base

import com.joaquimley.transporteta.domain.executor.PostExecutionThread
import com.joaquimley.transporteta.domain.executor.ThreadExecutor
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


/**
 * Abstract class for a UseCase that returns an instance of a [Flowable].
 */
abstract class FlowableUseCase<T, in Params> constructor(
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread) {

    /**
     * Builds a [Flowable] which will be used when the current [FlowableUseCase] is executed.
     */
    protected abstract fun buildUseCaseObservable(params: Params? = null): Flowable<T>

    /**
     * Executes the current use case.
     */
    open fun execute(params: Params? = null): Flowable<T> {
        return this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler) as Flowable<T>
    }
}