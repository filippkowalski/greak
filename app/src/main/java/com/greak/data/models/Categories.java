package com.greak.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Created by Filip Kowalski on 29.05.17.
 */

public class Categories implements FeedItem, Parcelable {

	@Getter
	private String headerTitle;

	@Getter
	private List<Category> categories = new ArrayList<>();

	public Categories(String headerTitle, List<Category> categories) {
		this.headerTitle = headerTitle;
		this.categories = categories;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.headerTitle);
		dest.writeTypedList(this.categories);
	}

	protected Categories(Parcel in) {
		this.headerTitle = in.readString();
		this.categories = in.createTypedArrayList(Category.CREATOR);
	}

	public static final Creator<Categories> CREATOR = new Creator<Categories>() {
		@Override
		public Categories createFromParcel(Parcel source) {
			return new Categories(source);
		}

		@Override
		public Categories[] newArray(int size) {
			return new Categories[size];
		}
	};
}