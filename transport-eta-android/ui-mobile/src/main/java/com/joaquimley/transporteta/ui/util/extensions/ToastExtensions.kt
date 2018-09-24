package com.joaquimley.transporteta.ui.util.extensions

import android.content.Context
import android.widget.Toast


fun Toast.show(context: Context?, text: String, isLong: Boolean = false) {
    Toast.makeText(context?.applicationContext, text, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}