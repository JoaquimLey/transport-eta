package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.ui.home.favorite.FavoritesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentBuildersModule {

    @ContributesAndroidInjector(modules = [
        HomeViewModelModule::class
    ])
    abstract fun contributeFavouritesFragment(): FavoritesFragment
}
