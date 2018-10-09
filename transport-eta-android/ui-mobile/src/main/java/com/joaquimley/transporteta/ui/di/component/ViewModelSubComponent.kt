package com.joaquimley.transporteta.ui.di.component

import com.joaquimley.transporteta.ui.di.module.ViewModelModule
import dagger.Subcomponent

@Subcomponent(modules = [
    ViewModelModule::class
])
interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelSubComponent
    }
}