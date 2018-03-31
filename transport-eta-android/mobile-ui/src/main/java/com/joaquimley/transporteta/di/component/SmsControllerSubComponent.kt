package com.joaquimley.transporteta.di.component

import com.joaquimley.transporteta.di.module.SmsControllerModule
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(SmsControllerModule::class))
interface SmsControllerSubComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): SmsControllerSubComponent
    }
}