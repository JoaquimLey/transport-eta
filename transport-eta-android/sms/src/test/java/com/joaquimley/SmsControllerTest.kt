package com.joaquimley

import com.joaquimley.transporteta.sms.SmsBroadcastReceiver
import com.joaquimley.transporteta.sms.SmsController
import com.joaquimley.transporteta.sms.SmsControllerImpl
import com.joaquimley.transporteta.sms.SmsSender
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test

class SmsControllerTest {

    private val robot = Robot()
    private val mockSmsBroadcastReceiver = mock<SmsBroadcastReceiver>()
    private val mockSmsSender = mock<SmsSender>()

    private lateinit var smsController: SmsController

    @Before
    fun setup() {
        robot.stubSmsBroadcastReceiverSuccess()
        smsController = SmsControllerImpl(mockSmsBroadcastReceiver, mockSmsSender)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun onCreationListensToSmsBroadcastReceiver() {
        // Assemble
        robot.stubSmsBroadcastReceiverSuccess()
        // Act
        // No action
        // Assert
        verify(mockSmsBroadcastReceiver, atLeastOnce()).observeServiceSms()
    }

    inner class Robot {
        fun stubSmsBroadcastReceiverSuccess(message: String = "randomString"): String {
            whenever(mockSmsBroadcastReceiver.observeServiceSms()).then { Observable.just(message) }
            return message
        }
    }
}