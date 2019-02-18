package com.joaquimley.transporteta.presentation.util.factory

import androidx.annotation.RestrictTo
import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.presentation.model.TransportView
import com.joaquimley.transporteta.presentation.util.factory.DataFactory.randomBoolean
import com.joaquimley.transporteta.presentation.util.factory.DataFactory.randomInt
import com.joaquimley.transporteta.presentation.util.factory.DataFactory.randomUuid


@RestrictTo(RestrictTo.Scope.TESTS)
object TransportFactory {

    fun makeTransport(code: Int): Transport {
        return Transport(randomUuid(), randomUuid(), code, randomUuid(), true, "bus")
    }

    fun makeTransport(isFavorite: Boolean = false, type: String = "bus"): Transport {
        return Transport(randomUuid(), randomUuid(), randomInt(), randomUuid(), isFavorite, type)
    }

    fun makeTransportList(count: Int, isFavorite: Boolean = false, type: String = "bus"): List<Transport> {
        val transports = mutableListOf<Transport>()
        repeat(count) {
            transports.add(makeTransport(isFavorite, type))
        }
        return transports
    }


    fun makeTransportView(isFavorite: Boolean = false, type: TransportView.Type = TransportView.Type.BUS): TransportView {
        return TransportView(randomUuid(), randomUuid(), randomInt(), randomUuid(), isFavorite, type)
    }

    fun makeTransportViewList(count: Int, isFavorite: Boolean = false, type: TransportView.Type = TransportView.Type.BUS): List<TransportView> {
        val transports = mutableListOf<TransportView>()
        repeat(count) {
            transports.add(makeTransportView(isFavorite, type))
        }
        return transports
    }


}


//    fun generateSmsModel(busStopCode: Int? = null): SmsModel {
//        return SmsModel(busStopCode
//                ?: DataFactory.randomInt(), DataFactory.randomString())
//    }
//
//    fun generateFavoriteView(busStopCode: Int? = null): TransportView {
//        return TransportView(busStopCode
//                ?: DataFactory.randomInt(), DataFactory.randomString(), DataFactory.randomString())
//    }
//
//    fun generateFavoriteViewList(size: Int = 5): List<TransportView> {
//        val result = ArrayList<TransportView>()
//        for(i in 0..size) {
//            result.add(generateFavoriteView())
//        }
//        return result
//    }
