package com.joaquimley.buseta.ui.detail;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.Nullable;

import com.joaquimley.buseta.repository.BusRepository;
import com.joaquimley.buseta.repository.BusStationRepository;
import com.joaquimley.buseta.repository.StationRepository;
import com.joaquimley.buseta.repository.local.db.AppDatabase;
import com.joaquimley.buseta.repository.local.db.DatabaseCreator;
import com.joaquimley.buseta.repository.local.db.entity.BusEntity;
import com.joaquimley.buseta.repository.local.db.entity.StationEntity;
import com.joaquimley.buseta.repository.remote.RemoteRepository;
import com.joaquimley.buseta.ui.viewobject.BusStationViewObject;

import java.util.List;

/**
 * Created by joaquimley on 24/05/2017.
 */

public class BusProfileViewModel extends ViewModel implements BusProfileContract.ViewActions, BusProfileContract.DataStreams {

	private BusRepository busRepository;
	private BusStationRepository busStationRepository;
	private StationRepository stationRepository;

	private MutableLiveData<Long> busId = new MutableLiveData<>();

	private LiveData<BusEntity> busObservable;
	private LiveData<List<StationEntity>> stationListObservable;
	private LiveData<List<BusStationViewObject>> stationViewObjectObservable;

	public BusProfileViewModel() {
		this(null, null, null);
	}

	public BusProfileViewModel(@Nullable BusRepository busRepository, @Nullable BusStationRepository busStationRepository,
	                           @Nullable StationRepository stationRepository) {
		if (this.busRepository != null && this.busStationRepository != null
				&& this.stationRepository != null) {
			// ViewModel is created per Activity, so instantiate once.
			return;
		}

		if (busRepository != null) {
			this.busRepository = busRepository;
		}
		if (busStationRepository != null) {
			this.busStationRepository = busStationRepository;
		}
		if (stationRepository != null) {
			this.stationRepository = stationRepository;
		}

		// Making it as a return method to be more explicit
		busObservable = subscribeToLocalData();
		stationListObservable = subscribeStationListObservable();

		stationViewObjectObservable = subscribeStationListViewObjectObservable();
	}

	@Override
	public void setBusId(long busId) {
		if (this.busId.getValue() != null && this.busId.getValue() == busId) {
			return;
		}
		this.busId.setValue(busId);
	}

	@Override
	public void setStationId(long stationId) {
		// Set station id which should fire the startStationDetailActivity
	}

	@Override
	public void fabButtonClicked() {
		// TODO: 30/05/2017 Remove this
		// Same as favorite?
	}

	@Override
	public void onFavoriteButtonClicked() {
		//  Write to database and do other shenanigans
	}

	@Override
	public LiveData<BusEntity> getBus() {
		return busObservable;
	}

	@Override
	public LiveData<List<StationEntity>> getBusStationList() {
		return stationListObservable;
	}

	public LiveData<List<BusStationViewObject>> getBusStationViewObject() {
		return stationViewObjectObservable;
	}

	private LiveData<BusEntity> subscribeToLocalData() {
		// Observe if/when database is created.
		return Transformations.switchMap(DatabaseCreator.getInstance().isDatabaseCreated(),
				new Function<Boolean, LiveData<BusEntity>>() {
					@Override
					public LiveData<BusEntity> apply(Boolean isDbCreated) {
						if (Boolean.TRUE.equals(isDbCreated)) {
							onDatabaseCreated();
							return subscribeBusObservable();
						}
						return null;
					}
				});
	}

	/**
	 * Listens for {@link MutableLiveData<Long>} busId changes, and loads the bus(id) from db.
	 *
	 * @return Database changes for Bus with id busId
	 */
	private LiveData<BusEntity> subscribeBusObservable() {
		return Transformations.switchMap(busId,
				new Function<Long, LiveData<BusEntity>>() {
					@Override
					public LiveData<BusEntity> apply(Long input) {
						if (input != null) {
							return busRepository.load(input);
						}
						return null;
					}
				});
	}

	private LiveData<List<StationEntity>> subscribeStationListObservable() {
		return Transformations.switchMap(busId,
				new Function<Long, LiveData<List<StationEntity>>>() {
					@Override
					public LiveData<List<StationEntity>> apply(Long busId) {
						if (busId != null) {
							return busStationRepository.loadBusStations(busId);
						}
						return null;
					}
				});
	}

	private LiveData<List<BusStationViewObject>> subscribeStationListViewObjectObservable() {
		return Transformations.switchMap(busId,
				new Function<Long, LiveData<List<BusStationViewObject>>>() {
					@Override
					public LiveData<List<BusStationViewObject>> apply(Long input) {
						if (input != null) {
							return busStationRepository.loadBusStationsEta(input);
						}
						return null;
					}
				});
	}

	// TODO: 30/05/2017 Shouldn't be here (inversion principle), inject when migrated to Dagger2
	private void onDatabaseCreated() {
		AppDatabase appDatabase = DatabaseCreator.getInstance().getDatabase();
		if (busRepository == null) {
			busRepository = BusRepository.getInstance(RemoteRepository.getInstance(), appDatabase.busDao());
		}

		if (busStationRepository == null) {
			busStationRepository = BusStationRepository.getInstance(RemoteRepository.getInstance(), appDatabase.busStationDao());
		}
		if (stationRepository == null) {
			stationRepository = StationRepository.getInstance(RemoteRepository.getInstance(), appDatabase.stationDao());
		}
	}

	/**
	 * A creator is used to inject the product ID into the ViewModel
	 * <p>
	 * This creator is to showcase how to inject dependencies into ViewModels. It's not
	 * actually necessary in this case, as the product ID can be passed in a public method.
	 */
	public static class Factory extends ViewModelProvider.NewInstanceFactory {

		private final BusRepository mBusRepository;
		private final BusStationRepository mBusStationRepository;
		private final StationRepository mStationRepository;

		public Factory() {
			this(null, null, null);
		}

		public Factory(AppDatabase appDatabase) {
			this(BusRepository.getInstance(RemoteRepository.getInstance(), appDatabase.busDao()),
					BusStationRepository.getInstance(RemoteRepository.getInstance(), appDatabase.busStationDao()),
					StationRepository.getInstance(RemoteRepository.getInstance(), appDatabase.stationDao()));
		}

		public Factory(@Nullable BusRepository busRepository, @Nullable BusStationRepository busStationRepository,
		               @Nullable StationRepository stationRepository) {
			mBusRepository = busRepository;
			mBusStationRepository = busStationRepository;
			mStationRepository = stationRepository;
		}

		@Override
		public <T extends ViewModel> T create(Class<T> modelClass) {
			//noinspection unchecked
			return (T) new BusProfileViewModel(mBusRepository, mBusStationRepository, mStationRepository);
		}
	}
}
