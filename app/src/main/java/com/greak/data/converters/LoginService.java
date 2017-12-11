package com.greak.data.converters;

import android.content.Context;

import com.greak.data.database.UserManager;
import com.greak.data.models.Account;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import eu.bittrade.libs.steemj.SteemJ;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import io.reactivex.Observable;

public class LoginService {

	private SteemJ steemJ;
	private final Context context;
	private String username;
	private final String password;

	public LoginService(Context context, String username, String password) {
		this.context = context;
		this.username = username;
		this.password = password;
	}

	public Observable<Boolean> getData() throws SteemResponseException, SteemCommunicationException {
		return Observable.create(e -> e.onNext(logInUser()));
	}

	private boolean logInUser() throws SteemResponseException, SteemCommunicationException, JSONException {
		steemJ = new SteemJ();
		AccountName accountName = new AccountName(username);
		boolean loggedIn = steemJ.login(accountName, password);
		if (loggedIn) {
			ImmutablePair<PublicKey, String> privateKeyFromPassword = SteemJ.getPrivateKeyFromPassword(accountName,
					PrivateKeyType.POSTING, password);

			List<ImmutablePair<PrivateKeyType, String>> privateKeys = new ArrayList<>();
			String postingPrivateKey = privateKeyFromPassword.getValue();
			privateKeys.add(new ImmutablePair<>(PrivateKeyType.POSTING, postingPrivateKey));

			SteemJConfig steemJConfig = SteemJConfig.getInstance();
			steemJConfig.setDefaultAccount(accountName);
			steemJConfig.getPrivateKeyStorage().addAccount(steemJConfig.getDefaultAccount(), privateKeys);

			UserManager userManager = new UserManager(context);
			userManager.setAccount(new Account(username, postingPrivateKey));
		}
		return loggedIn;
	}
}