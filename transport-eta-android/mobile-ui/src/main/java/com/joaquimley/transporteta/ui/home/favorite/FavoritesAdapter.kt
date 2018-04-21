package com.joaquimley.transporteta.ui.home.favorite

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joaquimley.transporteta.R
import com.joaquimley.transporteta.presentation.model.FavoriteView
import com.joaquimley.transporteta.ui.util.load
import kotlinx.android.synthetic.main.item_favorite.view.*

class FavoritesAdapter(private val clickListener: (FavoriteView) -> Unit)
    : ListAdapter<FavoriteView, RecyclerView.ViewHolder>(FavoriteViewDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) != null) {
            VIEW_TYPE_FAVORITE
        } else {
            VIEW_TYPE_PROGRESS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_FAVORITE -> {
                FavoriteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false))
            }
            else -> {
                ProgressViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val favourite = getItem(position)
        if (favourite != null) {
            (holder as? FavoriteViewHolder)?.bind(favourite)
        }
    }

    inner class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(favoriteView: FavoriteView) {
            itemView.avatar_image_view.load(R.drawable.station)
            itemView.title_text_view.text = favoriteView.code.toString()
            itemView.subtitle_text_view.text = favoriteView.latestEta
            itemView.original_sms_text_view.text = favoriteView.originalText
            itemView.eta_button.setOnClickListener { clickListener(favoriteView) }
        }
    }

    companion object {
        const val VIEW_TYPE_PROGRESS = -1
        const val VIEW_TYPE_FAVORITE = -2
    }

    class FavoriteViewDiffCallback : DiffUtil.ItemCallback<FavoriteView>() {
        override fun areItemsTheSame(oldItem: FavoriteView?, newItem: FavoriteView?): Boolean {
            return oldItem?.code == newItem?.code
        }

        override fun areContentsTheSame(oldItem: FavoriteView?, newItem: FavoriteView?): Boolean {
            return oldItem == newItem
        }
    }
}