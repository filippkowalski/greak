package com.greak.ui.screens.channel;

import android.content.Context;

import com.chrono.src.ui.list.OnItemClickListener;
import com.chrono.src.ui.list.adapters.multitype.MultiTypeRecyclerAdapter;
import com.greak.data.models.Post;
import com.greak.data.models.PostWithoutPhoto;
import com.greak.ui.screens.post.PostAdapter;
import com.greak.ui.screens.post.PostWithoutPhotoAdapter;

import java.util.List;

public class ChannelSummaryMultiAdapter extends MultiTypeRecyclerAdapter<Post> {

	public ChannelSummaryMultiAdapter(Context context, List<Post> elements, OnItemClickListener<Post> listener) {
		super(elements);

		PostAdapter postAdapter = new PostAdapter(context, listener);
		PostWithoutPhotoAdapter postWithoutPhotoAdapter = new PostWithoutPhotoAdapter(context, listener);

		getManager().addRowType(Post.class, postAdapter);
		getManager().addRowType(PostWithoutPhoto.class, postWithoutPhotoAdapter);
	}
}