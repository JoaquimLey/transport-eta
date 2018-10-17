package com.joaquimley.transporteta.sharedpreferences.model

@kotlinx.serialization.Serializable
data class SharedPrefTransport(val id: String, val name: String, val code: Int, val latestEta: String,
                               val isFavorite: Boolean = false, val type: String, val lastUpdated: Long,
                               val slot: String)