package com.greak.ui.common;

import android.graphics.Color;
import android.widget.TextView;

import com.greak.data.models.Category;

public class TagViewUtils {

	public void setTagViewParams(Category category, TextView tag) {
		RoundedViewUtils viewUtils = new RoundedViewUtils();
		tag.setText(category.getName());
		tag.setTextColor(Color.parseColor(category.getFontColor()));
		tag.setBackground(viewUtils.getRoundedDrawable(category.getColor(), 100));
	}
}