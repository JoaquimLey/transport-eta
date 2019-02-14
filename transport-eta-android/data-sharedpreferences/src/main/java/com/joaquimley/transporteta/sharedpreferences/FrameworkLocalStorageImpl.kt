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
        return Completable.fromAction {
            getFromSharedPrefs(transportEntityId)?.let { removeFromSharedPreferences(it.slot) }
                    ?: throw Throwable("No transport found")
        }
    }

    /**
     * TODO - Should this single return a nullable TransportEntity instead of Error?
     */
    override fun getTransport(transportEntityId: String): Single<TransportEntity> {
        return Single.defer {
            Single.just(getFromSharedPrefs(transportEntityId)?.let { mapper.toEntity(it) }
                    ?: throw Throwable("Transport with id $transportEntityId not found")
            )
        }
    }

    override fun getAll(): Single<List<TransportEntity>> {
        return Single.defer {
            Single.just(loadAll())
        }
    }

    override fun clearAll(): Completable {
        return Completable.fromAction {
            removeFromSharedPreferences(Slot.SAVE_SLOT_ONE)
            removeFromSharedPreferences(Slot.SAVE_SLOT_TWO)
            removeFromSharedPreferences(Slot.SAVE_SLOT_THREE)
        }
    }

    private fun observeSharedPreferencesChanges() {
        sharedPreferences.registerOnSharedPreferenceChangeListener { _, _ ->
            sharedPreferencesObservable.onNext(loadAll())
        }
    }

    private fun loadAll(): List<TransportEntity> {
        getFromSharedPrefs(Slot.SAVE_SLOT_ONE)?.let { data[Slot.SAVE_SLOT_ONE] = mapper.fromCacheString(it) }
        getFromSharedPrefs(Slot.SAVE_SLOT_TWO)?.let { data[Slot.SAVE_SLOT_TWO] = mapper.fromCacheString(it) }
        getFromSharedPrefs(Slot.SAVE_SLOT_THREE)?.let { data[Slot.SAVE_SLOT_THREE] = mapper.fromCacheString(it) }
        return data.values.filterNotNull().map { mapper.toEntity(it) }
    }

    private fun saveToSharedPrefs(sharedPrefTransport: SharedPrefTransport): Boolean {
        return getAvailableSLot()?.let {
            sharedPreferences.edit()
                    .putString(it.name, mapper.toCacheString(sharedPrefTransport))
                    .apply()
            true
        } ?: false
    }

    private fun removeFromSharedPreferences(slot: Slot?) {
        slot?.let {
            sharedPreferences.edit()
                    .remove(it.name)
                    .apply()
        }
    }

    /**
     * TODO should improve this with coroutines
     */
    private fun getAvailableSLot(): Slot? {
        return when {
            getFromSharedPrefs(Slot.SAVE_SLOT_ONE) == null -> Slot.SAVE_SLOT_ONE
            getFromSharedPrefs(Slot.SAVE_SLOT_TWO) == null -> Slot.SAVE_SLOT_TWO
            getFromSharedPrefs(Slot.SAVE_SLOT_THREE) == null -> Slot.SAVE_SLOT_THREE
            else -> null
        }
    }

    private fun getFromSharedPrefs(slot: Slot): String? {
        return sharedPreferences.getString(slot.name, null)
    }

    /**
     * TODO should improve this with coroutines
     */
    private fun getFromSharedPrefs(transportId: String): SharedPrefTransport? {
        val dataFromSlotOne = getFromSharedPrefs(Slot.SAVE_SLOT_ONE)?.let { mapper.fromCacheString(it) }
        if (dataFromSlotOne?.id == transportId) {
            return dataFromSlotOne
        }

        val dataFromSlotTwo = getFromSharedPrefs(Slot.SAVE_SLOT_TWO)?.let { mapper.fromCacheString(it) }
        if (dataFromSlotTwo?.id == transportId) {
            return dataFromSlotTwo
        }

        val dataFromSlotThree = getFromSharedPrefs(Slot.SAVE_SLOT_THREE)?.let { mapper.fromCacheString(it) }
        if (dataFromSlotThree?.id == transportId) {
            return dataFromSlotThree
        }
        return null
    }

    enum class Slot {
        SAVE_SLOT_ONE,
        SAVE_SLOT_TWO,
        SAVE_SLOT_THREE,
    }
}
