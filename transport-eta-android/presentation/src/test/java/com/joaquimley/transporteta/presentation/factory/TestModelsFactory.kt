package com.joaquimley.transporteta.presentation.factory

import androidx.annotation.RestrictTo
import com.joaquimley.transporteta.presentation.model.TransportView
import com.joaquimley.transporteta.sms.model.SmsModel
import com.joaquimley.transporteta.ui.testing.factory.ui.DataFactory

@RestrictTo(RestrictTo.Scope.TESTS)
object TestModelsFactory{

    fun generateSmsModel(busStopCode: Int? = null): SmsModel {
        return SmsModel(busStopCode
                ?: DataFactory.randomInt(), DataFactory.randomString())
    }

    fun generateFavoriteView(busStopCode: Int? = null): TransportView {
        return TransportView(busStopCode
                ?: DataFactory.randomInt(), DataFactory.randomString(), DataFactory.randomString())
    }

    fun generateFavoriteViewList(size: Int = 5): List<TransportView> {
        val result = ArrayList<TransportView>()
        for(i in 0..size) {
            result.add(generateFavoriteView())
        }
        return result
    }
}