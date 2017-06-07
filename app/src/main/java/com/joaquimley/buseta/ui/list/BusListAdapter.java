package com.joaquimley.buseta.ui.list;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joaquimley.buseta.R;
import com.joaquimley.buseta.repository.local.db.entity.BusEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joaquimley on 25/05/2017.
 */

class BusListAdapter extends RecyclerView.Adapter<BusListAdapter.BusViewHolder> {

	private static final String TAG = "BusListAdapter";

	private final ClickListener listener;
	private final List<BusEntity> dataSet;

	public BusListAdapter(ClickListener listener) {
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
	public BusListAdapter.BusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bus_list, parent, false);
		return new BusViewHolder(view);
	}

	@Override
	public void onBindViewHolder(BusListAdapter.BusViewHolder holder, int position) {
		BusEntity item = dataSet.get(position);
		holder.idView.setText(String.valueOf(item.getId()));
		holder.nameView.setText(item.getName());
	}

	public void setItems(List<BusEntity> itemsList) {
		dataSet.clear();
		dataSet.addAll(itemsList);
		notifyDataSetChanged();
	}

	public void add(BusEntity item) {
		add(null, item);
	}

	public void add(@Nullable Integer position, BusEntity item) {
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

	class BusViewHolder extends RecyclerView.ViewHolder {
		final TextView idView;
		final TextView nameView;

		BusViewHolder(View view) {
			super(view);
			idView = (TextView) view.findViewById(R.id.tv_id);
			nameView = (TextView) view.findViewById(R.id.tv_name);
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
		void onItemClicked(BusEntity bus);
	}
}
