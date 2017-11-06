package com.greak.ui.screens.login;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.greak.R;
import com.greak.ui.common.FragmentCommunicationUtils;

public class SignInDialogFragment extends AppCompatDialogFragment {

	private OnEmailLoginListener listener;
	private EditText email;
	private EditText password;
	private Button signUp;

	public static SignInDialogFragment newInstance() {
		return new SignInDialogFragment();
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		listener = FragmentCommunicationUtils.getListenerOrThrow(this, OnEmailLoginListener.class);
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		View view = initLayout();
		builder.setView(view)
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dismiss();
					}
				})
				.setPositiveButton(R.string.sign_in, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						listener.onUserLogin(email.getText().toString(), password.getText().toString());
					}
				});
		return builder.create();
	}

	@NonNull
	private View initLayout() {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_sign_in, null);

		email = (EditText) view.findViewById(R.id.edit_login_email);
		password = (EditText) view.findViewById(R.id.edit_login_password);
		signUp = (Button) view.findViewById(R.id.button_sign_up);

		signUp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				SignUpDialogFragment.newInstance().show(getParentFragment().getChildFragmentManager(), null);
			}
		});

		return view;
	}
}