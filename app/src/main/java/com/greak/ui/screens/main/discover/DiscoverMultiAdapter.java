package com.greak.ui.screens.main.discover;

import android.content.Context;

import com.chrono.src.ui.list.OnItemClickListener;
import com.chrono.src.ui.list.adapters.multitype.MultiTypeRecyclerAdapter;
import com.greak.data.models.Categories;
import com.greak.data.models.Category;
import com.greak.data.models.Channel;
import com.greak.data.models.Channels;
import com.greak.data.models.FeedItem;
import com.greak.data.models.Header;
import com.greak.data.models.Post;
import com.greak.data.models.PostWithoutPhoto;
import com.greak.data.models.Posts;
import com.greak.ui.common.adapters.HeaderAdapter;
import com.greak.ui.screens.main.discover.adapters.DiscoverCategoriesAdapter;
import com.greak.ui.screens.main.discover.adapters.DiscoverChannelAdapter;
import com.greak.ui.screens.main.discover.adapters.DiscoverChannelsAdapter;
import com.greak.ui.screens.main.discover.adapters.DiscoverPostsAdapter;
import com.greak.ui.screens.post.PostAdapter;
import com.greak.ui.screens.post.PostWithoutPhotoAdapter;

import java.util.List;

public class DiscoverMultiAdapter extends MultiTypeRecyclerAdapter<FeedItem> {

	public DiscoverMultiAdapter(Context context, List<FeedItem> elements, OnItemClickListener<Post> postListener,
	                            OnItemClickListener<Channel> channelListener,
	                            OnItemClickListener<Category> categoryListener) {
		super(elements);

		HeaderAdapter headerAdapter = new HeaderAdapter();
		PostAdapter postAdapter = new PostAdapter(context, postListener);
		PostWithoutPhotoAdapter postWithoutPhotoAdapter = new PostWithoutPhotoAdapter(context, postListener);
		DiscoverPostsAdapter postsAdapter = new DiscoverPostsAdapter(context, postListener);
		DiscoverChannelAdapter channelAdapter = new DiscoverChannelAdapter(context, channelListener);
		DiscoverChannelsAdapter channelsAdapter = new DiscoverChannelsAdapter(context, channelListener);
		DiscoverCategoriesAdapter categoriesAdapter = new DiscoverCategoriesAdapter(context, categoryListener);

		getManager().addRowType(Header.class, headerAdapter);
		getManager().addRowType(Post.class, postAdapter);
		getManager().addRowType(PostWithoutPhoto.class, postWithoutPhotoAdapter);
		getManager().addRowType(Posts.class, postsAdapter);
		getManager().addRowType(Channel.class, channelAdapter);
		getManager().addRowType(Channels.class, channelsAdapter);
		getManager().addRowType(Categories.class, categoriesAdapter);
	}
}