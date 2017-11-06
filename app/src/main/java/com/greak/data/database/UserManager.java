package com.greak.data.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.chrono.src.common.constants.StringConstants;
import com.crashlytics.android.Crashlytics;
import com.greak.BuildConfig;
import com.greak.R;
import com.greak.data.models.Account;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import hugo.weaving.DebugLog;

public class UserManager {

	private static final String USERNAME = "USERNAME";
	private static final String FONT_SIZE = "FONT_SIZE";

	private Context context;

	public UserManager(Context context) {
		this.context = context;
	}

	@DebugLog
	public void initializeUser() {
		Account account = getAccount();
		UserInstance.getInstance().setAccount(account);
		if (isUserLogged()) {
			logUserToCrashlytics(account);
		}
	}

	private void logUserToCrashlytics(Account account) {
		if (!BuildConfig.DEBUG) {
			Crashlytics.setUserName(account.getUsername());
		}
	}

	public void setAccount(Account account) {
		setApiConfig(account.getUsername());

		SharedPreferences.Editor preferencesEditor = getSharedPreferences().edit();
		preferencesEditor.putString(USERNAME, account.getUsername());
		preferencesEditor.apply();

		initializeUser();
		UserInstance.getInstance().setFreshLogin(true);
	}

	private void setApiConfig(String username) {
		SteemJConfig steemJConfig = SteemJConfig.getInstance();
		steemJConfig.setDefaultAccount(new AccountName(username));
	}

	@Nullable
	@DebugLog
	public Account getAccount() {
		SharedPreferences preferences = getSharedPreferences();
		String username = preferences.getString(USERNAME, StringConstants.EMPTY);
		Account account = null;
		if (!username.isEmpty()) {
			account = new Account(username);
		}
		return account;
	}

	public void setFontSize(int fontSize) {
		SharedPreferences.Editor preferencesEditor = getSharedPreferences().edit();
		preferencesEditor.putInt(FONT_SIZE, fontSize);
		preferencesEditor.apply();
	}

	public int getFontSize() {
		return getSharedPreferences().getInt(FONT_SIZE, 12);
	}

	public void logout() {
		SharedPreferences.Editor preferencesEditor = getSharedPreferences().edit();
		preferencesEditor.remove(USERNAME);
		preferencesEditor.apply();

		UserInstance.getInstance().logout();
	}

	private boolean isUserLogged() {
		return getAccount() != null;
	}

	private SharedPreferences getSharedPreferences() {
		return context.getSharedPreferences(context.getString(R.string.user_preference_key), Context.MODE_PRIVATE);
	}
}