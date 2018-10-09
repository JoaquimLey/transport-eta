package com.joaquimley.transporteta.ui.di.component

import com.joaquimley.transporteta.ui.di.module.DataSourceModule
import com.joaquimley.transporteta.ui.di.module.DataStoreModule
import com.joaquimley.transporteta.ui.di.module.RepositoryModule
import com.joaquimley.transporteta.ui.di.module.SmsControllerModule
import dagger.Subcomponent

@Subcomponent(modules = [
    DataSourceModule::class,
    DataStoreModule::class
])
interface DataSubComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): DataSubComponent
    }
}