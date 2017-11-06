package com.greak.ui.common.resolvers;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.greak.ui.screens.login.OnEmailLoginListener;
import com.greak.ui.screens.login.SignInDialogFragment;

public class EmailLoginHeadlessFragment extends LoginHeadlessFragment implements OnEmailLoginListener {

	public static EmailLoginHeadlessFragment newInstance() {
		return new EmailLoginHeadlessFragment();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showSignInDialog();
	}

	public void showSignInDialog() {
		SignInDialogFragment.newInstance().show(getChildFragmentManager(), null);
	}

	@Override
	public void onUserLogin(String email, String password) {
		loginUser(email, password);
	}

	public void loginUser(String email, String password) {

	}

	@Override
	public void onUserRegister(String username, String email, String password) {
		registerUser(username, email, password);
	}

	public void registerUser(final String username, final String email, final String password) {
	}
}