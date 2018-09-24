package com.joaquimley.transporteta.ui.util.extensions

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

/**
 * Keep the scope of this file to the [View] class
 */

fun View.setVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun Snackbar.setVisible(isVisible: Boolean?) {
    if (isVisible != true) this.show() else this.dismiss()
}

fun ImageView.load(resourceId: Int) {
    Glide.with(context)
            .load(resourceId)
            .into(this)
}

fun EditText.onChange(cb: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            cb(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}