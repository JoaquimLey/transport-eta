//package com.joaquimley.transporteta.home
//
//import android.arch.lifecycle.Observer
//import android.content.Context
//import android.os.Bundle
//import android.support.v4.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.joaquimley.transporteta.R
//import com.joaquimley.transporteta.model.data.ResourceState
//
///**
// * Created by joaquimley on 24/03/2018.
// */
//class FavouritesFragment : Fragment() {
//
////    private val recyclerView: RecyclerView by bindView(R.id.recycler_view)
////    private val contentLoadingView: ProgressBar by bindView(R.id.progress)
////    private val emptyView: EmptyView by bindView(R.id.view_empty)
////    private val swipeRefreshView: SwipeRefreshLayout by bindView(R.id.swipe_refresh)
////    private lateinit var viewModel: FavouritesViewModel
////
////    @Inject
////    lateinit var adapter: FavouritesAdapter
////    private var layoutManagerState: Parcelable? = null
//
//    override fun onAttach(context: Context?) {
////        AndroidSupportInjection.inject(this)
//        super.onAttach(context)
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
//            View = inflater.inflate(R.layout.fragment_favourites, container, false)
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setupRecyclerView()
//        setupListeners()
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        initViewModel()
//        observeFavourites()
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//    }
//
//    private fun observeFavourites() {
//        viewModel.getFavourites().observe(this,
//                Observer {
//                    if (it != null) handleDataState(it.status, it.data, it.message)
//                })
//    }
//
//    private fun handleDataState(resourceState: ResourceState, data: List<FavouriteView>?,
//                                message: String?) {
//        when (resourceState) {
//            ResourceState.LOADING -> setupScreenForLoadingState(true)
//            ResourceState.SUCCESS -> setupScreenForSuccess(data)
//            ResourceState.ERROR -> setupScreenForError(message)
//            ResourceState.EMPTY -> setupScreenForEmpty()
//        }
//    }
//
//    private fun setupScreenEmptyState(isEmpty: Boolean) {
//        emptyView.setVisibility(isEmpty)
//        recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
//    }
//
//    private fun setupScreenForLoadingState(isLoading: Boolean) {
//        setupScreenEmptyState(false)
//        if (isLoading) {
//            if (swipeRefreshView.isRefreshing.not() && adapter.isEmpty()) {
//                contentLoadingView.visibility = View.VISIBLE
//            }
//        } else {
//            swipeRefreshView.isRefreshing = false
//            contentLoadingView.visibility = View.GONE
//            adapter.removeLoadingView()
//        }
//    }
//
//    private fun setupScreenForSuccess(favouriteViewList: List<FavouriteView>?) {
//        setupScreenForLoadingState(false)
//        setupScreenEmptyState(false)
//        if (favouriteViewList != null && favouriteViewList.isEmpty()) {
//            adapter.add(favouriteViewList.map { favouriteMapper.mapToViewModel(it) })
//        } else {
//            setupScreenForLoadingState(false)
//            setupScreenEmptyState(true)
//        }
//        if (recyclerView.adapter == null) {
//            recyclerView.adapter = adapter
//        }
//    }
//
//    private fun setupScreenForEmpty() {
//        if (adapter.isEmpty()) {
//            emptyView.setVisibility(true)
//        }
//    }
//
//    private fun setupScreenForError(message: String?) {
//        // Todo
//        Timber.e("setupScreenForError: $message")
//    }
//
//
//    private fun initViewModel() {
//        viewModel = ViewModelProviders.of(activity as AppCompatActivity, viewModelFactory).get(FavouritesViewModel::class.java)
//    }
//
//    private fun setupRecyclerView() {
//        recyclerView.setHasFixedSize(true)
//        recyclerView.layoutManager = LinearLayoutManager(context)
//        recyclerView.adapter = adapter
//    }
//
//    private fun setupListeners() {
//        emptyView.setListener(emptyListener)
//        swipeRefreshView.setOnRefreshListener({ viewModel.retry() })
//    }
//
//    private val emptyListener = object : EmptyListener {
//        override fun onEmptyButtonPressed() {
//            viewModel.retry()
//        }
//    }
//
//    companion object {
//        private const val KEY_USER_ID = "keyUserId"
//
//        fun newInstance(userId: String? = ""): FavouritesFragment {
//            val fragment = FavouritesFragment()
//            val args = Bundle()
//            args.putString(KEY_USER_ID, userId)
//            fragment.arguments = args
//            return fragment
//        }
//    }
//}
//
///*
//
//LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);
// ...
// Cancellable compositionCancellable = LottieComposition.Factory.fromJson(getResources(), jsonObject, (composition) -> {
//     animationView.setComposition(composition);
//     animationView.playAnimation();
// });
//
// // Cancel to stop asynchronous loading of composition
// // compositionCancellable.cancel();
//
// */