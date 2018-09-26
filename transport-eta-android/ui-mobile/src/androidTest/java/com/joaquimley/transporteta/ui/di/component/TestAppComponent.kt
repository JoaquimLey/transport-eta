package com.joaquimley.transporteta.ui.di.component

import android.app.Application
import com.joaquimley.transporteta.domain.executor.PostExecutionThread
import com.joaquimley.transporteta.domain.repository.FavoritesRepository
import com.joaquimley.transporteta.ui.di.module.SmsControllerModule
import com.joaquimley.transporteta.ui.di.module.TestActivityBindingModule
import com.joaquimley.transporteta.ui.di.module.TestAppModule
import com.joaquimley.transporteta.ui.di.scope.PerApplication
import com.joaquimley.transporteta.ui.test.TestApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [
    TestAppModule::class,
    TestActivityBindingModule::class,
    SmsControllerModule::class,
    AndroidSupportInjectionModule::class
])
@PerApplication
interface TestAppComponent : AppComponent {

    fun favoritesRepository(): FavoritesRepository

    fun postExecutionThread(): PostExecutionThread

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): TestAppComponent.Builder

        fun build(): TestAppComponent
    }

    fun inject(application: TestApplication)
}