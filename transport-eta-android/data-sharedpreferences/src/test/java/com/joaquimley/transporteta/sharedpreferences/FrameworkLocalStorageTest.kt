package com.joaquimley.transporteta.sharedpreferences

import android.content.SharedPreferences
import com.joaquimley.transporteta.data.model.TransportEntity
import com.joaquimley.transporteta.data.source.FrameworkLocalStorage
import com.joaquimley.transporteta.sharedpreferences.factory.SharedPrefDataFactory
import com.joaquimley.transporteta.sharedpreferences.factory.SharedPrefTransportFactory
import com.joaquimley.transporteta.sharedpreferences.mapper.SharedPrefTransportMapper
import com.joaquimley.transporteta.sharedpreferences.model.SharedPrefTransport
import com.nhaarman.mockitokotlin2.*
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import kotlin.test.assertEquals

class FrameworkLocalStorageTest {

    // TODO -> These tests are missing
    // Changes aren't being reactively sent
    // DELETE Transport -> Slot is null so there is never a transport to be deleted
    // DELETE Transport -> Save Transport is doesn't have a slot:facepalm:
    // DELETE Transport -> Save Transport is saved with the FOUND slot


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
        val modelStringOne = robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE)
        val modelStringTwo = robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_TWO)
        val modelStringThree = robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE)

        robot.stubMapperFromStringToModel(modelStringOne, FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE)
        robot.stubMapperFromStringToModel(modelStringTwo, FrameworkLocalStorageImpl.Slot.SAVE_SLOT_TWO)
        robot.stubMapperFromStringToModel(modelStringThree, FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE)

        // Act
        // Nothing <->
        // Assert
        verify(mockSharedPreferences, times(1)).getString(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE.name, null)
        verify(mockSharedPreferences, times(1)).getString(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_TWO.name, null)
        verify(mockSharedPreferences, times(1)).getString(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE.name, null)
    }

    @Test
    @Ignore("Currently this 'observation' is done manually, still haven't made it work")
    fun sharedPreferencesChangesAreObservedAtStartup() {
        // Assemble
        // Nothing <->

        // Act
        // Nothing <->

        // Assert
        verify(mockSharedPreferences, times(1)).registerOnSharedPreferenceChangeListener(any())
    }


    @Test
    fun getTransportByIdFindsTheCorrectDataOnSlotOne() {
        // Assemble
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_TWO)
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE)

        val dataString = robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE)
        val stubModel = robot.stubMapperFromStringToModel(dataString, FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE)
        val stubEntity = robot.stubMapperFromModelToEntity(stubModel)
        // Act
        val testObserver = frameworkLocalStorage.getTransport(stubModel.id).test()
        // Assert
        testObserver.assertValue { it == stubEntity }
    }

    @Test
    fun getTransportByIdFindsTheCorrectDataOnSlotTwo() {
        // Assemble
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE)
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE)

        val dataString = robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_TWO)
        val stubModel = robot.stubMapperFromStringToModel(dataString, FrameworkLocalStorageImpl.Slot.SAVE_SLOT_TWO)
        val stubEntity = robot.stubMapperFromModelToEntity(stubModel)
        // Act
        val testObserver = frameworkLocalStorage.getTransport(stubModel.id).test()
        // Assert
        testObserver.assertValue { it == stubEntity }
    }

    @Test
    fun getTransportByIdFindsTheCorrectDataOnSlotThree() {
        // Assemble
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE)
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_TWO)

        val dataString = robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE)
        val stubModel = robot.stubMapperFromStringToModel(dataString, FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE)
        val stubEntity = robot.stubMapperFromModelToEntity(stubModel)
        // Act
        val testObserver = frameworkLocalStorage.getTransport(stubModel.id).test()
        // Assert
        testObserver.assertValue { it == stubEntity }
    }

    @Test
    fun getTransportByIdThrowsErrorWhenNoDataIsFound() {
        // TODO - Should this single return a nullable TransportEntity instead of Error?

        // Assemble
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE)
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_TWO)
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE)

        // Act
        val testObserver = frameworkLocalStorage.getTransport("This will never be an id").test()
        // Assert
        testObserver.assertFailure(Throwable::class.java)
    }

    @Test
    @Ignore("It's not supposed to complete since it's supposed to listen to data changes?")
    fun getAllTransportsCompletes() {
        // Assemble
        // <-> Nothing
        // Act
        val testObserver = frameworkLocalStorage.getAll().test()
        // Assert
        testObserver.assertComplete()
    }

    @Test
    fun getAllTransportsReturnsEmptyListWhenThereIsNoData() {
        // Assemble
        robot.stubSharedPrefGetFromSlotIsEmpty(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE)
        robot.stubSharedPrefGetFromSlotIsEmpty(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_TWO)
        robot.stubSharedPrefGetFromSlotIsEmpty(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE)
        // Act
        val testObserver = frameworkLocalStorage.getAll().test()
        // Assert
        testObserver.assertValue { it.isEmpty() }
    }

    @Test
    fun getAllTransportsReturnsCorrectData() {
        // Assemble
        val dataString1 = robot.stubSharedPrefGetFromSlotHasData(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE)
        val dataString2 = robot.stubSharedPrefGetFromSlotHasData(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_TWO)
        val dataString3 = robot.stubSharedPrefGetFromSlotHasData(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE)

        val stubModel1 = robot.stubMapperFromStringToModel(dataString1, FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE)
        val stubModel2 = robot.stubMapperFromStringToModel(dataString2, FrameworkLocalStorageImpl.Slot.SAVE_SLOT_TWO)
        val stubModel3 = robot.stubMapperFromStringToModel(dataString3, FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE)

        val stubEntity1 = robot.stubMapperFromModelToEntity(stubModel1)
        val stubEntity2 = robot.stubMapperFromModelToEntity(stubModel2)
        val stubEntity3 = robot.stubMapperFromModelToEntity(stubModel3)

        val list = mutableListOf<TransportEntity>().apply {
            add(stubEntity1)
            add(stubEntity2)
            add(stubEntity3)
        }

        // Act
        val testObserver = frameworkLocalStorage.getAll().test()
        // Assert
        testObserver.assertValue(list)
    }

    @Test
    fun saveTransportCompletes() {
        // Assemble
        val stubEntity = SharedPrefTransportFactory.makeTransportEntity()
        val stubModel = robot.stubMapperFromEntityToModel(stubEntity)
        val stubString = robot.stubMapperFromModelToString(stubModel)
        robot.stubSharedPrefSaveToSlotSuccess(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE, stubString)
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
        val stubSlot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE

        robot.stubSharedPrefSaveToSlotSuccess(stubSlot, stubString)
        // Act
        frameworkLocalStorage.saveTransport(stubEntity).test()
        // Assert
        verify(mockSharedPreferences, times(1)).edit()
        verify(mockSharedPreferencesEditor, times(1)).putString(stubSlot.name, stubString)
    }

    @Test
    fun saveTransportSavesToCorrectToSlotOneWhenAllSlotsAreFree() {
        // Assemble
        // All Slots are free
        robot.stubSharedPrefGetFromSlotIsEmpty(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE)
        robot.stubSharedPrefGetFromSlotIsEmpty(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_TWO)
        robot.stubSharedPrefGetFromSlotIsEmpty(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE)

        val stubEntity = SharedPrefTransportFactory.makeTransportEntity()
        val stubModel = robot.stubMapperFromEntityToModel(stubEntity)
        val stubString = robot.stubMapperFromModelToString(stubModel)
        robot.stubSharedPrefSaveToSlotSuccess(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE, stubString)
        // Act
        frameworkLocalStorage.saveTransport(stubEntity).test()
        // Assert
        verify(mockSharedPreferencesEditor, times(1)).putString(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE.name, stubString)
    }

    @Test
    fun saveTransportSavesToCorrectSlotWhenSlotOneIsFree() {
        // Assemble
        // Slot number 1 is free
        robot.stubSharedPrefGetFromSlotIsEmpty(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE)
        // Slot number 2 has data
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_TWO)
        // Slot number 3 has data
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE)

        val stubEntity = SharedPrefTransportFactory.makeTransportEntity()
        val stubModel = robot.stubMapperFromEntityToModel(stubEntity)
        val stubString = robot.stubMapperFromModelToString(stubModel)
        robot.stubSharedPrefSaveToSlotSuccess(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE, stubString)
        // Act
        frameworkLocalStorage.saveTransport(stubEntity).test()
        // Assert
        verify(mockSharedPreferencesEditor, times(1)).putString(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE.name, stubString)
    }

    @Test
    fun saveTransportSavesToCorrectSlotWhenSlotTwoIsFree() {
        // Assemble
        // Slot number 2 is free
        robot.stubSharedPrefGetFromSlotIsEmpty(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_TWO)
        // Slot number 1 has data
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE)
        // Slot number 3 has data
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE)

        val stubEntity = SharedPrefTransportFactory.makeTransportEntity()
        val stubModel = robot.stubMapperFromEntityToModel(stubEntity)
        val stubString = robot.stubMapperFromModelToString(stubModel)
        robot.stubSharedPrefSaveToSlotSuccess(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_TWO, stubString)
        // Act
        frameworkLocalStorage.saveTransport(stubEntity).test()
        // Assert
        verify(mockSharedPreferencesEditor, times(1)).putString(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_TWO.name, stubString)
    }

    @Test
    fun saveTransportSavesToCorrectSlotWhenSlotThreeIsFree() {
        // Assemble
        // Slot number 3 is free
        robot.stubSharedPrefGetFromSlotIsEmpty(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE)
        // Slot number 1 has data
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE)
        // Slot number 2 has data
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_TWO)

        val stubEntity = SharedPrefTransportFactory.makeTransportEntity()
        val stubModel = robot.stubMapperFromEntityToModel(stubEntity)
        val stubString = robot.stubMapperFromModelToString(stubModel)
        robot.stubSharedPrefSaveToSlotSuccess(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE, stubString)
        // Act
        frameworkLocalStorage.saveTransport(stubEntity).test()
        // Assert
        verify(mockSharedPreferencesEditor, times(1)).putString(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE.name, stubString)
    }

    @Test
    fun saveTransportThrowsErrorIfAllSlotsHaveData() {
        // Assemble
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE)
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_TWO)
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE)

        val stubEntity = SharedPrefTransportFactory.makeTransportEntity()
        val stubModel = robot.stubMapperFromEntityToModel(stubEntity)
        val stubString = robot.stubMapperFromModelToString(stubModel)
        robot.stubSharedPrefSaveToSlotSuccess(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE, stubString)
        // Act
        val testObserver = frameworkLocalStorage.saveTransport(stubEntity).test()
        // Assert
        testObserver.assertFailure(Throwable::class.java)
    }

    @Test
    fun deleteTransportCompletes() {
        // Assemble
        val stubModel = robot.stubMapperFromEntityToModel()
        val stubString = robot.stubMapperFromModelToString(stubModel)
        robot.stubSharedPrefGetFromSlotHasData(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE, stubString)
        robot.stubMapperFromStringToModel(stubString, FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE, stubModel)

        robot.stubSharedPrefDeleteTransportSuccess(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE.name)
        // Act
        val testObserver = frameworkLocalStorage.deleteTransport(stubModel.id).test()
        // Assert
        testObserver.assertComplete()
    }

    @Test
    fun deleteTransportCallsCorrectMethodOnSharedPrefs() {
        // Assemble
        val stubModel = robot.stubMapperFromEntityToModel()
        val stubString = robot.stubMapperFromModelToString(stubModel)
        robot.stubSharedPrefGetFromSlotHasData(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE, stubString)
        robot.stubMapperFromStringToModel(stubString, FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE, stubModel)

        robot.stubSharedPrefDeleteTransportSuccess(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE.name)
        // Act
        frameworkLocalStorage.deleteTransport(stubModel.id).test()
        // Assert
        verify(mockSharedPreferencesEditor, atLeastOnce()).remove(stubModel.slot?.name)
    }

    @Test
    fun deleteTransportThrowsErrorWhenAllSlotsAreFree() {
        // Assemble
        // All Slots are free
        robot.stubSharedPrefGetFromSlotIsEmpty(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE)
        robot.stubSharedPrefGetFromSlotIsEmpty(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_TWO)
        robot.stubSharedPrefGetFromSlotIsEmpty(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE)

        robot.stubSharedPrefDeleteTransportSuccess(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE.name)
        // Act
        val testObserver = frameworkLocalStorage.deleteTransport(SharedPrefDataFactory.randomUuid()).test()
        // Assert
        testObserver.assertFailure(Throwable::class.java)
    }

    @Test
    fun deleteTransportThrowsErrorWhenTransportWithPassedIdIsNotFound() {
        // Assemble
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE)
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_TWO)
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE)

        robot.stubSharedPrefDeleteTransportSuccess(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE.name)
        // Act
        val testObserver = frameworkLocalStorage.deleteTransport(SharedPrefDataFactory.randomUuid()).test()
        // Assert
        testObserver.assertFailure(Throwable::class.java)
    }


    @Test
    fun clearAllCompletes() {
        // Assemble
        robot.stubSharedPrefDeleteTransportSuccess(anyString())
        // Act
        val testObserver = frameworkLocalStorage.clearAll().test()
        // Assert
        testObserver.assertComplete()
    }

    @Test
    fun clearAllRemovesAllData() {
        // Assemble
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE)
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_TWO)
        robot.stubSharedPrefGetFromSlotHasData(slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE)

        robot.stubSharedPrefDeleteTransportSuccess(anyString())
        // Act
        frameworkLocalStorage.clearAll().test()
        // Assert
        verify(mockSharedPreferencesEditor, atLeastOnce()).remove(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE.name)
        verify(mockSharedPreferencesEditor, atLeastOnce()).remove(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_TWO.name)
        verify(mockSharedPreferencesEditor, atLeastOnce()).remove(FrameworkLocalStorageImpl.Slot.SAVE_SLOT_THREE.name)
    }


    inner class Robot {

        fun stubSharedPrefSaveToSlotSuccess(slot: FrameworkLocalStorageImpl.Slot, sharedPrefTransportString: String = SharedPrefTransportFactory.makeSharedPrefTransportString(slot = slot.name)): String {
            whenever(mockSharedPreferencesEditor.putString(slot.name, sharedPrefTransportString)).then { mockSharedPreferencesEditor }
            whenever(mockSharedPreferencesEditor.apply()).then { Unit }
            return sharedPrefTransportString
        }

        fun stubSharedPrefDeleteTransportSuccess(slotName: String) {
            whenever(mockSharedPreferencesEditor.remove(slotName)).then { mockSharedPreferencesEditor }
            whenever(mockSharedPreferencesEditor.apply()).then { mockSharedPreferencesEditor }
        }

        fun stubSharedPrefGetFromSlotHasData(slot: FrameworkLocalStorageImpl.Slot, sharedPrefTransportString: String = SharedPrefTransportFactory.makeSharedPrefTransportString(slot = slot.name)): String {
            whenever(mockSharedPreferences.getString(slot.name, null)).then { sharedPrefTransportString }
            return sharedPrefTransportString
        }

        fun stubSharedPrefGetFromSlotIsEmpty(slot: FrameworkLocalStorageImpl.Slot) {
            whenever(mockSharedPreferences.getString(slot.name, null)).then { null }
        }

        fun stubMapperFromModelToString(sharedPrefTransport: SharedPrefTransport = SharedPrefTransportFactory.makeSharedPrefTransport(), sharedPrefTransportString: String = SharedPrefDataFactory.randomUuid()): String {
            whenever(mockMapper.toCacheString(sharedPrefTransport)).then { sharedPrefTransportString }
            return sharedPrefTransportString
        }

        fun stubMapperFromStringToModel(sharedPrefTransportString: String, slot: FrameworkLocalStorageImpl.Slot, sharedPrefTransport: SharedPrefTransport = SharedPrefTransportFactory.makeSharedPrefTransport(slot = slot)): SharedPrefTransport {
            whenever(mockMapper.fromCacheString(sharedPrefTransportString)).then { sharedPrefTransport }
            return sharedPrefTransport
        }

        fun stubMapperFromEntityToModel(transportEntity: TransportEntity = SharedPrefTransportFactory.makeTransportEntity(), sharedPrefTransport: SharedPrefTransport = SharedPrefTransportFactory.makeSharedPrefTransport()): SharedPrefTransport {
            whenever(mockMapper.toSharedPref(transportEntity)).then { sharedPrefTransport }
            return sharedPrefTransport
        }

        fun stubMapperFromModelToEntity(sharedPrefTransport: SharedPrefTransport = SharedPrefTransportFactory.makeSharedPrefTransport(), transportEntity: TransportEntity = SharedPrefTransportFactory.makeTransportEntity()): TransportEntity {
            whenever(mockMapper.toEntity(sharedPrefTransport)).then { transportEntity }
            return transportEntity

        }
    }

}