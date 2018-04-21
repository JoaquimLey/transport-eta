//package com.joaquimley.transporteta.ui.model.data
//
//open class Resource<out T> constructor(val status: ResourceState, val data: T? = null, val message: String? = null) {
//
//    companion object {
//        fun <T> success(data: T): com.joaquimley.transporteta.presentation.data.Resource<T> {
//            return Resource(ResourceState.SUCCESS, data, null)
//        }
//
//        fun <T> empty(): com.joaquimley.transporteta.presentation.data.Resource<T> {
//            return Resource(ResourceState.EMPTY, null, null)
//        }
//
//        fun <T> error(message: String): com.joaquimley.transporteta.presentation.data.Resource<T> {
//            return Resource(ResourceState.ERROR, null, message)
//        }
//
//        fun <T> loading(): com.joaquimley.transporteta.presentation.data.Resource<T> {
//            return Resource(ResourceState.LOADING, null, null)
//        }
//    }
//}