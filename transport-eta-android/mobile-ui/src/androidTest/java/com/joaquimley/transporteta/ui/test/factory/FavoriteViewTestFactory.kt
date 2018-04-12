package com.joaquimley.transporteta.ui.test.factory

import android.support.annotation.RestrictTo
import com.joaquimley.transporteta.ui.model.FavoriteView
import com.joaquimley.transporteta.ui.test.factory.ui.DataFactory
import java.util.*
import kotlin.collections.ArrayList

@RestrictTo(RestrictTo.Scope.TESTS)
object FavoriteViewTestFactory {

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