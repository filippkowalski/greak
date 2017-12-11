package com.greak.ui.screens.main;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.greak.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginByObserveTest {

	@Rule
	public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

	@Test
	public void loginByObserve2Test() {
		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction recyclerView = onView(
				allOf(withId(R.id.recycler_view_horizontal_category),
						childAtPosition(
								childAtPosition(
										withId(R.id.layout_item_horizontal),
										0),
								1),
						isDisplayed()));
		recyclerView.perform(actionOnItemAtPosition(0, click()));

		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction recyclerView2 = onView(
				allOf(withId(R.id.recycler_view),
						childAtPosition(
								allOf(withId(R.id.layout_swipe_refresh),
										childAtPosition(
												withId(R.id.image_cover_item_channel),
												0)),
								0),
						isDisplayed()));
		recyclerView2.perform(actionOnItemAtPosition(0, click()));

		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction appCompatTextView = onView(
				allOf(withId(R.id.button_observe), withText("Follow"),
						childAtPosition(
								childAtPosition(
										withClassName(is("android.widget.LinearLayout")),
										1),
								5),
						isDisplayed()));
		appCompatTextView.perform(click());

		ViewInteraction appCompatTextView2 = onView(
				allOf(withId(R.id.button_login_with_email), withText("Sign in with email"),
						childAtPosition(
								childAtPosition(
										withId(R.id.container_login_bottom_sheet),
										0),
								4),
						isDisplayed()));
		appCompatTextView2.perform(click());

		ViewInteraction appCompatEditText = onView(
				allOf(withId(R.id.edit_login_username),
						childAtPosition(
								childAtPosition(
										withId(R.id.custom),
										0),
								0),
						isDisplayed()));
		appCompatEditText.perform(replaceText("adam@adam.pl"), closeSoftKeyboard());

		ViewInteraction appCompatEditText2 = onView(
				allOf(withId(R.id.edit_login_password),
						childAtPosition(
								childAtPosition(
										withId(R.id.custom),
										0),
								1),
						isDisplayed()));
		appCompatEditText2.perform(replaceText("qwe123"), closeSoftKeyboard());

		ViewInteraction appCompatButton = onView(
				allOf(withId(android.R.id.button1), withText("Sign in"),
						childAtPosition(
								childAtPosition(
										withId(R.id.buttonPanel),
										0),
								3)));
		appCompatButton.perform(scrollTo(), click());

		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction appCompatTextView3 = onView(
				allOf(withId(R.id.button_observe), withText("Follow"),
						childAtPosition(
								childAtPosition(
										withClassName(is("android.widget.LinearLayout")),
										1),
								5),
						isDisplayed()));
		appCompatTextView3.perform(click());

	}

	private static Matcher<View> childAtPosition(
			final Matcher<View> parentMatcher, final int position) {

		return new TypeSafeMatcher<View>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("Child at position " + position + " in parent ");
				parentMatcher.describeTo(description);
			}

			@Override
			public boolean matchesSafely(View view) {
				ViewParent parent = view.getParent();
				return parent instanceof ViewGroup && parentMatcher.matches(parent)
						&& view.equals(((ViewGroup) parent).getChildAt(position));
			}
		};
	}
}
