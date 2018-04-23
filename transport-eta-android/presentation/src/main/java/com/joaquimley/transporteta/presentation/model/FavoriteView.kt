package com.joaquimley.transporteta.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by joaquimley on 28/03/2018.
 */

@Parcelize
data class FavoriteView(val code: Int = -1, val latestEta: String = "", val originalText: String = "", var isActionEnabled: Boolean = true) : Parcelable