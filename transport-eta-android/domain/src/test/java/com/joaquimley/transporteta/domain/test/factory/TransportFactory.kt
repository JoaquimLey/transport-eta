package com.joaquimley.transporteta.domain.test.factory

import com.joaquimley.transporteta.domain.model.Transport
import org.buffer.android.boilerplate.domain.test.factory.DataFactory.Factory.randomInt
import org.buffer.android.boilerplate.domain.test.factory.DataFactory.Factory.randomUuid

/**
 * Factory class for [Transport] related instances
 */
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