package com.chrono.src.ui.states.error;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chrono.R;

public class GenericErrorView extends ErrorView {

	private TextView title;
	private TextView subtitle;
	private Button button;

	private OnClickListener buttonClickListener;

	public GenericErrorView(Context context, OnClickListener buttonClickListener) {
		super(context);
		this.buttonClickListener = buttonClickListener;

		init(context);
	}

	private void init(Context context) {
		ViewGroup.LayoutParams layoutParams =
				new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		setLayoutParams(layoutParams);
		View view = View.inflate(context, R.layout.layout_error, this);
		view.setBackgroundColor(Color.WHITE);

		title = (TextView) view.findViewById(R.id.text_error_title);
		subtitle = (TextView) view.findViewById(R.id.text_error_subtitle);
		button = (Button) view.findViewById(R.id.button_error);
		button.setOnClickListener(buttonClickListener);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// do nothing just fetch click
			}
		});
	}

	public void setError(@ErrorTypeGenerator.ErrorType int errorType) {
		title.setText(getErrorTitle(errorType));
		subtitle.setText(getErrorSubtitle(errorType));
	}

	private int getErrorTitle(@ErrorTypeGenerator.ErrorType int errorType) {
		switch (errorType) {
			case ErrorTypeGenerator.TYPE_UNSET:
				return R.string.error_unknown;
			case ErrorTypeGenerator.TYPE_CONNECTION_PROBLEM:
				return R.string.error_connection_problem;
			case ErrorTypeGenerator.TYPE_NO_CONTENT:
			default:
				return R.string.error_no_articles_to_show;
		}
	}

	private int getErrorSubtitle(int errorType) {
		switch (errorType) {
			case ErrorTypeGenerator.TYPE_UNSET:
				return R.string.error_try_again_later;
			case ErrorTypeGenerator.TYPE_CONNECTION_PROBLEM:
				return R.string.error_try_again_later;
			case ErrorTypeGenerator.TYPE_NO_CONTENT:
			default:
				return R.string.error_try_again_later;
		}
	}
}