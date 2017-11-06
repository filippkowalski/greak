package com.greak.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SteemAccount implements FeedItem, Parcelable {

	public long id;
	public String avatar;
	public String name;
	public String description;
	public Category category;
	public int followersCount;
	public String coverPhoto;
	public int postCount;

	public SteemAccount() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeString(this.avatar);
		dest.writeString(this.name);
		dest.writeString(this.description);
		dest.writeParcelable(this.category, flags);
		dest.writeInt(this.followersCount);
		dest.writeString(this.coverPhoto);
		dest.writeInt(this.postCount);
	}

	protected SteemAccount(Parcel in) {
		this.id = in.readLong();
		this.avatar = in.readString();
		this.name = in.readString();
		this.description = in.readString();
		this.category = in.readParcelable(Category.class.getClassLoader());
		this.followersCount = in.readInt();
		this.coverPhoto = in.readString();
		this.postCount = in.readInt();
	}

	public static final Creator<SteemAccount> CREATOR = new Creator<SteemAccount>() {
		@Override
		public SteemAccount createFromParcel(Parcel source) {
			return new SteemAccount(source);
		}

		@Override
		public SteemAccount[] newArray(int size) {
			return new SteemAccount[size];
		}
	};
}