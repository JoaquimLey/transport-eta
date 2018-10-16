package com.joaquimley.transporteta.ui.di.module

import com.joaquimley.transporteta.data.mapper.DataTransportMapper
import com.joaquimley.transporteta.presentation.mapper.PresentationTransportMapper
import com.joaquimley.transporteta.sharedpreferences.mapper.SharedPrefTransportMapper
import dagger.Module
import dagger.Provides

@Module
class MapperModule {

    @Provides
    fun sharedPrefsTransportMapper(): SharedPrefTransportMapper {
        return SharedPrefTransportMapper()
    }

    @Provides
    fun presentationTransportMapper(): PresentationTransportMapper {
        return PresentationTransportMapper()
    }

    @Provides
    fun dataTransportMapper(): DataTransportMapper {
        return DataTransportMapper()
    }

}
