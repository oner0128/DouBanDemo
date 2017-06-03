package com.android.oner0128.doubandemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rrr on 2017/6/3.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment>fragments;
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments=new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    public void addFragment(Fragment fragment){
        fragments.add(fragment);
    }
}
