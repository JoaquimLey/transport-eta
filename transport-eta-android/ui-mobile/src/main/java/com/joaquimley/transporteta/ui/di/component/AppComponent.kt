package com.joaquimley.transporteta.ui.di.component

import android.app.Application
import com.joaquimley.transporteta.ui.App
import com.joaquimley.transporteta.ui.di.module.ActivityBindingModule
import com.joaquimley.transporteta.ui.di.module.AppModule
import com.joaquimley.transporteta.ui.di.module.SmsControllerModule
import com.joaquimley.transporteta.ui.di.scope.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@PerApplication
@Component(modules = [
    AppModule::class,
    ActivityBindingModule::class,
    AndroidSupportInjectionModule::class,
    SmsControllerModule::class
])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}
