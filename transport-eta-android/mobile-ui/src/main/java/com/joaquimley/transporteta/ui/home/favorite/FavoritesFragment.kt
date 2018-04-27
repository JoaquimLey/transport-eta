package com.joaquimley.transporteta.ui.home.favorite

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.joaquimley.transporteta.R
import com.joaquimley.transporteta.presentation.model.FavoriteView
import com.joaquimley.transporteta.ui.model.data.ResourceState
import com.joaquimley.transporteta.ui.util.extensions.*
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_favourites.*
import kotlinx.android.synthetic.main.view_message.*
import javax.inject.Inject


/**
 * Created by joaquimley on 24/03/2018.
 */
class FavoritesFragment : Fragment() {

    private lateinit var adapter: FavoritesAdapter
    private lateinit var requestingSnackbar: Snackbar

    @Inject lateinit var viewModelProvider: FavoritesViewModelProvider
    private val viewModel by lazy { viewModelProvider(this) }

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
//            initViewModel()
        observeFavourites()
        observeRequestsEnabled()
    }

    private fun observeRequestsEnabled() {
        viewModel.getAcceptingRequests().observe(this,
                Observer {
                    adapter.setActionEnabledStatus(it ?: true)
                    requestingSnackbar.setVisible(it)
                })
    }

    private fun observeFavourites() {
        viewModel.getFavourites().observe(this,
                Observer {
                    it?.let { handleDataState(it.status, it.data, it.message) }
                })
    }

    private fun handleDataState(resourceState: ResourceState, data: List<FavoriteView>?, message: String?) {
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

    private fun setupScreenForSuccess(favoriteViewList: List<FavoriteView>) {
        swipe_refresh?.isRefreshing = false
        progress_bar?.setVisible(false)
        message_view?.setVisible(false)
        recycler_view?.setVisible(true)
        adapter.submitList(favoriteViewList)
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
                Snackbar.make(favorites_fragment_container, it, Snackbar.LENGTH_LONG)
                        .setAction(R.string.action_retry, { viewModel.retry() })
                        .show()
            }
        }
    }

    private fun setupRequestSnackbar() {
        requestingSnackbar = Snackbar.make(favorites_fragment_container, R.string.info_requesting, Snackbar.LENGTH_INDEFINITE)
        requestingSnackbar.setAction(R.string.action_cancel, {
            viewModel.cancelEtaRequest()
            Toast.makeText(activity?.applicationContext, R.string.info_canceled, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupRecyclerView() {
        recycler_view?.setHasFixedSize(true)
        recycler_view?.layoutManager = LinearLayoutManager(context)
        adapter = FavoritesAdapter({
            viewModel.onEtaRequested(it)
        })
        recycler_view?.adapter = adapter
        recycler_view?.addBottomPaddingDecoration()
    }

    private fun setupListeners() {
        // TODO emptyView.setListener(emptyListener)
        swipe_refresh.setOnRefreshListener({ viewModel.retry() })
        fab.setOnClickListener { showAddFavoriteDialog() }
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_DRAGGING -> fab.hide()
                    RecyclerView.SCROLL_STATE_IDLE -> fab.show()
                }
            }
        })
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

            val busStopTitleEditText: TextInputEditText? = dialog.findViewById(R.id.favorite_title_edit_text)
            val busStopCodeEditText: TextInputEditText? = dialog.findViewById(R.id.favorite_code_edit_text)
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
