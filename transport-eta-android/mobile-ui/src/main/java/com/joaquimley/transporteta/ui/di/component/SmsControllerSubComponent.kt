package com.joaquimley.transporteta.ui.di.component

import com.joaquimley.transporteta.ui.di.module.SmsControllerModule
import dagger.Subcomponent

@Subcomponent(modules = [(SmsControllerModule::class)])
interface SmsControllerSubComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): SmsControllerSubComponent
    }
}