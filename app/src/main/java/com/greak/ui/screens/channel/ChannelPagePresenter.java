package com.greak.ui.screens.channel;

import com.chrono.src.common.constants.ConfigConstants;
import com.chrono.src.ui.list.ListViewLogic;
import com.chrono.src.ui.list.endless.EndlessListPresenter;
import com.greak.data.models.Post;
import com.greak.data.rest.RestService;

public class ChannelPagePresenter extends EndlessListPresenter<Post> {

	private long channelId;

	ChannelPagePresenter(long channelId, ListViewLogic<Post> contract) {
		super(contract);
		this.channelId = channelId;
	}

	@Override
	public void downloadDataFromApi(int offset, boolean showLoadingView) {
		super.downloadDataFromApi(offset, showLoadingView);

		call = RestService.getInstance()
				.getApiService()
				.getChannelPosts(channelId, offset, ConfigConstants.LIST_LOAD_LIMIT);
		call.enqueue(getCallback());
	}

}