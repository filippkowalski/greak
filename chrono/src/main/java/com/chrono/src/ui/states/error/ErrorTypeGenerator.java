package com.chrono.src.ui.states.error;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ErrorTypeGenerator {

	@Retention(RetentionPolicy.SOURCE)
	@IntDef({TYPE_UNSET, TYPE_CONNECTION_PROBLEM, TYPE_NO_CONTENT})
	public @interface ErrorType {
	}

	public static final int TYPE_UNSET = -1;
	public static final int TYPE_CONNECTION_PROBLEM = 0;
	public static final int TYPE_NO_CONTENT = 1;
}
