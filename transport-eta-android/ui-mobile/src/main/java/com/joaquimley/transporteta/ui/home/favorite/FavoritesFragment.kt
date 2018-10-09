package com.joaquimley.transporteta.ui.home.favorite

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.joaquimley.transporteta.R
import com.joaquimley.transporteta.presentation.data.ResourceState
import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModelFactory
import com.joaquimley.transporteta.presentation.model.TransportView
import com.joaquimley.transporteta.ui.util.extensions.*
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_favourites.*
import kotlinx.android.synthetic.main.view_message.*
import javax.inject.Inject


/**
 * Created by joaquimley on 24/03/2018.
 */
class FavoritesFragment : Fragment() {

    // Inspiration for UI https://www.behance.net/gallery/69860023/Bust-app

    @Inject lateinit var viewModelProvider: FavoritesViewModelFactory
    private val viewModel by lazy { viewModelProvider.create()}

    private lateinit var adapter: FavoritesAdapter
    private lateinit var requestingSnackbar: Snackbar

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View = inflater.inflate(R.layout.fragment_favourites, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRequestSnackbar()
        setupRecyclerView()
        setupListeners()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeFavourites()
        observeRequestsEnabled()
    }

    private fun observeRequestsEnabled() {
        viewModel.isAcceptingRequests().observe(this,
                Observer {
                    adapter.setActionEnabledStatus(it ?: true)
                    requestingSnackbar.setVisible(it)
                })
    }

    private fun observeFavourites() {
        viewModel.getFavorites().observe(this,
                Observer { transportList ->
                    transportList?.let { handleDataState(it.status, it.data, it.message) }
                })
    }

    private fun handleDataState(resourceState: ResourceState, data: List<TransportView>?, message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> setupScreenForLoadingState(true)
            ResourceState.SUCCESS -> data?.let { setupScreenForSuccess(data) }
            ResourceState.ERROR -> setupScreenForError(message)
            ResourceState.EMPTY -> setupScreenEmptyState()
        }
    }

    private fun setupScreenForLoadingState(isLoading: Boolean) {
        message_view.setVisible(false)
        if (isLoading) {
            if (swipe_refresh.isRefreshing.not() && adapter.isEmpty()) {
                progress_bar?.setVisible(true)
            }
        } else {
            swipe_refresh?.isRefreshing = false
            progress_bar?.setVisible(false)
        }
    }

    private fun setupScreenForSuccess(transportViewList: List<TransportView>) {
        swipe_refresh?.isRefreshing = false
        progress_bar?.setVisible(false)
        message_view?.setVisible(false)
        recycler_view?.setVisible(true)
        adapter.submitList(transportViewList)
    }

    private fun setupScreenEmptyState() {
        adapter.clear()
        recycler_view?.setVisible(false)

        // TODO set the view to empty state
        message_view?.setVisible(true)
    }

    private fun setupScreenForError(message: String?) {
        if (adapter.isEmpty()) {
            recycler_view?.setVisible(false)
            message_text_view?.text = message
            message_view?.setVisible(true)
        } else {
            message?.let {
                com.google.android.material.snackbar.Snackbar.make(favorites_fragment_container, it, com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
                        .setAction(R.string.action_retry) { viewModel.onRefresh() }
                        .show()
            }
        }
    }

    private fun setupRequestSnackbar() {
        requestingSnackbar = Snackbar.make(favorites_fragment_container, R.string.info_requesting, com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE)
        requestingSnackbar.setAction(R.string.action_cancel) {
            viewModel.onEtaRequestCanceled()
            Toast.makeText(activity?.applicationContext, R.string.info_canceled, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        recycler_view?.setHasFixedSize(true)
        recycler_view?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        adapter = FavoritesAdapter { viewModel.onEtaRequested(it) }
        recycler_view?.adapter = adapter
        recycler_view?.addBottomPaddingDecoration()
        recycler_view.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: androidx.recyclerview.widget.RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_DRAGGING -> fab.hide()
                    RecyclerView.SCROLL_STATE_IDLE -> fab.show()
                }
            }
        })
    }

    private fun setupListeners() {
        // TODO emptyView.setListener(emptyListener)
        swipe_refresh.setOnRefreshListener { viewModel.onRefresh() }
        fab.setOnClickListener { showAddFavoriteDialog() }
    }

    private fun showAddFavoriteDialog() {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.create_favorite_title)
            builder.setView(R.layout.dialog_create_favorite)
            builder.setPositiveButton(getString(R.string.action_create), null)
            builder.setNegativeButton(getString(R.string.action_discard), null)
            val dialog = builder.create()
            dialog.show()

            val busStopTitleEditText: com.google.android.material.textfield.TextInputEditText? = dialog.findViewById(R.id.favorite_title_edit_text)
            val busStopCodeEditText: com.google.android.material.textfield.TextInputEditText? = dialog.findViewById(R.id.favorite_code_edit_text)
            busStopCodeEditText?.onChange {
                if (!TextUtils.isEmpty(it)) {
                    busStopCodeEditText.error = null
                }
            }

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                if (TextUtils.isEmpty(busStopCodeEditText?.text)) {
                    busStopCodeEditText?.error = getString(R.string.error_create_favorite_code_required)
                }
            }

            context?.let { ContextCompat.getColor(it, R.color.colorLightGrey) }?.let { dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(it) }
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
                dialog.dismiss()
            }

        }
    }

    companion object {
        @JvmStatic fun newInstance(): FavoritesFragment {
            val fragment = FavoritesFragment()
//            val args = Bundle()
//            fragment.arguments = args
            return fragment
        }
    }
}
