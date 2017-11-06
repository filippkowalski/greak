package com.greak.ui.screens.category;

import android.content.Context;

import com.chrono.src.ui.list.OnItemClickListener;
import com.chrono.src.ui.list.adapters.multitype.MultiTypeRecyclerAdapter;
import com.greak.data.models.Channel;
import com.greak.ui.screens.main.discover.adapters.DiscoverChannelAdapter;

import java.util.List;

public class ChannelMultiAdapter extends MultiTypeRecyclerAdapter<Channel> {

	public ChannelMultiAdapter(Context context, List<Channel> elements, OnItemClickListener<Channel> listener) {
		super(elements);

		DiscoverChannelAdapter channelAdapter = new DiscoverChannelAdapter(context, listener);
		getManager().addRowType(Channel.class, channelAdapter);
	}
}