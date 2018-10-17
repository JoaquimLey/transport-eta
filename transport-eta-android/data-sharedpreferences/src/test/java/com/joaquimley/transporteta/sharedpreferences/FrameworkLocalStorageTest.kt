package com.joaquimley.transporteta.sharedpreferences

import android.content.SharedPreferences
import com.joaquimley.transporteta.data.model.TransportEntity
import com.joaquimley.transporteta.data.source.FrameworkLocalStorage
import com.joaquimley.transporteta.sharedpreferences.factory.SharedPrefDataFactory
import com.joaquimley.transporteta.sharedpreferences.factory.SharedPrefTransportFactory
import com.joaquimley.transporteta.sharedpreferences.mapper.SharedPrefTransportMapper
import com.joaquimley.transporteta.sharedpreferences.model.SharedPrefTransport
import com.nhaarman.mockitokotlin2.*
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test


//@RunWith(RobolectricTestRunner::class)
class FrameworkLocalStorageTest {


    private val SHARED_PREFERENCES_NAME = "com.joaquimley.transporteta.sharedpreferences"

    private val robot = Robot()

//    private val robolectricSharedPreferences =  RuntimeEnvironment.application.applicationContext
//            .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    private val mockSharedPreferences = mock<SharedPreferences>()
    private val mockMapper = mock<SharedPrefTransportMapper>()

    private lateinit var frameworkLocalStorage: FrameworkLocalStorage


    @Before
    fun setup() {
        frameworkLocalStorage = FrameworkLocalStorageImpl(mockSharedPreferences, mockMapper)
//        frameworkLocalStorage = FrameworkLocalStorageImpl(robolectricSharedPreferences, mockMapper)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun allDataIsFetchedAtStartup() {
        // Assemble
        val modelStringOne = robot.stubSharedPrefGetFromSlotSuccess(slot = FrameworkLocalStorageImpl.Slot.ONE)
        val modelStringTwo = robot.stubSharedPrefGetFromSlotSuccess(slot = FrameworkLocalStorageImpl.Slot.TWO)
        val modelStringThree = robot.stubSharedPrefGetFromSlotSuccess(slot = FrameworkLocalStorageImpl.Slot.THREE)

        robot.stubMapperFromStringToModel(modelStringOne)
        robot.stubMapperFromStringToModel(modelStringTwo)
        robot.stubMapperFromStringToModel(modelStringThree)

        // Act
        // Nothing <->
        // Assert
        verify(mockSharedPreferences, times(1)).getString(FrameworkLocalStorageImpl.Slot.ONE.name, null)
        verify(mockSharedPreferences, times(1)).getString(FrameworkLocalStorageImpl.Slot.TWO.name, null)
        verify(mockSharedPreferences, times(1)).getString(FrameworkLocalStorageImpl.Slot.THREE.name, null)


//        val modelStringOne = SharedPrefTransportFactory.makeSharedPrefTransportString()
//        val modelStringTwo = SharedPrefTransportFactory.makeSharedPrefTransportString()
//        val modelStringThree = SharedPrefTransportFactory.makeSharedPrefTransportString()

//        robot.stubMapperFromStringToModel(modelStringOne)
//        robot.stubMapperFromStringToModel(modelStringTwo)
//        robot.stubMapperFromStringToModel(modelStringThree)
        // Act
        // Nothing <->
        // Assert
//        verify(robolectricSharedPreferences, times(1)).getString(FrameworkLocalStorageImpl.Slot.ONE.name, null)
//        verify(robolectricSharedPreferences, times(1)).getString(FrameworkLocalStorageImpl.Slot.TWO.name, null)
//        verify(robolectricSharedPreferences, times(1)).getString(FrameworkLocalStorageImpl.Slot.THREE.name, null)
    }

    @Test
    fun sharedPreferencesChangesAreObservedAtStartup() {
        // Assemble
        // Nothing <->

        // Act
        // Nothing <->

        // Assert
//        verify(robolectricSharedPreferences, times(1)).registerOnSharedPreferenceChangeListener(any())
        verify(mockSharedPreferences, times(1)).registerOnSharedPreferenceChangeListener(any())
    }

    @Test
    @Ignore("Lacking SharedPreferences mocking/roboeletric")
    fun saveTransportCompletes() {
        // Assemble
        val stubEntity = SharedPrefTransportFactory.makeTransportEntity()
        // Act
        val testObserver = frameworkLocalStorage.saveTransport(stubEntity).test()
        // Assert
        testObserver.assertComplete()
    }

    @Test
    @Ignore("Lacking SharedPreferences mocking/roboeletric")
    fun saveTransportCallsCorrectMethodOnSharedPrefs() {
        // Assemble
        val stubEntity = SharedPrefTransportFactory.makeTransportEntity()
        val stubModel = robot.stubMapperFromEntityToModel(stubEntity)
        val stubbedString = robot.stubMapperFromModelToString(stubModel)
        robot.stubSharedPrefGetFromSlotSuccess(stubbedString, FrameworkLocalStorageImpl.Slot.ONE)
        // Act
        frameworkLocalStorage.saveTransport(stubEntity)
        // Assert
//        verify(robolectricSharedPreferences, times(1)).edit().putString(any(), stubbedString).apply()
        verify(mockSharedPreferences, times(1)).edit().putString(any(), stubbedString).apply()
    }


    inner class Robot {


        fun stubSharedPrefSaveToSlotSuccess(sharedPrefTransportString: String = SharedPrefTransportFactory.makeSharedPrefTransportString(), slot: FrameworkLocalStorageImpl.Slot): String {
            whenever(mockSharedPreferences.edit().putString(slot.name, sharedPrefTransportString)).then { sharedPrefTransportString }
            return sharedPrefTransportString
        }

        fun stubSharedPrefGetFromSlotSuccess(sharedPrefTransportString: String = SharedPrefTransportFactory.makeSharedPrefTransportString(), slot: FrameworkLocalStorageImpl.Slot): String {
            whenever(mockSharedPreferences.getString(slot.name, null)).then { sharedPrefTransportString }
            return sharedPrefTransportString
        }

        fun stubMapperFromModelToString(sharedPrefTransport: SharedPrefTransport = SharedPrefTransportFactory.makeSharedPrefTransport(), sharedPrefTransportString: String = SharedPrefDataFactory.randomUuid()): String {
            whenever(mockMapper.toCacheString(sharedPrefTransport)).then { sharedPrefTransportString }
            return sharedPrefTransportString
        }

        fun stubMapperFromStringToModel(sharedPrefTransportString: String, sharedPrefTransport: SharedPrefTransport = SharedPrefTransportFactory.makeSharedPrefTransport()): SharedPrefTransport {
            whenever(mockMapper.fromCacheString(sharedPrefTransportString)).then { sharedPrefTransport }
            return sharedPrefTransport
        }

        fun stubMapperFromEntityToModel(transportEntity: TransportEntity = SharedPrefTransportFactory.makeTransportEntity(), sharedPrefTransport: SharedPrefTransport = SharedPrefTransportFactory.makeSharedPrefTransport()): SharedPrefTransport {
            whenever(mockMapper.toSharedPref(transportEntity)).then { sharedPrefTransport }
            return sharedPrefTransport
        }
    }

}