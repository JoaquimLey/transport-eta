package com.joaquimley.transporteta.sharedpreferences

import android.content.SharedPreferences
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
class FrameworkLocalStorageImpl @Inject constructor(private val sharedPreferences: SharedPreferences,
                                                    private val mapper: SharedPrefTransportMapper) : FrameworkLocalStorage {


    private val data = HashMap<Slot, SharedPrefTransport?>()

    private val sharedPreferencesObservable: PublishSubject<List<TransportEntity>> = PublishSubject.create()

    init {
        loadAll()
        observeSharedPreferencesChanges()
    }

    override fun saveTransport(transportEntity: TransportEntity): Completable {
        return Completable.fromAction {
            if (saveToSharedPrefs(mapper.toSharedPref(transportEntity)).not()) {
                throw Throwable("All slots filled")
            }
        }
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
        return Completable.fromAction {

        }
    }

    private fun observeSharedPreferencesChanges() {
        sharedPreferences.registerOnSharedPreferenceChangeListener { _, key ->
            if (key != SHARED_PREFERENCES_LAST_UPDATED) {
                loadAll()
            }
        }
    }

    private fun loadAll() {
        getFromSharedPrefs(Slot.ONE)?.let { data[Slot.ONE] = mapper.fromCacheString(it) }
        getFromSharedPrefs(Slot.TWO)?.let { data[Slot.TWO] = mapper.fromCacheString(it) }
        getFromSharedPrefs(Slot.THREE)?.let { data[Slot.THREE] = mapper.fromCacheString(it) }

        sharedPreferencesObservable
                .onNext(data.values.filterNotNull().map { mapper.toEntity(it) })
    }

    private fun saveToSharedPrefs(sharedPrefTransport: SharedPrefTransport): Boolean {
        return getFreeSlot()?.let {
            sharedPreferences.edit()
                    .putString(it.slotName, mapper.toCacheString(sharedPrefTransport))
                    .apply()
            true
        } ?: false
    }

    private fun getFreeSlot(): Slot? {
        return when {
            getFromSharedPrefs(Slot.ONE) == null -> Slot.ONE
            getFromSharedPrefs(Slot.TWO) == null -> Slot.TWO
            getFromSharedPrefs(Slot.THREE) == null -> Slot.THREE
            else -> null
        }
    }

    private fun getFromSharedPrefs(slot: Slot): String? {
        return sharedPreferences.getString(slot.slotName, null)
    }

    companion object {
        private const val SHARED_PREFERENCES_LAST_UPDATED = "sharedpreferences.last_updated"
    }

    enum class Slot(val slotName: String) {
        ONE("transport_eta_fav_1"),
        TWO("transport_eta_fav_2"),
        THREE("transport_eta_fav_3"),
    }
}
