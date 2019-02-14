package com.joaquimley.transporteta.sharedpreferences.model

import com.joaquimley.transporteta.sharedpreferences.FrameworkLocalStorageImpl
import kotlinx.serialization.Serializable

@Serializable
data class SharedPrefTransport(val id: String, val name: String, val code: Int, val latestEta: String,
                               val isFavorite: Boolean = false, val type: String, val lastUpdated: String,
                               val slot: FrameworkLocalStorageImpl.Slot? = null)