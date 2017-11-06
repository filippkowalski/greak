package com.greak.ui.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greak.R;

public class HeaderView extends LinearLayout {

	private String title;
	private TextView titleTextView;

	public HeaderView(Context context) {
		super(context);
		initLayout(context);
	}

	public HeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		getAttributes(context, attrs);
		initLayout(context);
	}

	public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		getAttributes(context, attrs);
		initLayout(context);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public HeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		getAttributes(context, attrs);
		initLayout(context);
	}

	private void getAttributes(Context context, AttributeSet attrs) {
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HeaderView, 0, 0);
		try {
			title = a.getString(R.styleable.HeaderView_textTitle);
		} finally {
			a.recycle();
		}
	}

	private void initLayout(Context context) {
		inflate(context, R.layout.view_header, this);
		titleTextView = (TextView) findViewById(R.id.text_header_view_title);

		setOrientation(HORIZONTAL);
		setGravity(Gravity.CENTER_VERTICAL);
	}

	public void setTitle(@StringRes int stringRes) {
		titleTextView.setText(getContext().getString(stringRes));
	}
}