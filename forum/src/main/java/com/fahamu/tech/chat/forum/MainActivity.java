package com.fahamu.tech.chat.forum;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fahamu.tech.chat.forum.database.NoSqlDatabase;
import com.fahamu.tech.chat.forum.fragment.MyChatFragment;
import com.fahamu.tech.chat.forum.fragment.AllChartFragment;
import com.fahamu.tech.chat.forum.model.Post;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private NoSqlDatabase noSqlDatabase;

    @Override
    protected void onStart() {
        checkIsLogin();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.forum_toolbar);
        setSupportActionBar(toolbar);

        //initiate database
        noSqlDatabase = new NoSqlDatabase();

        //render the view
        iniUI();
        //check if user is exist
       checkIsLogin();
    }

    @Override
    protected void onResume() {
        checkIsLogin();
        super.onResume();
    }

    /**
     * check if user is exist in firebase
     */
    private void checkIsLogin(){
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(this,"Please login firest",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, SignUpActivity.class));
            //finish();
        }
    }

    /**
     * render the chat view
     */
    private void iniUI() {
        mViewPager = findViewById(R.id.container_view);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        FloatingActionButton fab = findViewById(R.id.forum_new);

        tabLayout.getTabAt(0).setText("MY CHARTS");
        tabLayout.getTabAt(1).setText("ALL CHARTS");

        //fab listener
        tabAction(tabLayout, fab);
        createForum(fab, this);

    }

    /**
     * hide the float button if not in the tab of my forum
     *
     * @param tabLayout -
     * @param fab       -
     */
    private void tabAction(final TabLayout tabLayout, final FloatingActionButton fab) {
       // if (mViewPager != null) mViewPager.setCurrentItem(0);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position != 0) {
                    fab.setVisibility(View.INVISIBLE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position != 0) {
                    fab.setVisibility(View.INVISIBLE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position != 0) {
                    fab.setVisibility(View.INVISIBLE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    /**
     * create the new topic
     * @param fab the to initiate the popup
     * @param context the activity
     */
    private void createForum(FloatingActionButton fab, Context context) {

        fab.setOnClickListener(v -> {
            @SuppressLint("InflateParams") LinearLayout view = (LinearLayout) LayoutInflater
                    .from(this).inflate(R.layout.create_forum, null);

            MaterialStyledDialog show = new MaterialStyledDialog.Builder(context)
                    .setStyle(Style.HEADER_WITH_ICON)
                    .setIcon(R.drawable.ic_mode_edit_black_48dp)
                    .setCustomView(view, 20, 20, 20, 20)
                    .setScrollable(true)
                    .setPositiveText("Ok")
                    .setNegativeText("Cancel")
                    .onPositive((dialog, which) -> {
                        if (dialog.getCustomView() != null) {
                            EditText editText = dialog.getCustomView()
                                    .findViewById(R.id.new_forum_title);
                            EditText maelezoEd = dialog.getCustomView()
                                    .findViewById(R.id.new_forum_title_forum_description);
                            if (!editText.getText().toString().equals("")
                                    && !maelezoEd.getText().toString().equals("")) {

                                String Uid = FirebaseAuth.getInstance().getUid();
                                String image;
                                try {
                                    image = FirebaseAuth.getInstance().getCurrentUser()
                                            .getPhotoUrl().toString();
                                } catch (NullPointerException e) {
                                    image = "";
                                }

                                String time = String.valueOf(new Date().getTime());
                                //add the new topic to the database
                                noSqlDatabase.createPost(
                                        new Post(editText.getText().toString().trim()
                                                , maelezoEd.getText().toString().trim(),
                                                Uid,
                                                time,
                                                image)
                                );
                            } else {
                                Snackbar.make(mViewPager, "Fill all the details",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        } else {
                            Snackbar.make(mViewPager, "Error, try again",
                                    Snackbar.LENGTH_SHORT).show();
                        }
                    }).onNegative((dialog, which) -> {
                        Snackbar.make(mViewPager, "Canceled...", Snackbar.LENGTH_SHORT).show();
                    }).setCancelable(false)
                    .show();
        });
    }

    /**
     * set up the viewer page to use with tab layout
     * @param mViewPager the viewer pager
     */
    private void setupViewPager(ViewPager mViewPager) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MyChatFragment());
        adapter.addFragment(new AllChartFragment());
        mViewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_profile:
                startActivity(new Intent(this, ProfileActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
