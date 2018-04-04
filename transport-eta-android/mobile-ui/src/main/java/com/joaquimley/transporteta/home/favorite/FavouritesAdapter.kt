package com.joaquimley.transporteta.home.favorite

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joaquimley.transporteta.R
import com.joaquimley.transporteta.model.FavoriteView
import com.joaquimley.transporteta.util.load
import kotterknife.bindView

class FavouritesAdapter(private val clickListener: (FavoriteView) -> Unit)
    : ListAdapter<FavoriteView, RecyclerView.ViewHolder>(FavoriteViewDiffCallback()) {

    fun isEmpty() = itemCount == 0

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

    inner class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val avatarImage: AppCompatImageView by bindView(R.id.image_avatar)
        private val titleTextView: AppCompatTextView by bindView(R.id.text_title)
        private val subtitleTextView: AppCompatTextView by bindView(R.id.text_subtitle)
        private val originalSmsTextView: AppCompatTextView by bindView(R.id.original_sms_text)
        private val etaButton: AppCompatButton by bindView(R.id.eta_button)

        fun bind(favoriteView: FavoriteView) {
            avatarImage.load(R.drawable.station)
            titleTextView.text = favoriteView.code.toString()
            subtitleTextView.text = favoriteView.latestEta
            originalSmsTextView.text = favoriteView.originalText
            etaButton.setOnClickListener { clickListener(favoriteView) }
        }
    }

    class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view)

    companion object {
        const val TAG = "FavoritesAdapter"
        const val VIEW_TYPE_PROGRESS = -1
        const val VIEW_TYPE_FAVORITE = -2
    }
}