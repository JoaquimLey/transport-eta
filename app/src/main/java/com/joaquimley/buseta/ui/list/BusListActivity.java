package com.joaquimley.buseta.ui.list;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.joaquimley.buseta.R;
import com.joaquimley.buseta.repository.local.db.entity.BusEntity;
import com.joaquimley.buseta.ui.detail.BusProfileActivity;

import java.util.List;

public class BusListActivity extends AppCompatActivity implements LifecycleRegistryOwner,
		BusListAdapter.ClickListener {

	private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

	private BusListViewModel viewModel;

	private BusListAdapter adapter;
	private ContentLoadingProgressBar progressDialogView;
	private SwipeRefreshLayout swipeToRefreshView;

	@Override
	public LifecycleRegistry getLifecycle() {
		return this.lifecycleRegistry;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_list);
		initViews();
		initViewModel();
	}

	private void initViews() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle(R.string.title_bus_list_activity);
		setSupportActionBar(toolbar);

		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
		adapter = new BusListAdapter(this);
		recyclerView.setAdapter(adapter);

		progressDialogView = (ContentLoadingProgressBar) findViewById(R.id.progress_dialog);

		swipeToRefreshView = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
		swipeToRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				viewModel.onPullRequested();
			}
		});

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				viewModel.onFabButtonClicked();
			}
		});
	}

	private void initViewModel() {
		viewModel = ViewModelProviders.of(this).get(BusListViewModel.class);
		subscribeDataStreams(viewModel);
	}

	private void subscribeDataStreams(BusListViewModel viewModel) {
		viewModel.getBusList().observe(this, new Observer<List<BusEntity>>() {
			@Override
			public void onChanged(@Nullable List<BusEntity> busEntities) {
				if (busEntities != null) {
					hideProgress();
					showBusListInUi(busEntities);
				} else {
					showProgress();
				}
			}
		});

		viewModel.getSelectedItem().observe(this, new Observer<BusEntity>() {
			@Override
			public void onChanged(@Nullable BusEntity busEntity) {
				if (busEntity != null) {
					hideProgress();
				}
			}
		});
	}

	private void showBusListInUi(List<BusEntity> busEntities) {
		adapter.setItems(busEntities);
	}

	private void showProgress() {
		if (adapter.isEmpty() && !progressDialogView.isShown()) {
			progressDialogView.setVisibility(View.VISIBLE);
		}
	}

	private void hideProgress() {
		if (swipeToRefreshView.isRefreshing()) {
			swipeToRefreshView.setRefreshing(false);
		}

		if (progressDialogView.isShown()) {
			progressDialogView.setVisibility(View.GONE);
		}
	}

	@Override
	public void onItemClicked(BusEntity bus) {
		viewModel.onListItemClicked(bus);
		openBusDetailActivity(bus);
	}

	private void openBusDetailActivity(BusEntity bus) {
		BusProfileActivity.start(this, bus.getId());
	}
}
