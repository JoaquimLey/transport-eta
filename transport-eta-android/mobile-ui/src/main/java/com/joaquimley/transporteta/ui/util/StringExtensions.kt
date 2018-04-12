package com.joaquimley.transporteta.ui.util

fun String.isValidPhoneNumber(phoneNumber: String): Boolean {
    return android.util.Patterns.PHONE.matcher(phoneNumber).matches()
}