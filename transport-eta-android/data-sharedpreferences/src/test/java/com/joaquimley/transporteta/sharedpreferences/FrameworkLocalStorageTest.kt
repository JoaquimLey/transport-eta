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

class FrameworkLocalStorageTest {

    private val robot = Robot()

    private val mockSharedPreferencesEditor = mock<SharedPreferences.Editor>()
    private val mockSharedPreferences = mock<SharedPreferences>()
    private val mockMapper = mock<SharedPrefTransportMapper>()

    private lateinit var frameworkLocalStorage: FrameworkLocalStorage


    @Before
    fun setup() {
        whenever(mockSharedPreferences.edit()).then { mockSharedPreferencesEditor }
        frameworkLocalStorage = FrameworkLocalStorageImpl(mockSharedPreferences, mockMapper)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun allDataIsFetchedAtStartup() {
        // Assemble
        val modelStringOne = robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.ONE)
        val modelStringTwo = robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.TWO)
        val modelStringThree = robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.THREE)

        robot.stubMapperFromStringToModel(modelStringOne)
        robot.stubMapperFromStringToModel(modelStringTwo)
        robot.stubMapperFromStringToModel(modelStringThree)

        // Act
        // Nothing <->
        // Assert
        verify(mockSharedPreferences, times(1)).getString(FrameworkLocalStorageImpl.Slot.ONE.slotName, null)
        verify(mockSharedPreferences, times(1)).getString(FrameworkLocalStorageImpl.Slot.TWO.slotName, null)
        verify(mockSharedPreferences, times(1)).getString(FrameworkLocalStorageImpl.Slot.THREE.slotName, null)
    }

    @Test
    fun sharedPreferencesChangesAreObservedAtStartup() {
        // Assemble
        // Nothing <->

        // Act
        // Nothing <->

        // Assert
        verify(mockSharedPreferences, times(1)).registerOnSharedPreferenceChangeListener(any())
    }

    @Test
    fun saveTransportCompletes() {
        // Assemble
        val stubEntity = SharedPrefTransportFactory.makeTransportEntity()
        val stubModel = robot.stubMapperFromEntityToModel(stubEntity)
        val stubString = robot.stubMapperFromModelToString(stubModel)
        robot.stubSharedPrefSaveToSlotSuccess(stubString, FrameworkLocalStorageImpl.Slot.ONE)
        // Act
        val testObserver = frameworkLocalStorage.saveTransport(stubEntity).test()
        // Assert
        testObserver.assertComplete()
    }

    @Test
    fun saveTransportCallsCorrectMethodOnSharedPrefs() {
        // Assemble
        val stubEntity = SharedPrefTransportFactory.makeTransportEntity()
        val stubModel = robot.stubMapperFromEntityToModel(stubEntity)
        val stubString = robot.stubMapperFromModelToString(stubModel)
        val stubSlot = FrameworkLocalStorageImpl.Slot.ONE

        robot.stubSharedPrefSaveToSlotSuccess(stubString, stubSlot)
        // Act
        frameworkLocalStorage.saveTransport(stubEntity).test()
        // Assert
        verify(mockSharedPreferences, times(1)).edit()
        verify(mockSharedPreferencesEditor, times(1)).putString(stubSlot.slotName, stubString)
    }

    @Test
    @Ignore("Lacking implementation")
    fun saveTransportSavesToCorrectSlot() {
        // Assemble
        val stubEntity = SharedPrefTransportFactory.makeTransportEntity()
        val stubModel = robot.stubMapperFromEntityToModel(stubEntity)
        val stubString = robot.stubMapperFromModelToString(stubModel)

        // Slot number 2 is free
        robot.stubSharedPrefGetFromSlotIsEmpty(slot = FrameworkLocalStorageImpl.Slot.TWO)
        // Slot number 1 has data
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.ONE)
        // Slot number 3 has data
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.THREE)

//        robot.stubSharedPrefSaveToSlotSuccess(stubString, FrameworkLocalStorageImpl.Slot.TWO)

        // Act
        frameworkLocalStorage.saveTransport(stubEntity).subscribe()
        // Assert
        verify(mockSharedPreferences, times(1)).edit()
        verify(mockSharedPreferencesEditor, times(1)).putString(FrameworkLocalStorageImpl.Slot.TWO.slotName, stubString)
    }

    inner class Robot {

        fun stubSharedPrefSaveToSlotSuccess(sharedPrefTransportString: String = SharedPrefTransportFactory.makeSharedPrefTransportString(), slot: FrameworkLocalStorageImpl.Slot): String {
            whenever(mockSharedPreferencesEditor.putString(slot.slotName, sharedPrefTransportString)).then { mockSharedPreferencesEditor }
            whenever(mockSharedPreferencesEditor.apply()).then { true }
            return sharedPrefTransportString
        }

        fun stubSharedPrefGetFromSlotHasData(sharedPrefTransportString: String = SharedPrefTransportFactory.makeSharedPrefTransportString(), slot: FrameworkLocalStorageImpl.Slot): String {
            whenever(mockSharedPreferences.getString(slot.slotName, null)).then { sharedPrefTransportString }
            return sharedPrefTransportString
        }

        fun stubSharedPrefGetFromSlotIsEmpty(slot: FrameworkLocalStorageImpl.Slot) {
            whenever(mockSharedPreferences.getString(slot.slotName, null)).then { null }
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