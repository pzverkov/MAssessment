package com.massessment.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.massessment.network.data.Category;
import com.massessment.ui.main.ProductListFragment;

import java.util.List;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private List<Category> categories;

    public ScreenSlidePagerAdapter(FragmentManager fm, List<Category> categories) {
        super(fm);
        this.categories = categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return ProductListFragment.getInstance(categories.get(position));
    }

    public Category getCategoryAtPosition(int position) {
        return categories.get(position);
    }

    @Override
    public int getCount() {
        return categories == null ? 0 : categories.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return getCategoryAtPosition(position).getName();
    }
}
