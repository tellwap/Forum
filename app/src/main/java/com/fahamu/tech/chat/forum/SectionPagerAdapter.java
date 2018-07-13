package com.fahamu.tech.chat.forum;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chami on 7/3/18.
 */

public class SectionPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragment = new ArrayList<>();

    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    public void addFragment(Fragment fragment){
         mFragment.add(fragment);
    }
}
