package com.greak.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Created by Filip Kowalski on 29.05.17.
 */

public class Channels implements FeedItem, Parcelable {

	@Getter
	private String headerTitle;

	@Getter
	private List<SteemAccount> steemAccounts = new ArrayList<>();

	public Channels(String headerTitle, List<SteemAccount> steemAccounts) {
		this.headerTitle = headerTitle;
		this.steemAccounts = steemAccounts;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.headerTitle);
		dest.writeTypedList(this.steemAccounts);
	}

	protected Channels(Parcel in) {
		this.headerTitle = in.readString();
		this.steemAccounts = in.createTypedArrayList(SteemAccount.CREATOR);
	}

	public static final Creator<Channels> CREATOR = new Creator<Channels>() {
		@Override
		public Channels createFromParcel(Parcel source) {
			return new Channels(source);
		}

		@Override
		public Channels[] newArray(int size) {
			return new Channels[size];
		}
	};
}