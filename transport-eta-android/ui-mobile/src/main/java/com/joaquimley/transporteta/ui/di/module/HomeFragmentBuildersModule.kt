package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.ui.home.favorite.FavoritesFragment
import dagger.Module

@Module
abstract class HomeFragmentBuildersModule {

//    @ContributesAndroidInjector(modules = [
//        ViewModelModule::class
//    ])
    abstract fun contributeFavouritesFragment(): FavoritesFragment
}
