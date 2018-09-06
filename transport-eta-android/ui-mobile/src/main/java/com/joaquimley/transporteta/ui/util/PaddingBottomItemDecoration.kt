package com.joaquimley.transporteta.ui.util

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.View

class PaddingBottomItemDecoration(private val size: Int) : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
            outRect.bottom = size
        }
    }
}
