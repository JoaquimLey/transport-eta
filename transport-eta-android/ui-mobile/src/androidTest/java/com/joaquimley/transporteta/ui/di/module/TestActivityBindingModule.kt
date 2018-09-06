package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.ui.di.scope.PerActivity
import com.joaquimley.transporteta.ui.testing.TestFragmentActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TestActivityBindingModule {

    @PerActivity // HomeFragmentBuildersModule::class
    @ContributesAndroidInjector(modules = [
        TestFragmentActivityModule::class,
        TestFragmentsBuildersModule::class
    ])
    abstract fun bindTestFragmentActivity(): TestFragmentActivity
}