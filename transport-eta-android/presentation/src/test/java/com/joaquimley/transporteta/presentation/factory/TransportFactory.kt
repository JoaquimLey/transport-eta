package com.joaquimley.transporteta.presentation.factory

import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.presentation.factory.DataFactory.randomInt
import com.joaquimley.transporteta.presentation.factory.DataFactory.randomUuid

class TransportFactory {

    companion object Factory {

        fun makeTransportList(count: Int, isFavorite: Boolean = false, type: String = randomUuid()): List<Transport> {
            val transports = mutableListOf<Transport>()
            repeat(count) {
                transports.add(makeTransport(isFavorite, type))
            }
            return transports
        }

        fun makeTransport(isFavorite: Boolean = false, type: String = randomUuid()): Transport {
            return Transport(randomUuid(), randomUuid(), randomInt(), randomUuid(), isFavorite, type)
        }

    }

}