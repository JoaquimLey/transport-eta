package com.joaquimley.transporteta

import com.joaquimley.transporteta.sms.SmsBroadcastReceiver
import com.nhaarman.mockito_kotlin.mock
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class SmsControllerUnitTest {

    private lateinit var smsBroadcastReceiver: SmsBroadcastReceiver

    @Before
    fun setUp() {
        smsBroadcastReceiver = mock()
    }


//    @Test(expected = UnsupportedOperationException::class)
//    fun onSmsReceivedItemNexted() {
//         //TODO
//    }
}