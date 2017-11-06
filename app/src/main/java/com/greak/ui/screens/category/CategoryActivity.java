package com.greak.ui.screens.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.greak.R;
import com.greak.data.models.Category;
import com.greak.ui.base.BaseActivity;
import com.greak.ui.common.ToolbarInitializer;

import butterknife.ButterKnife;

public class CategoryActivity extends BaseActivity {

	private static final String EXTRA_CATEGORY = "category";

	private Category category;

	public static void startActivity(Context context, Category category) {
		Intent intent = new Intent(context, CategoryActivity.class);
		intent.putExtra(EXTRA_CATEGORY, category);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_fragment_container_with_toolbar);
		ButterKnife.bind(this);

		initArguments(savedInstanceState);
		initPostFragment();

		ToolbarInitializer.initToolbarWithTitle(this, category.getName(), true);
	}

	private void initArguments(Bundle savedInstanceState) {
		Bundle extras = savedInstanceState == null ? getIntent().getExtras() : savedInstanceState;
		category = extras.getParcelable(EXTRA_CATEGORY);
	}

	private void initPostFragment() {
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.container_fragment, CategoryFragment.newInstance(category))
				.commit();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(EXTRA_CATEGORY, category);
		super.onSaveInstanceState(outState);
	}
}