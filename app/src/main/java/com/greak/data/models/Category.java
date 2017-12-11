package com.greak.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class Category implements FeedItem, Parcelable {

	@Getter
	@SerializedName("id")
	private long id;

	@Getter
	@SerializedName("name")
	private String name;

	@Getter
	@SerializedName("cover_photo")
	private String coverPhoto;

	@Getter
	@SerializedName("color")
	private String color;

	@Getter
	@SerializedName("font_color")
	private String fontColor;

	@Getter
	@SerializedName("posts")
	private List<Post> posts = new ArrayList<>();

	// TODO tmp constructor
	public Category(String name) {
		this.name = name;
		this.color = "#06d1a5";
		this.fontColor = "#ffffff";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeString(this.name);
		dest.writeString(this.coverPhoto);
		dest.writeString(this.color);
		dest.writeString(this.fontColor);
		dest.writeTypedList(this.posts);
	}

	protected Category(Parcel in) {
		this.id = in.readLong();
		this.name = in.readString();
		this.coverPhoto = in.readString();
		this.color = in.readString();
		this.fontColor = in.readString();
		this.posts = in.createTypedArrayList(Post.CREATOR);
	}

	public static final Creator<Category> CREATOR = new Creator<Category>() {
		@Override
		public Category createFromParcel(Parcel source) {
			return new Category(source);
		}

		@Override
		public Category[] newArray(int size) {
			return new Category[size];
		}
	};
}
