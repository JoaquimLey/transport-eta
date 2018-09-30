package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.presentation.mapper.TransportMapper
import dagger.Module
import dagger.Provides

@Module
class MapperModule {

    @Provides
    fun transportMapper(): TransportMapper {
        return TransportMapper()
    }

}