package com.greak.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.greak.common.utils.DateUtils;

import java.text.ParseException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post implements Parcelable, FeedItem {

	private long id;
	private SteemAccount steemAccount;
	private String title;
	private String dateCreated;
	private String coverPhoto;
	private String teaser;
	private String content;
	private int commentsCount;
	private int votesCount;
	private Double moneyEarned;
	private Category category;
	private int readTime;
	private String permlink;

	public int getReadTime() {
		if (readTime <= 0) {
			return 1;
		}
		return readTime;
	}

	public long getDateCreatedAsTimestamp() {
		try {
			return DateUtils.getTimeInMillis(dateCreated);
		} catch (ParseException e) {
			return 0;
		}
	}

	public Post() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeParcelable(this.steemAccount, flags);
		dest.writeString(this.title);
		dest.writeString(this.dateCreated);
		dest.writeString(this.coverPhoto);
		dest.writeString(this.teaser);
		dest.writeString(this.content);
		dest.writeInt(this.commentsCount);
		dest.writeInt(this.votesCount);
		dest.writeValue(this.moneyEarned);
		dest.writeParcelable(this.category, flags);
		dest.writeInt(this.readTime);
		dest.writeString(this.permlink);
	}

	protected Post(Parcel in) {
		this.id = in.readLong();
		this.steemAccount = in.readParcelable(SteemAccount.class.getClassLoader());
		this.title = in.readString();
		this.dateCreated = in.readString();
		this.coverPhoto = in.readString();
		this.teaser = in.readString();
		this.content = in.readString();
		this.commentsCount = in.readInt();
		this.votesCount = in.readInt();
		this.moneyEarned = (Double) in.readValue(Double.class.getClassLoader());
		this.category = in.readParcelable(Category.class.getClassLoader());
		this.readTime = in.readInt();
		this.permlink = in.readString();
	}

	public static final Creator<Post> CREATOR = new Creator<Post>() {
		@Override
		public Post createFromParcel(Parcel source) {
			return new Post(source);
		}

		@Override
		public Post[] newArray(int size) {
			return new Post[size];
		}
	};
}