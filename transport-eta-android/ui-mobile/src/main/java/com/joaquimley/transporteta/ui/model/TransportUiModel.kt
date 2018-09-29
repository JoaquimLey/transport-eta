package com.joaquimley.transporteta.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class TransportUiModel(val code: Int = -1, val latestEta: String = "", val originalText: String = "", val type: Type): Parcelable {

	enum class Type(val type: String) {
		BUS("bus")
	}
}