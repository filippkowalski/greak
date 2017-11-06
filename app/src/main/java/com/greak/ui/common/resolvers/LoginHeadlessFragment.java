package com.greak.ui.common.resolvers;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.greak.R;
import com.greak.data.database.UserManager;
import com.greak.data.database.UserResolver;
import com.greak.data.models.Account;
import com.greak.service.tasks.SynchronizationService;
import com.greak.ui.common.FragmentCommunicationUtils;

public class LoginHeadlessFragment extends Fragment {

	private OnLoginListener onLoginListener;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		onLoginListener = FragmentCommunicationUtils.getListenerOrThrow(this, OnLoginListener.class);
	}

	protected void onLoginSuccessful(Account account) {
		UserManager userManager = new UserManager(getContext());
		userManager.setAccount(account);

		Toast.makeText(getContext(), R.string.login_successful, Toast.LENGTH_SHORT).show();

		SynchronizationService synchronizationService = new SynchronizationService();
		synchronizationService.synchronize(getContext());

		onLoginListener.onLoginSuccessful();
	}

	protected void onLoginFailure(String errorMessage) {
		Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();

		UserResolver userResolver = new UserResolver();
		userResolver.logout(getContext());
	}
}