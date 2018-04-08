package com.joaquimley.transporteta.di.module

import com.joaquimley.transporteta.home.favorite.FavoritesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentsBuildersModule {

    @ContributesAndroidInjector(modules = arrayOf(FavouriteFragmentModule::class))
    abstract fun contributeFavouritesFragment(): FavoritesFragment
}
