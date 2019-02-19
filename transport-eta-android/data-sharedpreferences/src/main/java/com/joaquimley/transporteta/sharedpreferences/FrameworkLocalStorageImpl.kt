package com.joaquimley.transporteta.sharedpreferences

import android.content.SharedPreferences
import com.joaquimley.transporteta.data.model.TransportEntity
import com.joaquimley.transporteta.data.source.FrameworkLocalStorage
import com.joaquimley.transporteta.sharedpreferences.mapper.SharedPrefTransportMapper
import com.joaquimley.transporteta.sharedpreferences.model.SharedPrefTransport
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Attention any one looking at this from GitHub! This code needs to be cleaned up/refactored the
 * functionality to external consumers will remain the same but there's still a lot of experimentation
 * being done, fixing tests etc.
 * After this implementation is stable I'll remove this notice.
 */
@Singleton
class FrameworkLocalStorageImpl @Inject constructor(private val sharedPreferences: SharedPreferences,
                                                    private val mapper: SharedPrefTransportMapper) : FrameworkLocalStorage {

    private val data: BehaviorSubject<List<TransportEntity>> = BehaviorSubject.create()

//        observeSharedPreferencesChanges()
//    private fun observeSharedPreferencesChanges() {
//        val sharedPreferencesObserver = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
//            Log.e("loadAll", "something changed on $key")
//            emitLatestData()
//        }
//
//        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferencesObserver)
//    }

    init {
        emitLatestData()
    }

    override fun saveTransport(transportEntity: TransportEntity): Completable {
        return Completable.fromAction {
            if (saveToSharedPrefs(mapper.toSharedPref(transportEntity))) {
                emitLatestData()
            } else {
                throw Throwable("All slots filled")
            }
        }
    }

    override fun deleteTransport(transportEntityId: String): Completable {
        return Completable.fromAction {
            getFromSharedPrefs(transportEntityId)?.let {
                if (removeFromSharedPreferences(it.slot)) {
                    emitLatestData()
                }
            } ?: run {
                throw Throwable("No transport found with id $transportEntityId")
            }
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

    override fun getAll(): Flowable<List<TransportEntity>> {
        emitLatestData()
        return data.toFlowable(BackpressureStrategy.LATEST)
    }

    override fun clearAll(): Completable {
        return Completable.fromAction {
            removeFromSharedPreferences(Slot.SAVE_SLOT_ONE)
            removeFromSharedPreferences(Slot.SAVE_SLOT_TWO)
            removeFromSharedPreferences(Slot.SAVE_SLOT_THREE)
        }
    }

    private fun emitLatestData() {
        data.onNext(loadDataFromSharedPreferences())
    }

    private fun loadDataFromSharedPreferences(): List<TransportEntity> {
        return mutableListOf<TransportEntity>().apply {
            getFromSharedPrefs(Slot.SAVE_SLOT_ONE)?.let { mapper.fromCacheString(it) }?.let {
                add(mapper.toEntity(it))
            }
            getFromSharedPrefs(Slot.SAVE_SLOT_TWO)?.let { mapper.fromCacheString(it) }?.let {
                add(mapper.toEntity(it))
            }

            getFromSharedPrefs(Slot.SAVE_SLOT_THREE)?.let { mapper.fromCacheString(it) }?.let {
                add(mapper.toEntity(it))
            }
        }
    }

    private fun saveToSharedPrefs(sharedPrefTransport: SharedPrefTransport): Boolean {
        return getAvailableSLot()?.let {
            sharedPreferences.edit()
                    .putString(it.name, mapper.toCacheString(sharedPrefTransport.apply { slot = it }))
                    .apply()
            true
        } ?: false
    }

    private fun removeFromSharedPreferences(slot: Slot?): Boolean {
        return slot?.let {
            sharedPreferences.edit()
                    .remove(it.name)
                    .apply()
            true
        } ?: false
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
