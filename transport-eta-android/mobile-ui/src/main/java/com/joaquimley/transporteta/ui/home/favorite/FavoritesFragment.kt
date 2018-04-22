package com.joaquimley.transporteta.ui.home.favorite

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joaquimley.transporteta.R
import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModel
import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModelFactory
import com.joaquimley.transporteta.presentation.model.FavoriteView
import com.joaquimley.transporteta.ui.model.data.ResourceState
import com.joaquimley.transporteta.ui.util.clear
import com.joaquimley.transporteta.ui.util.isEmpty
import com.joaquimley.transporteta.ui.util.onChange
import com.joaquimley.transporteta.ui.util.setVisible
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_favourites.*
import kotlinx.android.synthetic.main.view_message.*
import javax.inject.Inject


/**
 * Created by joaquimley on 24/03/2018.
 */
class FavoritesFragment : Fragment() {

    private lateinit var adapter: FavoritesAdapter
    private lateinit var viewModel: FavoritesViewModel
    @Inject lateinit var viewModelFactory: FavoritesViewModelFactory

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View = inflater.inflate(R.layout.fragment_favourites, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupRecyclerView()
        setupListeners()
    }

    private fun initViews() {
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        observeFavourites()
    }

    private fun observeFavourites() {
        viewModel.getFavourites().observe(this,
                Observer {
                    if (it != null) handleDataState(it.status, it.data, it.message)
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


    private fun initViewModel() {
        viewModel = ViewModelProviders.of(activity as AppCompatActivity, viewModelFactory).get(FavoritesViewModel::class.java)
    }

    private fun setupRecyclerView() {
        recycler_view?.setHasFixedSize(true)
        recycler_view?.layoutManager = LinearLayoutManager(context)
        adapter = FavoritesAdapter({ viewModel.onEtaRequested(it) })
        recycler_view?.adapter = adapter
    }

    private fun setupListeners() {
        // TODO emptyView.setListener(emptyListener)
        swipe_refresh.setOnRefreshListener({ viewModel.retry() })
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