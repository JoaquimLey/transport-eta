package com.joaquimley.transporteta.home.favorite

import android.support.v7.util.DiffUtil
import com.joaquimley.transporteta.model.FavoriteView

class FavoriteViewDiffCallback : DiffUtil.ItemCallback<FavoriteView>() {
    override fun areItemsTheSame(oldItem: FavoriteView?, newItem: FavoriteView?): Boolean {
        return oldItem?.code == newItem?.code
    }

    override fun areContentsTheSame(oldItem: FavoriteView?, newItem: FavoriteView?): Boolean {
        return oldItem == newItem
    }
}