package com.joaquimley.transporteta.sms.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SmsModel(val code: Int, val message: String) : Parcelable