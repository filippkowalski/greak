package com.greak.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Created by Filip Kowalski on 6/28/17.
 */

public class UserConfiguration {

	@SerializedName("follows")
	@Getter
	private List<Long> follows = new ArrayList<>();

	@Getter
	private List<Long> subscriptions = new ArrayList<>();

	@Getter
	private List<Long> votes = new ArrayList<>();

	@Getter
	private String postHtmlConfig;
}