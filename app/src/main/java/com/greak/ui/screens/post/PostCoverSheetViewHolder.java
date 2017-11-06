package com.greak.ui.screens.post;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.techisfun.android.topsheet.TopSheetBehavior;
import com.greak.R;
import com.greak.data.models.Post;
import com.greak.ui.common.TagViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostCoverSheetViewHolder {


	@BindView(R.id.container_cover_photo_sheet)
	protected FrameLayout coverPhotoSheet;

	@BindView(R.id.image_post_cover_photo)
	protected ImageView postCoverPhoto;

	@BindView(R.id.text_tag_post_cover)
	protected TextView tag;

	@BindView(R.id.text_title_post_cover)
	protected TextView title;

	private Context context;
	private Post post;
	private TopSheetBehavior topSheetBehavior;

	PostCoverSheetViewHolder(View view, Post post) {
		ButterKnife.bind(this, view);
		this.context = view.getContext();
		this.post = post;
	}

	void initSheet(boolean showCover) {
		initValues();
		initCoverPhotoImage();
		initBehavior(showCover);
		initCoverClickListener();
	}

	private void initValues() {
		TagViewUtils tagViewUtils = new TagViewUtils();
		tagViewUtils.setTagViewParams(post.getCategory(), tag);

		title.setText(post.getTitle());
	}

	private void initCoverPhotoImage() {
		Glide.with(context)
				.load(post.getCoverPhoto())
				.into(postCoverPhoto);
	}

	private void initBehavior(boolean showCover) {
		topSheetBehavior = TopSheetBehavior.from(coverPhotoSheet);
		expandSheet(showCover);
	}

	private void initCoverClickListener() {
		coverPhotoSheet.setOnClickListener(v -> expandSheet(false));
	}

	void expandSheet(boolean expand) {
		if (expand) {
			topSheetBehavior.setState(TopSheetBehavior.STATE_EXPANDED);
		} else {
			topSheetBehavior.setState(TopSheetBehavior.STATE_COLLAPSED);
		}
	}
}
