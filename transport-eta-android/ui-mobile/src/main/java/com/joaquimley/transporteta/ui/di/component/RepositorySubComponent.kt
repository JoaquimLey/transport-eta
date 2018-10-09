package com.joaquimley.transporteta.ui.di.component

import com.joaquimley.transporteta.ui.di.module.MapperModule
import com.joaquimley.transporteta.ui.di.module.RepositoryModule
import dagger.Subcomponent

@Subcomponent(modules = [
    RepositoryModule::class,
    MapperModule::class
])
interface RepositorySubComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): RepositorySubComponent
    }
}