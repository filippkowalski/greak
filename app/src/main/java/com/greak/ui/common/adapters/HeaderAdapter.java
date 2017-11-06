package com.greak.ui.common.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chrono.src.ui.list.adapters.multitype.MultiTypeAdapter;
import com.greak.R;
import com.greak.data.models.Header;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeaderAdapter implements MultiTypeAdapter<HeaderAdapter.ViewHolder, Header> {

	public HeaderAdapter() {
	}

	@NonNull
	@Override
	public ViewHolder getViewHolder(@NonNull ViewGroup viewGroup) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_header, viewGroup, false);
		return new ViewHolder(view);
	}

	@Override
	public void populateViewHolder(@NonNull ViewHolder holder, int position, final Header item) {
		holder.title.setText(item.getTitle());
	}

	static class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.text_header_view_title)
		TextView title;

		private ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}