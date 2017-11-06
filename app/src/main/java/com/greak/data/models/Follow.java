package com.greak.data.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

/**
 * Created by Filip Kowalski on 25.05.17.
 */

public class Follow {

    @Getter
    @SerializedName("id")
    private long id;

    @Getter
    @SerializedName("user")
    private long userId;

    @Getter
    @SerializedName("channel")
    private long channel;

    @Getter
    @SerializedName("fee")
    private double fee;
}
