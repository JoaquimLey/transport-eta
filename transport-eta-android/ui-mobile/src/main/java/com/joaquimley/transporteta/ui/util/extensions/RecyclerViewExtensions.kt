package com.joaquimley.transporteta.ui.util.extensions

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joaquimley.transporteta.ui.util.PaddingBottomItemDecoration

fun <T, VH : RecyclerView.ViewHolder> ListAdapter<T, VH>.isEmpty() = itemCount == 0

fun <T, VH : RecyclerView.ViewHolder> ListAdapter<T, VH>.clear() {
    submitList(null)
}

fun RecyclerView.addBottomPaddingDecoration(size: Int = 80) {
    this.addItemDecoration(PaddingBottomItemDecoration(size.px))
}