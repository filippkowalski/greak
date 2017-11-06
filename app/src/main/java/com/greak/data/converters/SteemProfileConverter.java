package com.greak.data.converters;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.greak.data.models.SteemAccount;
import com.greak.data.models.SteemProfile;

import org.json.JSONException;
import org.json.JSONObject;

import eu.bittrade.libs.steemj.base.models.AccountName;

public class SteemProfileConverter {

	private AccountName accountName;

	public SteemProfileConverter(AccountName accountName) {
		this.accountName = accountName;
	}

	@NonNull
	public SteemAccount getSteemAccount(JSONObject profileJson) throws JSONException {
		SteemProfile steemProfile = new Gson().fromJson(profileJson.getString("profile"), SteemProfile.class);
		SteemAccount steemAccount = new SteemAccount();
		steemAccount.setName(accountName.getName());
		steemAccount.setAvatar(steemProfile.getAvatar());
		steemAccount.setCoverPhoto(steemProfile.getCoverImage());
		steemAccount.setDescription(steemProfile.getDescription());
		return steemAccount;
	}
}