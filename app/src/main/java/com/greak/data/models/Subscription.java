package com.greak.data.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

/**
 * Created by Filip Kowalski on 01.03.17.
 */

public class Subscription {

    @Getter
    @SerializedName("id")
    private long id;

    @Getter
    @SerializedName("user")
    private long userId;

    @Getter
    @SerializedName("channel")
    private SteemAccount steemAccount;

    @Getter
    @SerializedName("fee")
    private double fee;
}
