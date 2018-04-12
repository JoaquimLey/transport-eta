package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.ui.home.HomeActivity
import com.joaquimley.transporteta.ui.injection.scope.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(HomeActivityModule::class, HomeFragmentsBuildersModule::class))
    abstract fun bindHomeActivity(): HomeActivity
}