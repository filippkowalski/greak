package com.greak.ui.screens.main.common;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.greak.ui.screens.main.common.ListType.TYPE_NEW;
import static com.greak.ui.screens.main.common.ListType.TYPE_TRENDING;

@IntDef({TYPE_NEW, TYPE_TRENDING})
@Retention(RetentionPolicy.SOURCE)
public @interface ListType {
	int TYPE_NEW = 0;
	int TYPE_TRENDING = 1;
}