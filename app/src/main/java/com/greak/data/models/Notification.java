package com.greak.data.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Filip Kowalski on 10.04.17.
 */

public class Notification {

	@Setter
	@Getter
	@SerializedName("id")
	private int id;

	@Setter
	@Getter
	@SerializedName("post")
	private Post post;

	@Setter
	@Getter
	@SerializedName("subscription")
	private Subscription subscription;
}
