package com.greak.data.models

import android.os.Parcel
import android.os.Parcelable

class Header(var title: String) : FeedItem, Parcelable {

    constructor(source: Parcel) : this(
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(title)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Header> = object : Parcelable.Creator<Header> {
            override fun createFromParcel(source: Parcel): Header = Header(source)
            override fun newArray(size: Int): Array<Header?> = arrayOfNulls(size)
        }
    }
}