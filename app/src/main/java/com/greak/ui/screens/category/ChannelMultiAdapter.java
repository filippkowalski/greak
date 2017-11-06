package com.greak.ui.screens.category;

import android.content.Context;

import com.chrono.src.ui.list.OnItemClickListener;
import com.chrono.src.ui.list.adapters.multitype.MultiTypeRecyclerAdapter;
import com.greak.data.models.SteemAccount;
import com.greak.ui.screens.main.trending.adapters.DiscoverChannelAdapter;

import java.util.List;

public class ChannelMultiAdapter extends MultiTypeRecyclerAdapter<SteemAccount> {

	public ChannelMultiAdapter(Context context, List<SteemAccount> elements, OnItemClickListener<SteemAccount> listener) {
		super(elements);

		DiscoverChannelAdapter channelAdapter = new DiscoverChannelAdapter(context, listener);
		getManager().addRowType(SteemAccount.class, channelAdapter);
	}
}