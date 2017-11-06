package com.greak.ui.screens.post;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.greak.R;
import com.greak.ui.common.resolvers.EmailLoginHeadlessFragment;
import com.greak.ui.common.resolvers.LoginHeadlessFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInSheetViewHolder {

	@BindView(R.id.container_login_bottom_sheet)
	protected FrameLayout bottomSheet;

	@BindView(R.id.text_title_login_sheet)
	protected TextView title;

	private BottomSheetBehavior bottomSheetBehavior;

	private Fragment fragment;

	public SignInSheetViewHolder(View view, Fragment fragment) {
		ButterKnife.bind(this, view);
		this.fragment = fragment;
	}

	public void initBottomSheet() {
		initLayout();
		initBehavior();
	}

	private void initLayout() {
		title.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
			}
		});
	}

	private void initBehavior() {
		bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
		bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
		bottomSheetBehavior.setPeekHeight(0);
	}

	public void showBottomSheet(boolean show) {
		if (show) {
			bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
		} else {
			bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
		}
	}

	@OnClick(R.id.button_login_with_email)
	public void onLoginWithEmail(View view) {
		commitHeadlessFragment(EmailLoginHeadlessFragment.newInstance());
	}

	private void commitHeadlessFragment(LoginHeadlessFragment headlessFragment) {
		fragment.getChildFragmentManager().beginTransaction()
				.add(headlessFragment, null)
				.commit();
	}
}