package com.greak.ui.common;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

public class RoundedViewUtils {

	public GradientDrawable getRoundedDrawable(String hexColor, int cornerRadius) {
		GradientDrawable gradientDrawable = new GradientDrawable();
		gradientDrawable.setCornerRadius(cornerRadius);
		if (hexColor != null) {
			gradientDrawable.setColor(Color.parseColor(hexColor));
		}
		return gradientDrawable;
	}
}
