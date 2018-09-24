package com.joaquimley.transporteta.presentation.data

open class Resource<out T> constructor(val status: ResourceState, val data: T? = null, val message: String? = null) {

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(ResourceState.SUCCESS, data, null)
        }

        fun <T> empty(): Resource<T> {
            return Resource(ResourceState.EMPTY, null, null)
        }

        fun <T> error(message: String?): Resource<T> {
            return Resource(ResourceState.ERROR, null, message)
        }

        fun <T> loading(): Resource<T> {
            return Resource(ResourceState.LOADING, null, null)
        }
    }
}