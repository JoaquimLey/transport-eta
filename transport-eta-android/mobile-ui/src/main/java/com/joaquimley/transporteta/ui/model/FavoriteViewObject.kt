package com.joaquimley.transporteta.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class FavoriteViewObject(val code: Int = -1, val latestEta: String = "", val originalText: String = ""): Parcelable