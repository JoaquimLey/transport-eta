package com.joaquimley.transporteta.ui.util.extensions

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.view.View

fun <T : View> Fragment.findViewById(@IdRes id: Int): T? {
    return activity?.findViewById(id)
}