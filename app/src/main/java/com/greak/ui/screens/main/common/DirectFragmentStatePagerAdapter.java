package com.greak.ui.screens.main.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

public abstract class DirectFragmentStatePagerAdapter<T extends Fragment> extends FragmentStatePagerAdapter {

	private SparseArray<T> createdFragments = new SparseArray<>();

	public DirectFragmentStatePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		T instantiatedItem = (T) super.instantiateItem(container, position);
		createdFragments.put(position, instantiatedItem);
		return instantiatedItem;
	}

	@Override
	public abstract T getItem(int position);

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
		createdFragments.delete(position);
	}

	public Fragment getCreatedFragment(int position) {
		return createdFragments.get(position);
	}
}