package com.joaquimley.transporteta.domain.test.factory

import com.joaquimley.transporteta.domain.model.Transport
import org.buffer.android.boilerplate.domain.test.factory.DataFactory.Factory.randomInt
import org.buffer.android.boilerplate.domain.test.factory.DataFactory.Factory.randomUuid

/**
 * Factory class for [Transport] related instances
 */
class TransportFactory {

    companion object Factory {

        fun makeTransportList(count: Int, isFavorite: Boolean = false): List<Transport> {
            val transports = mutableListOf<Transport>()
            repeat(count) {
                transports.add(makeTransport(isFavorite))
            }
            return transports
        }

        fun makeTransport(isFavorite: Boolean = false, type: Transport.TransportType = Transport.TransportType.BUS): Transport {
            return Transport(randomUuid(), randomUuid(), randomInt(), isFavorite, type)
        }

    }

}