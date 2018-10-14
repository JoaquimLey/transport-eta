package com.joaquimley.transporteta.sharedpreferences

import com.joaquimley.transporteta.sharedpreferences.mapper.SharedPrefTransportMapper
import com.nhaarman.mockitokotlin2.mock
import org.junit.After
import org.junit.Test

import org.junit.Before


// Roboeletric
class FrameworkLocalStorageTest {

    private val robot = Robot()

    //    private val mockApplicationContext = mock</Use Roboeletric for mock?>()
    private val mockMapper = mock<SharedPrefTransportMapper>()


    @Before
    fun setup() {

    }

    @After
    fun tearDown() {

    }

    @Test
    fun assertThisBoi() {
        val sum = 4 + 4
        val result = 8
        assert(sum == result)
    }


    inner class Robot {
        // TODO
    }

}