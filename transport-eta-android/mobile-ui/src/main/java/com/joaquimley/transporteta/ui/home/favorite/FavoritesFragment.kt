package com.joaquimley.transporteta.ui.home.favorite

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joaquimley.transporteta.R
import com.joaquimley.transporteta.ui.model.FavoriteView
import com.joaquimley.transporteta.ui.model.data.ResourceState
import com.joaquimley.transporteta.ui.util.setVisible
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_favourites.*
import javax.inject.Inject

/**
 * Created by joaquimley on 24/03/2018.
 */
class FavoritesFragment : Fragment() {

    @Inject lateinit var viewModelFactory: FavoritesViewModelFactory
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
        if (activity != null) {
            val builder = AlertDialog.Builder(activity!!)
            builder.setTitle(R.string.create_favorite_title)
            builder.setView(R.layout.dialog_create_favorite)
            builder.setPositiveButton(getString(R.string.action_create), null)
            builder.setNegativeButton(getString(R.string.action_discard), null)
            val dialog = builder.create()

            dialog.show()
            val busStopCodeInputLayout: TextInputLayout? = dialog.findViewById(R.id.favorite_code_text_input_layout)
            val busStopTitleEditText: TextInputEditText? = dialog.findViewById(R.id.favorite_title_edit_text)

            val busStopCodeEditText: TextInputEditText? = dialog.findViewById(R.id.favorite_code_edit_text)
            busStopCodeEditText?.addTextChangedListener(object: TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    if(!TextUtils.isEmpty(s)) {
                        busStopCodeInputLayout?.error = null
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                     // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }


            })

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                if (TextUtils.isEmpty(busStopCodeEditText?.text)) {
                    busStopCodeInputLayout?.error = "Please input bus stop code"
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
        recycler_view.setVisible(!isEmpty)
    }

    private fun setupScreenForLoadingState(isLoading: Boolean) {
        setupScreenEmptyState(false)
        if (isLoading) {
            if (swipe_refresh.isRefreshing.not() && adapter.isEmpty()) {
                progress.visibility = View.VISIBLE
            }
        } else {
            swipe_refresh.isRefreshing = false
            progress.visibility = View.GONE
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
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(context)
        adapter = FavoritesAdapter({ viewModel.onEtaRequested(it) })
        recycler_view.adapter = adapter
    }

    private fun setupListeners() {
        // TODO emptyView.setListener(emptyListener)
        swipe_refresh.setOnRefreshListener({ viewModel.retry() })
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