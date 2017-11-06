package com.greak.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Created by Filip Kowalski on 22.05.17.
 */

public class Posts implements Parcelable, FeedItem {

	@Getter
	@SerializedName("posts")
	private List<Post> posts = new ArrayList<>();

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedList(this.posts);
	}

	public Posts() {
	}

	protected Posts(Parcel in) {
		this.posts = in.createTypedArrayList(Post.CREATOR);
	}

	public static final Creator<Posts> CREATOR = new Creator<Posts>() {
		@Override
		public Posts createFromParcel(Parcel source) {
			return new Posts(source);
		}

		@Override
		public Posts[] newArray(int size) {
			return new Posts[size];
		}
	};
}