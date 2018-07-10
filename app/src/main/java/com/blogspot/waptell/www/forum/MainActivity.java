package com.blogspot.waptell.www.forum;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SectionPagerAdapter mSectionPagerAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.forum_toolbar);
        setSupportActionBar(toolbar);

        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container_view);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        fab=findViewById(R.id.forum_new);

        tabLayout.getTabAt(0).setText("MZangu");
        tabLayout.getTabAt(1).setText("Forum");
        tabLayout.getTabAt(2).setText("profile");

        tabAction(tabLayout,fab);

    }

    /**
     * hide
     * @param tabLayout
     * @param fab
     */
    private void tabAction(final TabLayout tabLayout, final FloatingActionButton fab){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position!=0){
                    fab.setVisibility(View.INVISIBLE);
                }else {
                    fab.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position!=0){
                    fab.setVisibility(View.INVISIBLE);
                }else {
                    fab.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position!=0){
                    fab.setVisibility(View.INVISIBLE);
                }else {
                    fab.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void setupViewPager(ViewPager mViewPager) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment());
        adapter.addFragment(new Tab2Fragment());
        adapter.addFragment(new Tab3Fragment());
        mViewPager.setAdapter(adapter);
    }
}
