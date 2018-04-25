package com.joaquimley.transporteta.ui.util

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class PaddingBottomItemDecoration(private val size: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
            outRect.bottom = size
        }
    }
}
