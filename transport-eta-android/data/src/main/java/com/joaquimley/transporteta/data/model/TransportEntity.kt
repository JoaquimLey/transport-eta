package com.joaquimley.transporteta.data.model

data class TransportEntity(val id: String, val name: String, val code: Int, val latestEta: String,
                           val isFavorite: Boolean = false, val type: String)
