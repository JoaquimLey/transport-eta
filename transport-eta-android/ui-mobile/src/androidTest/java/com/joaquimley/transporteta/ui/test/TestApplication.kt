package com.joaquimley.transporteta.ui.test

import android.app.Activity
import android.app.Application
import androidx.test.InstrumentationRegistry
import com.joaquimley.transporteta.ui.di.component.DaggerTestAppComponent
import com.joaquimley.transporteta.ui.di.component.TestAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class TestApplication: Application(), HasActivityInjector {

    private lateinit var appComponent: TestAppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerTestAppComponent.builder().application(this).build()
        appComponent.inject(this)
    }

    @Inject lateinit var injector: DispatchingAndroidInjector<Activity>
    override fun activityInjector(): AndroidInjector<Activity> {
        return injector
    }

    companion object {
        @JvmStatic
        fun appComponent(): TestAppComponent {
            return (InstrumentationRegistry.getTargetContext().applicationContext as TestApplication).appComponent
        }
    }

}