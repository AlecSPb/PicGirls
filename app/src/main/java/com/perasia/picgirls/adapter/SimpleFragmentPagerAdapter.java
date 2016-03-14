package com.perasia.picgirls.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.perasia.picgirls.view.PageFragment;

public class SimpleFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = SimpleFragmentPagerAdapter.class.getSimpleName();

    private String[] tags;

    public SimpleFragmentPagerAdapter(FragmentManager fm, String[] tags) {
        super(fm);
        this.tags = tags;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return null == tags ? 0 : tags.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (tags != null) {
            return tags[position];
        }

        return "";
    }
}
