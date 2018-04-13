package com.joaquimley.transporteta.ui.di.component

import android.app.Application
import com.joaquimley.transporteta.sms.SmsBroadcastReceiver
import com.joaquimley.transporteta.sms.SmsController
import com.joaquimley.transporteta.ui.testing.di.module.TestActivityBindingModule
import com.joaquimley.transporteta.ui.di.module.TestAppModule
import com.joaquimley.transporteta.ui.injection.scope.PerApplication
import com.joaquimley.transporteta.ui.test.TestApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = arrayOf(TestAppModule::class, TestActivityBindingModule::class,
        AndroidSupportInjectionModule::class))
@PerApplication
interface TestAppComponent : AppComponent {

    fun smsController(): SmsController

    fun smsBroadcastReceiver(): SmsBroadcastReceiver

    fun inject(application: TestApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestAppComponent
    }

}