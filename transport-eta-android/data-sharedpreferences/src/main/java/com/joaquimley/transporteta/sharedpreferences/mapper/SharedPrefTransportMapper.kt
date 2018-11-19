package com.joaquimley.transporteta.sharedpreferences.mapper

import com.joaquimley.transporteta.data.model.TransportEntity
import com.joaquimley.transporteta.sharedpreferences.model.SharedPrefTransport
import kotlinx.serialization.json.JSON

class SharedPrefTransportMapper {

    fun toCacheString(from: SharedPrefTransport): String {
        return JSON.stringify(SharedPrefTransport.serializer(), from)
    }

    fun fromCacheString(from: String): SharedPrefTransport {
        return JSON.parse(SharedPrefTransport.serializer(), from)
    }

    fun toSharedPref(from: List<TransportEntity>): List<SharedPrefTransport> {
        return from.map { toSharedPref(it) }
    }

    fun toSharedPref(from: TransportEntity): SharedPrefTransport {
        return SharedPrefTransport(from.id, from.name, from.code, from.latestEta, from.isFavorite, from.type, from.latestEta)
    }

    fun toEntity(from: List<SharedPrefTransport>): List<TransportEntity> {
        return from.map { toEntity(it) }
    }

    fun toEntity(from: SharedPrefTransport): TransportEntity {
        return TransportEntity(from.id, from.name, from.code, from.latestEta, from.isFavorite, from.type)
    }
}