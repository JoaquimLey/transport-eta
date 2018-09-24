package com.joaquimley.transporteta.presentation.util.extensions

import androidx.lifecycle.MutableLiveData
import com.joaquimley.transporteta.presentation.data.Resource

fun <T>MutableLiveData<Resource<T>>.success(data: T) {
	this.postValue(Resource.success(data))
}

fun <T>MutableLiveData<Resource<T>>.error(throwable: Throwable) {
	this.postValue(Resource.error(throwable.message))
}