package com.joaquimley.transporteta.presentation.mapper

import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.presentation.model.TransportView

class TransportMapper {

    fun toView(from: Transport): TransportView {
        return TransportView(from.code, from.latestEta, type = TransportView.TransportType.valueOf(from.type))
    }
}