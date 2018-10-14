package com.joaquimley.transporteta.ui.di.qualifier

import javax.inject.Qualifier

interface AndroidContext {
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ApplicationContext
}