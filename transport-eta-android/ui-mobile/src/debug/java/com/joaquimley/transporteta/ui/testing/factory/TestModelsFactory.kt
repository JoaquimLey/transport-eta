package com.joaquimley.transporteta.ui.testing.factory

import androidx.annotation.RestrictTo
import com.joaquimley.transporteta.presentation.model.TransportView

@RestrictTo(RestrictTo.Scope.TESTS)
object TestModelsFactory {

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