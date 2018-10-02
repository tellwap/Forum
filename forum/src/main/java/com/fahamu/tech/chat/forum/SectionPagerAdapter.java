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

    SectionPagerAdapter(FragmentManager fm) {
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

    //add fragment to the page tab layout
    public void addFragment(Fragment fragment){
         mFragment.add(fragment);
    }
}
