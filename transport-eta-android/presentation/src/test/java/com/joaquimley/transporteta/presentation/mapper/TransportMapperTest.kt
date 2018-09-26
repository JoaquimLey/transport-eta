package com.joaquimley.transporteta.presentation.mapper

import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.presentation.model.TransportView
import com.joaquimley.transporteta.presentation.util.factory.TransportFactory
import org.junit.Before
import org.junit.Test


class TransportMapperTest {

    private val robot = Robot()
    private lateinit var mapper: TransportMapper

    @Before
    fun setup() {
        mapper = TransportMapper()
    }

    @Test
    fun fromModelToView() {
        // Assemble
        val stubbed = TransportFactory.makeTransport()
        // Act
        val mapped = mapper.toView(stubbed)
        // Assert
        assert(robot.areItemsTheSame(stubbed, mapped))
    }

    @Test
    fun fromViewToModel() {
        // Assemble
        val stubbed = TransportFactory.makeTransportView()
        // Act
        val mapped = mapper.toModel(stubbed)
        // Assert
        assert(robot.areItemsTheSame(mapped, stubbed))
    }

    inner class Robot {
        fun areItemsTheSame(transport: Transport, transportView: TransportView): Boolean {
            return transport.id == transportView.id &&
                    transport.code == transportView.code &&
                    transport.latestEta == transportView.latestEta &&
                    transport.isFavorite == transportView.isFavorite &&
                    transport.type == transportView.type.name.toLowerCase()
        }
    }

}