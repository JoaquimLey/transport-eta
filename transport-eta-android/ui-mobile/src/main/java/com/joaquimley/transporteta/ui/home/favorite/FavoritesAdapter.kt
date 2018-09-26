package com.joaquimley.transporteta.ui.home.favorite

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joaquimley.transporteta.R
import com.joaquimley.transporteta.presentation.model.TransportView
import com.joaquimley.transporteta.ui.util.extensions.load
import kotlinx.android.synthetic.main.item_favorite.view.*

class FavoritesAdapter(private val clickListener: (TransportView) -> Unit)
    : ListAdapter<TransportView, androidx.recyclerview.widget.RecyclerView.ViewHolder>(FavoriteViewDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) != null) {
            VIEW_TYPE_FAVORITE
        } else {
            VIEW_TYPE_PROGRESS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_FAVORITE -> {
                FavoriteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false))
            }
            else -> {
                ProgressViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val favourite = getItem(position)
        if (favourite != null) {
            (holder as? FavoriteViewHolder)?.bind(favourite)
        }
    }

    fun setActionEnabledStatus(isEnabled: Boolean) {
        for (position in 0 until itemCount) {
            getItem(position).isActionEnabled = isEnabled
        }
        notifyDataSetChanged()
    }

    inner class ProgressViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

    inner class FavoriteViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

        fun bind(transportView: TransportView) {
            itemView.avatar_image_view.load(R.drawable.station)
            itemView.title_text_view.text = transportView.code.toString()
            itemView.subtitle_text_view.text = transportView.latestEta
            itemView.original_sms_text_view.text = transportView.latestEta
            transportView.isActionEnabled.let {
                itemView.eta_button.isEnabled = it
                itemView.eta_button.alpha = if (it) 1.0f else 0.3f
            }

            itemView.eta_button.setOnClickListener {
                clickListener(transportView)
            }
        }
    }

    companion object {
        const val VIEW_TYPE_PROGRESS = -1
        const val VIEW_TYPE_FAVORITE = -2
    }

    class FavoriteViewDiffCallback : DiffUtil.ItemCallback<TransportView>() {
        override fun areItemsTheSame(oldItem: TransportView, newItem: TransportView): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem: TransportView, newItem: TransportView): Boolean {
            return oldItem == newItem
        }
    }
}