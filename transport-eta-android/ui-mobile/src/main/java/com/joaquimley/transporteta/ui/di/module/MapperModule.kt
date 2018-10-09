package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.data.mapper.DataTransportMapper
import com.joaquimley.transporteta.presentation.mapper.PresentationTransportMapper
import dagger.Module
import dagger.Provides

@Module
class MapperModule {

    @Provides
    fun presentationTransportMapper(): PresentationTransportMapper {
        return PresentationTransportMapper()
    }

    @Provides
    fun dataTransportMapper(): DataTransportMapper {
        return DataTransportMapper()
    }

}
