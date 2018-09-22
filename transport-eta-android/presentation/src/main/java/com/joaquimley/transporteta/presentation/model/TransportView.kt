package com.joaquimley.transporteta.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by joaquimley on 28/03/2018.
 */

@Parcelize
data class TransportView(val code: Int = -1, val latestEta: String = "", val type: TransportType = TransportType.BUS,
                         var isActionEnabled: Boolean = true) : Parcelable {

    enum class TransportType(val type: String) {
        BUS("bus")
    }
}

