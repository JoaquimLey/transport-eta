package com.joaquimley.transporteta.domain.model

data class Transport(val id: String, val name: String, val code: Int, val isFavorite: Boolean = false,
                     val type: TransportType = TransportType.BUS) {

    enum class TransportType {
        BUS
    }
}

