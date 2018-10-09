package com.joaquimley.transporteta.ui.di.component

import com.joaquimley.transporteta.ui.di.module.SmsControllerModule
import dagger.Subcomponent

@Subcomponent(modules = [
    SmsControllerModule::class
])
interface ControllerSubComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): ControllerSubComponent
    }
}