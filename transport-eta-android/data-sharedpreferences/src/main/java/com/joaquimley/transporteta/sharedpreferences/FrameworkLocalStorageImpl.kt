package com.joaquimley.transporteta.sharedpreferences

import android.content.Context
import com.joaquimley.transporteta.data.model.TransportEntity
import com.joaquimley.transporteta.data.source.FrameworkLocalStorage
import com.joaquimley.transporteta.sharedpreferences.mapper.SharedPrefTransportMapper
import com.joaquimley.transporteta.sharedpreferences.model.SharedPrefTransport
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FrameworkLocalStorageImpl @Inject constructor(context: Context,
                                                    private val mapper: SharedPrefTransportMapper) : FrameworkLocalStorage {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    private val sharedPreferencesObservable: PublishSubject<List<TransportEntity>> = PublishSubject.create()

    init {
        observeSharedPreferencesChanges()
        loadAll()
    }

    override fun saveTransport(transportEntity: TransportEntity): Completable {
        return Completable.complete()
    }

    override fun deleteTransport(transportEntityId: String): Completable {
        return Completable.complete()
    }

    override fun getTransport(transportEntityId: String): Single<TransportEntity> {
        return Single.just(TransportEntity("hi", "mock", 2, "el", true, "bus"))
    }

    override fun getAll(): Single<List<TransportEntity>> {
        val list = mutableListOf<TransportEntity>()
        list.add(TransportEntity("hi", "mock", 2, "latestEta 12324", true, "bus"))
        list.add(TransportEntity("there", "mock", 23, "latestEta 123", true, "bus"))
        list.add(TransportEntity("world", "mock", 25, "latestEta 12454", true, "bus"))
        list.add(TransportEntity("sup", "mock", 29, "latestEta 675", true, "bus"))
        return Single.just(list)
    }

    override fun clearAll(): Completable {
        return Completable.complete()
    }

    private fun observeSharedPreferencesChanges() {
        sharedPreferences.registerOnSharedPreferenceChangeListener { _, key ->
            if (key != SHARED_PREFERENCES_LAST_UPDATED) {
                sharedPreferencesObservable.onNext(loadAll())
            }
        }
    }

    private fun loadAll(): List<TransportEntity> {
        val data = mutableListOf<TransportEntity>()
        data.add(mapper.toEntity(get(Slot.ONE)))
        data.add(mapper.toEntity(get(Slot.TWO)))
        data.add(mapper.toEntity(get(Slot.THREE)))
        return data
    }

    private fun get(key: Slot): SharedPrefTransport {
        return mapper.fromCacheString(sharedPreferences.getString(key.name, ""))
    }

    companion object {
        private const val SHARED_PREFERENCES_NAME = "com.joaquimley.transporteta.sharedpreferences"
        private const val SHARED_PREFERENCES_LAST_UPDATED = "sharedpreferences.last_updated"
    }

    enum class Slot(name: String) {
        ONE("transport_eta_fav_1"),
        TWO("transport_eta_fav_2"),
        THREE("transport_eta_fav_3")
    }

    // TODO - Still not sure this is needed
    /**
     * Store and retrieve the last time data was cached
     */
//var lastUpdated: Long
//    get() = sharedPreferences.getLong(FrameworkLocalStorageImpl.SHARED_PREFERENCES_LAST_UPDATED, 0)
//    set(lastUpdated) = sharedPreferences.edit().putLong(FrameworkLocalStorageImpl.SHARED_PREFERENCES_LAST_UPDATED, lastUpdated).apply()
}


