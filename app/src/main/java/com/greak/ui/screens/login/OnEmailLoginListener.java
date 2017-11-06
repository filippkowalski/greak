package com.greak.ui.screens.login;

public interface OnEmailLoginListener {

	void onUserLogin(String email, String password);
	void onUserRegister(String username, String email, String password);
}
