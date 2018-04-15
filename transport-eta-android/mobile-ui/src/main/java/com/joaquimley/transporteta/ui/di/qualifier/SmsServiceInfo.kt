package com.joaquimley.transporteta.ui.di.qualifier

import javax.inject.Qualifier

/**
 * Created by joaquimley on 18/06/2017.
 */

interface SmsServiceInfo {
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ServiceNumber

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ServiceBodyCode
}