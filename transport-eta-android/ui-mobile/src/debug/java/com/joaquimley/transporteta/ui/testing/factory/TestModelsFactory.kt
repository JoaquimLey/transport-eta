package com.joaquimley.transporteta.ui.testing.factory

import androidx.annotation.RestrictTo
import com.joaquimley.transporteta.presentation.model.FavoriteView

@RestrictTo(RestrictTo.Scope.TESTS)
object TestModelsFactory {

    fun generateFavoriteView(busStopCode: Int? = null): FavoriteView {
        return FavoriteView(busStopCode
                ?: DataFactory.randomInt(), DataFactory.randomString(), DataFactory.randomString())
    }

    fun generateFavoriteViewList(size: Int = 5): List<FavoriteView> {
        val result = ArrayList<FavoriteView>()
        for(i in 0..size) {
            result.add(generateFavoriteView())
        }
        return result
    }
}