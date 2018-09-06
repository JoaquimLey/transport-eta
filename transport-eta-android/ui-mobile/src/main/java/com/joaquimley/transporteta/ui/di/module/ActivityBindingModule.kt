package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.ui.home.HomeActivity
import com.joaquimley.transporteta.ui.di.scope.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [
        HomeActivityModule::class,
        HomeFragmentBuildersModule::class
    ])
    abstract fun bindHomeActivity(): HomeActivity
}