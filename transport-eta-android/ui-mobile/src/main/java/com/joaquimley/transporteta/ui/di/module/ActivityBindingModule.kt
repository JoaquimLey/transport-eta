package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.ui.di.scope.PerActivity
import com.joaquimley.transporteta.ui.home.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [
        HomeActivityModule::class
//        HomeActivityFragmentBuildersModule::class
    ])
    abstract fun bindHomeActivity(): HomeActivity
}