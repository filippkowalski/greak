package com.greak.ui.screens.main.subscriptions.emptystate;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chrono.src.ui.states.error.ErrorView;
import com.greak.R;
import com.greak.ui.screens.post.SignInSheetViewHolder;

public class LoginEmptyView extends ErrorView {

	private SignInSheetViewHolder loginSheetHolder;

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

		initLoginBottomSheet(getRootView(), fragment);

		TextView signIn = (TextView) view.findViewById(R.id.button_login);
		signIn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loginSheetHolder.showBottomSheet(true);
			}
		});
	}

	private void initLoginBottomSheet(View view, Fragment fragment) {
		loginSheetHolder = new SignInSheetViewHolder(view, fragment);
		loginSheetHolder.initBottomSheet();
	}

	@Override
	public void setError(int errorType) {
		// not used
	}
}