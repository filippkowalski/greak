package com.greak.ui.screens.main.feed.emptystate;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chrono.src.ui.states.error.ErrorView;
import com.greak.R;
import com.greak.ui.screens.login.SignInDialogFragment;

public class LoginEmptyView extends ErrorView {

	public LoginEmptyView(Fragment fragment) {
		super(fragment.getContext());
		initView(fragment);
	}

	private void initView(Fragment fragment) {
		int matchParent = ViewGroup.LayoutParams.MATCH_PARENT;
		ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(matchParent, matchParent);
		setLayoutParams(layoutParams);

		View view = View.inflate(fragment.getContext(), R.layout.layout_empty_login, this);
		view.setBackgroundColor(Color.WHITE);

		TextView signIn = view.findViewById(R.id.button_login);
		signIn.setOnClickListener(v -> SignInDialogFragment.newInstance().show(
				fragment.getChildFragmentManager(), null));
	}

	@Override
	public void setError(int errorType) {
		// not used
	}
}