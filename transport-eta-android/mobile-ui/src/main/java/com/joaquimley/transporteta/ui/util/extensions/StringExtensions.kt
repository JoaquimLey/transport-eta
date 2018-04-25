package com.joaquimley.transporteta.ui.util.extensions

fun String.isValidPhoneNumber(phoneNumber: String): Boolean {
    return android.util.Patterns.PHONE.matcher(phoneNumber).matches()
}