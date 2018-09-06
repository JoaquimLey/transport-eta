package com.joaquimley.transporteta.ui.home.favorite

import androidx.lifecycle.ViewModelProviders
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModel
import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModelFactory

class FavoritesViewModelProvider(private val viewModelFactory: FavoritesViewModelFactory) {

    operator fun invoke(fragment: Fragment): FavoritesViewModel {
        return provide(fragment)
    }

    operator fun invoke(activity: AppCompatActivity): FavoritesViewModel {
        return provide(activity)
    }

    // Internal API
    private fun provide(fragment: Fragment): FavoritesViewModel {
        return ViewModelProviders.of(fragment, viewModelFactory).get(FavoritesViewModel::class.java)
    }

    private fun provide(activity: AppCompatActivity): FavoritesViewModel {
        return ViewModelProviders.of(activity, viewModelFactory).get(FavoritesViewModel::class.java)
    }
}
