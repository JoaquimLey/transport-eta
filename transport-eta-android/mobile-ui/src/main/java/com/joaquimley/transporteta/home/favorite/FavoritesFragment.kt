package com.joaquimley.transporteta.home.favorite

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.joaquimley.transporteta.R
import com.joaquimley.transporteta.model.FavoriteView
import com.joaquimley.transporteta.model.data.ResourceState
import com.joaquimley.transporteta.util.setVisible
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_favourites.*
import kotterknife.bindView
import javax.inject.Inject

/**
 * Created by joaquimley on 24/03/2018.
 */
class FavoritesFragment : Fragment() {

    private val recyclerView: RecyclerView by bindView(R.id.recycler_view)
    private val contentLoadingView: ProgressBar by bindView(R.id.progress)
    private val swipeRefreshView: SwipeRefreshLayout by bindView(R.id.swipe_refresh)

    @Inject
    lateinit var viewModelFactory: FavoritesViewModelFactory
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var adapter: FavoritesAdapter

    override fun onAttach(context: Context?) {
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
        val currentContext = context
        if (currentContext != null) {
            val builder = AlertDialog.Builder(currentContext)
            builder.setTitle(R.string.create_favorite_title)
            builder.setView(R.layout.dialog_create_favorite)
            val dialog = builder.create()
            val busStopCodeInputLayout: TextInputLayout? = dialog.findViewById(R.id.favorite_code_text_input_layout)
            val busStopCodeEditText: TextInputEditText? = dialog.findViewById(R.id.favorite_code_edit_text)
            val busStopTitleEditText: TextInputEditText? = dialog.findViewById(R.id.favorite_title_edit_text)


            dialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_create)) { dialog, which ->
                if(TextUtils.isEmpty(busStopCodeEditText?.text)) {
                    busStopCodeInputLayout?.error = "Please input bus stop code"
                }
            }

            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_discard)) { dialog, which ->
                // track discard action
            }

            dialog.show()
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(currentContext, R.color.colorLightGrey))


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

    private fun handleDataState(resourceState: ResourceState, data: List<FavoriteView>?,
                                message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> setupScreenForLoadingState(true)
            ResourceState.SUCCESS -> setupScreenForSuccess(data)
            ResourceState.ERROR -> setupScreenForError(message)
            ResourceState.EMPTY -> setupScreenForEmpty()
        }
    }

    private fun setupScreenEmptyState(isEmpty: Boolean) {
        // TODO show emptyView.setVisibility(isEmpty)
        recyclerView.setVisible(!isEmpty)
    }

    private fun setupScreenForLoadingState(isLoading: Boolean) {
        setupScreenEmptyState(false)
        if (isLoading) {
            if (swipeRefreshView.isRefreshing.not() && adapter.isEmpty()) {
                contentLoadingView.visibility = View.VISIBLE
            }
        } else {
            swipeRefreshView.isRefreshing = false
            contentLoadingView.visibility = View.GONE
//            adapter.removeLoadingView()
        }
    }

    private fun setupScreenForSuccess(favoriteViewList: List<FavoriteView>?) {
        setupScreenForLoadingState(false)
        setupScreenEmptyState(false)
        if (favoriteViewList != null) {
            adapter.submitList(favoriteViewList)
        }
    }

    private fun setupScreenForEmpty() {
        if (adapter.isEmpty()) {
            // TODO emptyView.setVisibility(true)

        }
    }

    private fun setupScreenForError(message: String?) {
        // TODO
        Log.e("FavFragment", "DEBUG: setupScreenForError: $message")
    }


    private fun initViewModel() {
        viewModel = ViewModelProviders.of(activity as AppCompatActivity, viewModelFactory).get(FavoritesViewModel::class.java)
    }

    private fun setupRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = FavoritesAdapter({ viewModel.onEtaRequested(it) })
        recyclerView.adapter = adapter
    }

    private fun setupListeners() {
        // TODO emptyView.setListener(emptyListener)
        swipeRefreshView.setOnRefreshListener({ viewModel.retry() })
    }

    companion object {

        fun newInstance(): FavoritesFragment {
            val fragment = FavoritesFragment()
//            val args = Bundle()
//            fragment.arguments = args
            return fragment
        }
    }
}


/*

LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);
 ...
 Cancellable compositionCancellable = LottieComposition.Factory.fromJson(getResources(), jsonObject, (composition) -> {
     animationView.setComposition(composition);
     animationView.playAnimation();
 });

 // Cancel to stop asynchronous loading of composition
 // compositionCancellable.cancel();

 */