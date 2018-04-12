package com.joaquimley.transporteta.ui.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

fun View.setVisible(isVisible: Boolean) {
    visibility = if(isVisible) View.VISIBLE else View.GONE
}

fun ImageView.load(resourceId: Int) {
    Glide.with(context)
            .load(resourceId)
            .into(this)
}

//private fun android.support.v7.app.AlertDialog.setButton(whichButton: Int, @StringRes stringResource: Int, function: () -> Unit) {
//   setButton(whichButton, context.getString(stringResource), function)
//}