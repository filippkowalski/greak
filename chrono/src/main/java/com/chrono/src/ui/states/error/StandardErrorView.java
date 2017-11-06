package com.chrono.src.ui.states.error;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chrono.R;

public class StandardErrorView extends ErrorView {

	private TextView titleTextView;
	private TextView subtitleTextView;
	private Button button;

	private String title;
	private String subtitle;
	private String buttonText;

	private OnClickListener buttonClickListener;

	public StandardErrorView(Context context, OnClickListener buttonClickListener, String title, String subtitle,
	                         String buttonText) {
		super(context);
		this.buttonClickListener = buttonClickListener;
		this.title = title;
		this.subtitle = subtitle;
		this.buttonText = buttonText;

		init(context);
	}

	private void init(Context context) {
		ViewGroup.LayoutParams layoutParams =
				new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		setLayoutParams(layoutParams);
		View view = View.inflate(context, R.layout.layout_error, this);
		view.setBackgroundColor(Color.WHITE);

		titleTextView = (TextView) view.findViewById(R.id.text_error_title);
		subtitleTextView = (TextView) view.findViewById(R.id.text_error_subtitle);

		button = (Button) view.findViewById(R.id.button_error);
		button.setOnClickListener(buttonClickListener);

		initValues();
	}

	private void initValues() {
		titleTextView.setText(title);
		subtitleTextView.setText(subtitle);
		button.setText(buttonText);
	}

	@Override
	public void setError(@ErrorTypeGenerator.ErrorType int errorType) {
		// not used
	}
}