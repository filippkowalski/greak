package com.greak.ui.screens.login;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.greak.R;
import com.greak.ui.common.FragmentCommunicationUtils;
import com.greak.ui.common.resolvers.OnLoginListener;

public class SignInDialogFragment extends AppCompatDialogFragment {

	private OnLoginListener listener;
	private EditText username;
	private EditText password;
	private TextView footer;

	public static SignInDialogFragment newInstance() {
		return new SignInDialogFragment();
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		listener = FragmentCommunicationUtils.getListenerOrThrow(this, OnLoginListener.class);
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		View view = initLayout();
		builder.setView(view)
				.setNegativeButton(R.string.cancel, (dialog, which) -> dismiss())
				.setPositiveButton(R.string.sign_in, (dialog, which) ->
						//TODO implement password input
						listener.onUserLogin(username.getText().toString(), "TODO"));
		return builder.create();
	}

	@NonNull
	private View initLayout() {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_sign_in, null);

		username = view.findViewById(R.id.edit_login_username);
		password = view.findViewById(R.id.edit_login_password);
		footer = view.findViewById(R.id.text_login_footer);
		footer.setMovementMethod(LinkMovementMethod.getInstance());

		return view;
	}
}