package com.joaquimley.transporteta.ui.di.component

import com.joaquimley.transporteta.ui.di.module.HomeFragmentsBuildersModule
import com.joaquimley.transporteta.ui.injection.scope.PerActivity
import com.joaquimley.transporteta.ui.testing.TestFragmentActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TestActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(HomeFragmentsBuildersModule::class))
    abstract fun bindTestFragmentActivity(): TestFragmentActivity
}