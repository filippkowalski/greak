package com.greak.ui.screens.main.common;

import android.content.Context;

import com.chrono.src.ui.list.OnItemClickListener;
import com.chrono.src.ui.list.adapters.multitype.MultiTypeRecyclerAdapter;
import com.greak.data.models.FeedItem;
import com.greak.data.models.Header;
import com.greak.data.models.Post;
import com.greak.data.models.PostWithoutPhoto;
import com.greak.ui.common.adapters.HeaderAdapter;
import com.greak.ui.screens.post.PostAdapter;
import com.greak.ui.screens.post.PostWithoutPhotoAdapter;

import java.util.List;

public class PostsMultiAdapter extends MultiTypeRecyclerAdapter<FeedItem> {

	public PostsMultiAdapter(Context context, List<FeedItem> elements, OnItemClickListener<Post> listener) {
		super(elements);

		HeaderAdapter headerAdapter = new HeaderAdapter();
		PostAdapter postAdapter = new PostAdapter(context, listener);
		PostWithoutPhotoAdapter postWithoutPhotoAdapter = new PostWithoutPhotoAdapter(context, listener);

		getManager().addRowType(Header.class, headerAdapter);
		getManager().addRowType(Post.class, postAdapter);
		getManager().addRowType(PostWithoutPhoto.class, postWithoutPhotoAdapter);

	}
}