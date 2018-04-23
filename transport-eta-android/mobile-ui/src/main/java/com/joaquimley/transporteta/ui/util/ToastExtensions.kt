package com.joaquimley.transporteta.ui.util

import android.widget.Toast
import com.joaquimley.transporteta.ui.App

fun Toast.showToast(text:String, isLong : Boolean = false) {
    Toast.makeText(App.getContext(), text, if(isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}