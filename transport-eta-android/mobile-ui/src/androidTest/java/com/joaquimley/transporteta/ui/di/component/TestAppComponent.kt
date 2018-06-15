package com.joaquimley.transporteta.ui.di.component

import android.app.Application
import com.joaquimley.transporteta.ui.di.module.SmsControllerModule
import com.joaquimley.transporteta.ui.di.module.TestActivityBindingModule
import com.joaquimley.transporteta.ui.di.module.TestAppModule
import com.joaquimley.transporteta.ui.injection.scope.PerApplication
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

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestAppComponent
    }

    fun inject(application: TestApplication)
}