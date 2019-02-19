package com.joaquimley.transporteta.presentation.mapper

import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.presentation.model.TransportView
import com.joaquimley.transporteta.presentation.util.factory.TransportFactory
import org.junit.Before
import org.junit.Test


class PresentationTransportMapperTest {

    private val robot = Robot()
    private lateinit var mapper: PresentationTransportMapper

    @Before
    fun setup() {
        mapper = PresentationTransportMapper()
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
    fun fromModelListToViewList() {
        // Assemble
        val stubbed = TransportFactory.makeTransportList(5)
        // Act
        val mapped = mapper.toView(stubbed)
        // Assert
        assert(robot.areItemsInListTheSame(stubbed, mapped))
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

    @Test
    fun fromViewListToModelList() {
        // Assemble
        val stubbed = TransportFactory.makeTransportViewList(5)
        // Act
        val mapped = mapper.toModel(stubbed)
        // Assert
        assert(robot.areItemsInListTheSame(mapped, stubbed))
    }

    inner class Robot {
        fun areItemsInListTheSame(transportList: List<Transport>, transportViewList: List<TransportView>): Boolean {
            for (transport in transportList.withIndex()) {
                if (!areItemsTheSame(transportList[transport.index], transportViewList[transport.index])) {
                    return false
                }
            }
            return true
        }

        fun areItemsTheSame(transport: Transport, transportView: TransportView): Boolean {
            return transport.id == transportView.id &&
                    transport.name == transportView.name &&
                    transport.code == transportView.code &&
                    transport.latestEta == transportView.latestEta &&
                    transport.isFavorite == transportView.isFavorite &&
                    transport.type == transportView.type.name.toLowerCase()
        }
    }

}