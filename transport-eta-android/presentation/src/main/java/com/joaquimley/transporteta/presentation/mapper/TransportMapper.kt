package com.joaquimley.transporteta.presentation.mapper

import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.presentation.model.TransportView

class TransportMapper {

    fun toView(from: List<Transport>): List<TransportView> {
        return from.map { toView(it) }
    }

    fun toView(from: Transport): TransportView {
        return TransportView(from.id, from.code, from.latestEta, from.isFavorite, TransportView.Type.valueOf(from.type.toUpperCase()))
    }

    fun toModel(from: List<TransportView>): List<Transport> {
        return from.map { toModel(it) }
    }

    fun toModel(from: TransportView): Transport {
        return Transport(from.id, "", from.code, from.latestEta, from.isFavorite, from.type.name.toLowerCase())
    }
}