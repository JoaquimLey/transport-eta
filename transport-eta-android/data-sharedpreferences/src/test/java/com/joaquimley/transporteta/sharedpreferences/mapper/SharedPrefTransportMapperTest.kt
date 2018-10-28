package com.joaquimley.transporteta.sharedpreferences.mapper

import com.joaquimley.transporteta.data.model.TransportEntity
import com.joaquimley.transporteta.sharedpreferences.factory.SharedPrefTransportFactory
import com.joaquimley.transporteta.sharedpreferences.model.SharedPrefTransport
import org.junit.Before
import org.junit.Test


class SharedPrefTransportMapperTest {

    private val robot = Robot()
    private lateinit var mapper: SharedPrefTransportMapper

    @Before
    fun setup() {
        mapper = SharedPrefTransportMapper()
    }

    @Test
    fun fromCacheStringToModel() {
        // Assemble
        val stubbedSharedPrefTransport = SharedPrefTransportFactory.makeSharedPrefTransport()
        val stringCounterPart = SharedPrefTransportFactory.makeSharedPrefTransportString(stubbedSharedPrefTransport.id, stubbedSharedPrefTransport.name,
                stubbedSharedPrefTransport.code, stubbedSharedPrefTransport.latestEta, stubbedSharedPrefTransport.isFavorite,
                stubbedSharedPrefTransport.type, stubbedSharedPrefTransport.lastUpdated, stubbedSharedPrefTransport.slot?.name
                ?: "")
        // Act
        val modelMappedFromString = mapper.fromCacheString(stringCounterPart)

        // Assert
        assert(robot.areItemsTheSame(modelMappedFromString, stubbedSharedPrefTransport))
    }

    @Test
    fun fromModelToCacheString() {
        // Assemble
        val stubbedSharedPrefTransport = SharedPrefTransportFactory.makeSharedPrefTransport()
        val stringCounterPart = SharedPrefTransportFactory.makeSharedPrefTransportString(stubbedSharedPrefTransport.id, stubbedSharedPrefTransport.name,
                stubbedSharedPrefTransport.code, stubbedSharedPrefTransport.latestEta, stubbedSharedPrefTransport.isFavorite,
                stubbedSharedPrefTransport.type, stubbedSharedPrefTransport.lastUpdated, stubbedSharedPrefTransport.slot?.name
                ?: "")
        // Act
        val mappedString = mapper.toCacheString(stubbedSharedPrefTransport)
        // Assert
        assert(robot.areItemsTheSame(mappedString, stringCounterPart))
    }

    @Test
    fun fromEntityToModel() {
        // Assemble
        val stubbed = SharedPrefTransportFactory.makeTransportEntity()
        // Act
        val mapped = mapper.toSharedPref(stubbed)
        // Assert
        assert(robot.areItemsTheSame(mapped, stubbed))
    }

    @Test
    fun fromEntityListToModelList() {
        // Assemble
        val stubbed = SharedPrefTransportFactory.makeTransportEntityList(5)
        // Act
        val mapped = mapper.toSharedPref(stubbed)
        // Assert
        assert(robot.areItemsTheSame(mapped, stubbed))
    }

    @Test
    fun fromModelToEntity() {
        // Assemble
        val stubbed = SharedPrefTransportFactory.makeSharedPrefTransport()
        // Act
        val mapped = mapper.toEntity(stubbed)
        // Assert
        assert(robot.areItemsTheSame(stubbed, mapped))
    }

    @Test
    fun fromModelListToEntityList() {
        // Assemble
        val stubbed = SharedPrefTransportFactory.makeSharedPrefTransportList(5)
        // Act
        val mapped = mapper.toEntity(stubbed)
        // Assert
        assert(robot.areItemsTheSame(stubbed, mapped))
    }

    inner class Robot {

        fun areItemsTheSame(sharedPrefTransportStringLeft: String, sharedPrefTransportStringRight: String): Boolean {
            return sharedPrefTransportStringLeft == sharedPrefTransportStringRight
        }

        fun areItemsTheSame(sharedPrefTransportLeft: SharedPrefTransport, sharedPrefTransportRight: SharedPrefTransport): Boolean {
            return sharedPrefTransportLeft == sharedPrefTransportRight
        }

        fun areItemsTheSame(sharedPrefTransport: SharedPrefTransport, transportEntity: TransportEntity): Boolean {
            return sharedPrefTransport.id == transportEntity.id &&
                    sharedPrefTransport.code == transportEntity.code &&
                    sharedPrefTransport.latestEta == transportEntity.latestEta &&
                    transportEntity.isFavorite == transportEntity.isFavorite &&
                    sharedPrefTransport.type == transportEntity.type
        }

        fun areItemsTheSame(sharedPrefTransportList: List<SharedPrefTransport>, transportEntity: List<TransportEntity>): Boolean {
            for (transport in sharedPrefTransportList.withIndex()) {
                if (!areItemsTheSame(sharedPrefTransportList[transport.index], transportEntity[transport.index])) {
                    return false
                }
            }
            return true
        }
    }
}
