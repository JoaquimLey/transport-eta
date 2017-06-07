package com.joaquimley.buseta.ui.detail;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.joaquimley.buseta.R;
import com.joaquimley.buseta.repository.local.db.entity.BusEntity;
import com.joaquimley.buseta.repository.local.db.entity.StationEntity;
import com.joaquimley.buseta.ui.viewobject.BusStationViewObject;

import java.util.List;

public class BusProfileActivity extends AppCompatActivity implements LifecycleRegistryOwner, StationAdapter.ClickListener {

	private static final String KEY_BUS_ID = "busId";

	private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

	private BusProfileViewModel viewModel;

	private StationAdapter adapter;

	private Toolbar toolbarView;
	private TextView busDetailsView;
	private CollapsingToolbarLayout collapsingToolbarView;


	public static void start(Context context, long id) {
		Intent intent = new Intent(context, BusProfileActivity.class);
		intent.putExtra(KEY_BUS_ID, id);
		context.startActivity(intent);
	}

	@Override
	public LifecycleRegistry getLifecycle() {
		return this.lifecycleRegistry;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_profile);
		initViews();
		initViewModel();
	}

	private void initViews() {
		toolbarView = (Toolbar) findViewById(R.id.toolbar);
		collapsingToolbarView = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
		setSupportActionBar(toolbarView);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		busDetailsView = (TextView) findViewById(R.id.tv_bus_details);
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new StationAdapter(this);
		recyclerView.setAdapter(adapter);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				viewModel.fabButtonClicked();
			}
		});
	}

	private void initViewModel() {
		BusProfileViewModel.Factory factory = new BusProfileViewModel.Factory();
		viewModel = ViewModelProviders.of(this, factory).get(BusProfileViewModel.class);
		subscribeToDataStreams(viewModel);
	}

	private void subscribeToDataStreams(BusProfileViewModel viewModel) {
		viewModel.setBusId(getIntent().getExtras().getLong(KEY_BUS_ID));
		viewModel.getBus().observe(this, new Observer<BusEntity>() {
			@Override
			public void onChanged(@Nullable BusEntity busEntity) {
				if (busEntity != null) {
					hideBusProgress();
					showBusInUi(busEntity);
				} else {
					showBusProgress();
				}
			}
		});

		viewModel.getBusStationList().observe(this, new Observer<List<StationEntity>>() {
			@Override
			public void onChanged(@Nullable List<StationEntity> stationEntities) {
				if (stationEntities != null) {
					hideStationListProgress();
					showStationListInUit(stationEntities);
				} else {
					showStationListProgress();
				}
			}
		});

		viewModel.getBusStationViewObject().observe(this, new Observer<List<BusStationViewObject>>() {
			@Override
			public void onChanged(@Nullable List<BusStationViewObject> busStationViewObjects) {
				if (busStationViewObjects != null) {
					hideStationListProgress();

					for (int i = 0; i < busStationViewObjects.size(); i++) {
						Log.e("BusViewModel", "StationEntity " + busStationViewObjects.get(i).eta);
					}

				} else {
					showStationListProgress();
				}
			}
		});
	}

	private void showBusInUi(BusEntity busEntity) {
		collapsingToolbarView.setTitle(busEntity.getName());

		busDetailsView.setText("BusId: " + busEntity.getId() + "\nName: " + busEntity.getName());
	}

	private void showStationListInUit(List<StationEntity> stationEntities) {
		adapter.setItems(stationEntities);
	}

	private void showBusProgress() {
		// TODO: 30/05/2017
		Log.e("BusProfile", "BusEntity on changed null");
	}

	private void hideBusProgress() {
		// TODO: 30/05/2017

	}

	private void showStationListProgress() {
		// TODO: 30/05/2017
		Log.e("BusProfile", "StationEntity on changed null");
	}

	private void hideStationListProgress() {
		// TODO: 30/05/2017
	}

	@Override
	public void onItemClicked(StationEntity station) {
		viewModel.setStationId(station.getId());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
