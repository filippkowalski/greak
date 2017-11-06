package com.greak.data.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class SteemProfile {

	@SerializedName("profile_image")
	private String avatar;

	@SerializedName("name")
	private String name;

	@SerializedName("about")
	private String description;

	@SerializedName("location")
	private String location;

	@SerializedName("cover_image")
	private String coverImage;
}