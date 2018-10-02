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

import com.fahamu.tech.chat.forum.database.NoSqlDatabase;
import com.fahamu.tech.chat.forum.fragment.MyChatFragment;
import com.fahamu.tech.chat.forum.fragment.AllChartFragment;
import com.fahamu.tech.chat.forum.fragment.ProfileFragment;
import com.fahamu.tech.chat.forum.model.Post;
import com.fahamu.tech.chat.forum.model.User;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ViewPager mViewPager;
    private int RC_SIGN_IN = 221;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private NoSqlDatabase noSqlDatabase;

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, ProfileActivity.class));
            finish();
            // signIn();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.forum_toolbar);
        setSupportActionBar(toolbar);

        //initiate database
        noSqlDatabase = new NoSqlDatabase();

        iniUI();
    }

    private void iniUI() {
        mViewPager = findViewById(R.id.container_view);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        FloatingActionButton fab = findViewById(R.id.forum_new);

        tabLayout.getTabAt(0).setText("MY CHARTS");
        tabLayout.getTabAt(1).setText("ALL CHARTS");
        tabLayout.getTabAt(2).setText("PROFILE");

        tabAction(tabLayout, fab);
        createForum(fab, this);

    }

    private void initSignIn() {
        // Configure Google Sign In
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        initSignIn();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.e(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        noSqlDatabase.createUser(new User(
                                user.getDisplayName(),
                                user.getEmail(),
                                mAuth.getUid(),
                                user.getPhotoUrl().toString()
                        ));
                        //updateUI(user);
                        iniUI();

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithCredential:failure", task.getException());
                        //Snackbar.make(mViewPager, "Authentication Failed.",
                        //Snackbar.LENGTH_SHORT).show();
                        //updateUI(null);
                    }

                    // ...
                });
    }

    /**
     * hide the float button if not in the tab of my forum
     *
     * @param tabLayout -
     * @param fab       -
     */
    private void tabAction(final TabLayout tabLayout, final FloatingActionButton fab) {
        if (mViewPager != null) mViewPager.setCurrentItem(1);
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

    private void createForum(FloatingActionButton fab, Context context) {

        fab.setOnClickListener(v -> {
            @SuppressLint("InflateParams") LinearLayout view = (LinearLayout) LayoutInflater
                    .from(this).inflate(R.layout.create_forum, null);
            Log.e("TAG***VIEW", view.toString());
            MaterialStyledDialog show = new MaterialStyledDialog.Builder(context)
                    .setStyle(Style.HEADER_WITH_ICON)
                    .setIcon(R.drawable.ic_mode_edit_black_48dp)
                    .setCustomView(view, 20, 20, 20, 20)
                    .setScrollable(true)
                    .setPositiveText("TUMA")
                    .setNegativeText("GHAIRI")
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

    private void setupViewPager(ViewPager mViewPager) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MyChatFragment());
        adapter.addFragment(new AllChartFragment());
        adapter.addFragment(new ProfileFragment());
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
                Snackbar.make(item.getActionView(), "Menu clicked", Snackbar.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
