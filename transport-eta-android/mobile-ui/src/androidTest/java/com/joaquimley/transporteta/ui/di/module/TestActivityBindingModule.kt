package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.ui.injection.scope.PerActivity
import com.joaquimley.transporteta.ui.testing.TestFragmentActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TestActivityBindingModule {

    @PerActivity // HomeFragmentsBuildersModule::class
    @ContributesAndroidInjector(modules = arrayOf(TestFragmentActivityModule::class, TestHomeFragmentsBuildersModule::class))
    abstract fun bindTestFragmentActivity(): TestFragmentActivity
}