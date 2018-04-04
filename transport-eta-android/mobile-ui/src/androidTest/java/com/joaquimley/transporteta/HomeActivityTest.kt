package com.joaquimley.transporteta

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.joaquimley.transporteta.presentation.HomeViewModel
import com.joaquimley.transporteta.sms.SmsBroadcastReceiver
import com.joaquimley.transporteta.sms.SmsController
import com.nhaarman.mockito_kotlin.mock

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.runners.JUnit4

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.joaquimley.transporteta", appContext.packageName)
    }
}