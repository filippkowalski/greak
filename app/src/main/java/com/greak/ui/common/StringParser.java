package com.greak.ui.common;

import android.support.annotation.Nullable;

import java.util.List;

public class StringParser {

	// TODO write regex for this
	@Nullable
	public static String extractFirstFoundImageUrl(List<String> urls) {
		for (String url : urls) {
			if (url.endsWith("png") || url.endsWith("PNG")
					|| url.endsWith("jpg") || url.endsWith("JPG")
					|| url.endsWith("jpeg") || url.endsWith("JPEG")) {
				return url;
			}
		}
		return null;
	}
}
