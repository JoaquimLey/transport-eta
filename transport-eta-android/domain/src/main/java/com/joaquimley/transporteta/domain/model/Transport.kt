package com.joaquimley.transporteta.domain.model

data class Transport(val id: String, val name: String, val code: Int, val latestEta: String, val isFavorite: Boolean = false,
                     val type: String)

