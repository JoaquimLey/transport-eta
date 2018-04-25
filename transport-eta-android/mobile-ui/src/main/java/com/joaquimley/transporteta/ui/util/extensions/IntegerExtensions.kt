package com.joaquimley.transporteta.ui.util.extensions

import android.content.res.Resources

val Int.dp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()