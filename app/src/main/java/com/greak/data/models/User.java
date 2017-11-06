package com.greak.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

/**
 * Created by Filip Kowalski on 01.03.17.
 */

public class User implements Parcelable {

    @Getter
    private long id;

    @Getter
    @SerializedName("username")
    private String username;

    @Getter
    @SerializedName("first_name")
    private String firstName;

    @Getter
    @SerializedName("last_name")
    private String lastName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.username);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readLong();
        this.username = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
