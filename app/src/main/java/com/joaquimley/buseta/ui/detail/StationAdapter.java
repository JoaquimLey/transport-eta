package com.joaquimley.buseta.ui.detail;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joaquimley.buseta.R;
import com.joaquimley.buseta.repository.local.db.entity.StationEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joaquimley on 25/05/2017.
 */

class StationAdapter extends RecyclerView.Adapter<StationAdapter.StationViewHolder> {

	private static final String TAG = "StationAdapter";

	private final ClickListener listener;
	private final List<StationEntity> dataSet;

	public StationAdapter(ClickListener listener) {
		setHasStableIds(true);
		this.listener = listener;
		this.dataSet = new ArrayList<>();
	}

	@Override
	public long getItemId(int position) {
		return dataSet.size() >= position ? dataSet.get(position).getId() : View.NO_ID;
	}

	@Override
	public int getItemCount() {
		return dataSet.size();
	}

	@Override
	public StationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_station_list, parent, false);
		return new StationViewHolder(view);
	}

	@Override
	public void onBindViewHolder(StationViewHolder holder, int position) {
		StationEntity item = dataSet.get(position);
		holder.idView.setText(String.valueOf(item.getId()));
		holder.nameView.setText(item.getName());
		holder.locationView.setText("Coords: " + item.getLocation().getLatitude() + " " + item.getLocation().getLongitude());
	}

	public void setItems(List<StationEntity> itemsList) {
		dataSet.clear();
		dataSet.addAll(itemsList);
		notifyDataSetChanged();
	}

	public void add(StationEntity item) {
		add(null, item);
	}

	public void add(@Nullable Integer position, StationEntity item) {
		if (position != null) {
			dataSet.add(position, item);
			notifyItemInserted(position);
		} else {
			dataSet.add(item);
			notifyItemInserted(dataSet.size() - 1);
		}
	}

	public void remove(int position) {
		if (dataSet.size() < position) {
			Log.w(TAG, "The item at position: " + position + " doesn't exist");
			return;
		}
		dataSet.remove(position);
		notifyItemRemoved(position);
	}

	public boolean isEmpty() {
		return dataSet.isEmpty();
	}

	class StationViewHolder extends RecyclerView.ViewHolder {
		final TextView idView;
		final TextView nameView;
		final TextView locationView;

		StationViewHolder(View view) {
			super(view);
			idView = (TextView) view.findViewById(R.id.tv_id);
			nameView = (TextView) view.findViewById(R.id.tv_name);
			locationView = (TextView) view.findViewById(R.id.tv_location);

			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (listener != null) {
						listener.onItemClicked(dataSet.get(getAdapterPosition()));
					}
				}
			});
		}
	}

	interface ClickListener {
		void onItemClicked(StationEntity station);
	}
}
