package com.greak.data.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

/**
 * Created by Filip Kowalski on 23.05.17.
 */
public class Vote {

	@Getter
	private int id;

	@Getter
	@SerializedName("current_votes_count")
	private int currentVotesCount;
}