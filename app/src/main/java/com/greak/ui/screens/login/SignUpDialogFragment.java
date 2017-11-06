package com.greak.ui.screens.login;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.greak.R;
import com.greak.ui.common.FragmentCommunicationUtils;

public class SignUpDialogFragment extends AppCompatDialogFragment {

	private OnEmailLoginListener listener;
	private EditText username;
	private EditText email;
	private EditText password;
	private CheckBox terms;

	public static SignUpDialogFragment newInstance() {
		return new SignUpDialogFragment();
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
				.setPositiveButton(R.string.sign_up, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (terms.isChecked()) {
							listener.onUserRegister(username.getText().toString(), email.getText().toString(),
									password.getText().toString());
						} else {
							Toast.makeText(getContext(), R.string.terms_must_be_accepted, Toast.LENGTH_SHORT).show();
						}
					}
				});
		return builder.create();
	}

	@NonNull
	private View initLayout() {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_sign_up, null);

		username = (EditText) view.findViewById(R.id.edit_login_username);
		email = (EditText) view.findViewById(R.id.edit_login_email);
		password = (EditText) view.findViewById(R.id.edit_login_password);
		terms = (CheckBox) view.findViewById(R.id.text_terms_accept);
		terms.setText(Html.fromHtml(getString(R.string.accept_terms)));
		terms.setMovementMethod(LinkMovementMethod.getInstance());

		return view;
	}
}