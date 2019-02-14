package com.joaquimley.transporteta.sharedpreferences.factory

import com.joaquimley.transporteta.sharedpreferences.factory.SharedPrefDataFactory.Factory.randomInt
import com.joaquimley.transporteta.sharedpreferences.factory.SharedPrefDataFactory.Factory.randomUuid
import com.joaquimley.transporteta.data.model.TransportEntity
import com.joaquimley.transporteta.sharedpreferences.FrameworkLocalStorageImpl
import com.joaquimley.transporteta.sharedpreferences.model.SharedPrefTransport

/**
 * Factory class for [SharedPrefTransport] related instances
 */
class SharedPrefTransportFactory {

    companion object Factory {

        fun makeSharedPrefTransportList(count: Int, isFavorite: Boolean = false, type: String = randomUuid()): List<SharedPrefTransport> {
            val transports = mutableListOf<SharedPrefTransport>()
            repeat(count) {
                transports.add(makeSharedPrefTransport(isFavorite, type))
            }
            return transports
        }

        fun makeSharedPrefTransport(isFavorite: Boolean = false, type: String = "bus", code: Int = randomInt(), id: String = randomUuid(), slot: FrameworkLocalStorageImpl.Slot = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE): SharedPrefTransport {
            return SharedPrefTransport(id, randomUuid(), code, randomUuid(), isFavorite, type, randomUuid(), slot)
        }
        
        fun makeSharedPrefTransportString(id: String = randomUuid(), transportName: String = randomUuid(), code: Int = randomInt(), latestEta: String = randomUuid(), isFavorite: Boolean = true, type: String = "bus", lastUpdated: String = randomUuid(), slot: String = FrameworkLocalStorageImpl.Slot.SAVE_SLOT_ONE.name): String {
            return "{\"id\":\"$id\",\"name\":\"$transportName\",\"code\":$code,\"latestEta\":\"$latestEta\",\"isFavorite\":$isFavorite,\"type\":\"$type\",\"lastUpdated\":\"$lastUpdated\",\"slot\":\"$slot\"}"
        }

        fun makeTransportEntityList(count: Int, isFavorite: Boolean = false, type: String = randomUuid()): List<TransportEntity> {
            val transports = mutableListOf<TransportEntity>()
            repeat(count) {
                transports.add(makeTransportEntity(isFavorite, type))
            }
            return transports
        }

        fun makeTransportEntity(isFavorite: Boolean = false, type: String = randomUuid(), code: Int = randomInt(), id: String = randomUuid()): TransportEntity {
            return TransportEntity(id, randomUuid(), code, randomUuid(), isFavorite, type)
        }

    }

}