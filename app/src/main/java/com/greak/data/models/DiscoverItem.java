package com.greak.data.models;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

/**
 * Created by Filip Kowalski on 18.05.17.
 */

public class DiscoverItem {

	@Nullable
	@Getter
	@SerializedName("header_title")
	private String headerTitle;

	@Getter
	@SerializedName("type")
	private String type;

	@Getter
	@SerializedName("object")
	private Object object;

	public DiscoverItem(@Nullable String headerTitle, String type, Object object) {
		this.headerTitle = headerTitle;
		this.type = type;
		this.object = object;
	}
}