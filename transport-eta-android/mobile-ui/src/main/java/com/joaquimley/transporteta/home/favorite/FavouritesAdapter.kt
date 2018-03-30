package com.joaquimley.transporteta.home.favorite

import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.joaquimley.transporteta.R
import com.joaquimley.transporteta.model.FavouriteView
import kotterknife.bindView

class FavouritesAdapter(val listener: Listener? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val dataSet: MutableList<FavouriteView?> = emptyList<FavouriteView?>().toMutableList()

    init {
        setHasStableIds(true)
    }

    fun set(favourites: List<FavouriteView>) {
        dataSet.clear()
        dataSet.addAll(favourites)
        notifyDataSetChanged()
    }

    fun add(favourites: List<FavouriteView>) {
        val index = dataSet.size
        dataSet.addAll(favourites)
        notifyItemRangeInserted(index, favourites.size)
    }

    fun addLoadingView() {
        val index = dataSet.size
        if (dataSet.isEmpty().not() && dataSet[index] != null) {
            dataSet.add(null)
            notifyItemInserted(index + 1)
        }
    }

    fun removeLoadingView() {
        val lastIndex = dataSet.size - 1
        if (dataSet.isEmpty().not() && dataSet[lastIndex] == null) {
            dataSet.removeAt(lastIndex)
            notifyItemRemoved(lastIndex)
        }
    }

    fun isEmpty() = itemCount == 0

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun getItemId(position: Int): Long {
        return if (dataSet.size >= position) {
            dataSet[position]?.code?.toLong() ?: View.NO_ID.toLong()
        } else {
            View.NO_ID.toLong()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataSet.size >= position && dataSet[position] != null) {
            VIEW_TYPE_FAVORITE
        } else {
            VIEW_TYPE_PROGRESS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_FAVORITE -> {
                FavouriteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false))
            }
            else -> {
                ProgressViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val favourite = dataSet[position]
        if (favourite != null) {
            (holder as? FavouriteViewHolder)?.bind(favourite)
        }
    }

    inner class FavouriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val avatarImage: AppCompatImageView by bindView(R.id.image_avatar)
        private val titleTextView: AppCompatTextView by bindView(R.id.text_title)
        private val subtitleTextView: AppCompatTextView by bindView(R.id.text_subtitle)
        private val etaButton: AppCompatButton by bindView(R.id.eta_button)

        fun bind(favourite: FavouriteView) {
            Glide.with(itemView.context)
                    .load(R.drawable.station)
                    .into(avatarImage)

            titleTextView.text = favourite.code.toString()
            subtitleTextView.text = favourite.latestEta
            etaButton.setOnClickListener { listener?.onItemClicked(favourite) }
        }
    }

    class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view)


    companion object {
        const val TAG = "FavouritesAdapter"
        const val VIEW_TYPE_PROGRESS = -1
        const val VIEW_TYPE_FAVORITE = -2
    }

    interface Listener {
        fun onItemClicked(favourite: FavouriteView)
    }
}