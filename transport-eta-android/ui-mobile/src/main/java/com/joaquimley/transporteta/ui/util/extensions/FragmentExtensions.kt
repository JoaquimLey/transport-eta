package com.joaquimley.transporteta.ui.util.extensions

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import android.view.View

fun <T : View> androidx.fragment.app.Fragment.findViewById(@IdRes id: Int): T? {
    return activity?.findViewById(id)
}