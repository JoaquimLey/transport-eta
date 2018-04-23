package com.joaquimley.transporteta.ui.util

import android.app.Activity
import android.widget.Toast


fun Toast.showToast(activity: Activity?, text: String, isLong: Boolean = false) {
    Toast.makeText(activity?.applicationContext, text, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}