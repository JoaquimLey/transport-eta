package com.joaquimley.transporteta.ui.util

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.airbnb.lottie.Cancellable
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieComposition
import com.bumptech.glide.Glide

fun View.setVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
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

fun <T, VH : RecyclerView.ViewHolder> ListAdapter<T, VH>.isEmpty() = itemCount == 0

fun <T, VH : RecyclerView.ViewHolder> ListAdapter<T, VH>.clear() {
    submitList(null)
}

fun LottieAnimationView.load(jsonString: String) {
    this.setAnimation(jsonString)
    this.playAnimation()
}

fun LottieAnimationView.loadAsync(jsonString: String): Cancellable {
    return LottieComposition.Factory.fromJsonString(jsonString, {
        it?.let {
            this.setComposition(it)
            this.playAnimation()
        }
    })
}