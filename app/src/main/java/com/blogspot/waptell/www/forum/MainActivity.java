package com.blogspot.waptell.www.forum;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.blogspot.waptell.www.forum.database.FirestoreUtils;
import com.blogspot.waptell.www.forum.fragment.Tab1Fragment;
import com.blogspot.waptell.www.forum.fragment.Tab2Fragment;
import com.blogspot.waptell.www.forum.fragment.Tab3Fragment;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SectionPagerAdapter mSectionPagerAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private GoogleSignInOptions gso ;
    private int RC_SIGN_IN=197;
    private FirebaseAuth mAuth;
    private FirestoreUtils firestoreUtils;
    private EditText editTextTitle, editTextDisc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.forum_toolbar);
        setSupportActionBar(toolbar);

        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container_view);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        fab = findViewById(R.id.forum_new);

        tabLayout.getTabAt(0).setText("ZANGU");
        tabLayout.getTabAt(1).setText("ZOTE");
        tabLayout.getTabAt(2).setText("MIMI");

        tabAction(tabLayout, fab);
        createForum(fab, this);

        firestoreUtils=new FirestoreUtils();
        initSign();
        if (mAuth.getCurrentUser()==null){
            signIn();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        //updateUI(currentUser);
    }

    private void signIn() {
        Intent signInIntent = GoogleSignIn.getClient(this,gso).getSignInIntent();
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

    private  void initSign(){
        mAuth=FirebaseAuth.getInstance();
        // Configure Google Sign In
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
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
                        firestoreUtils.createUser(user.getDisplayName(),
                                user.getEmail(),user.getUid(),user.getPhotoUrl().toString());
                        //updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithCredential:failure", task.getException());
                        Snackbar.make(mViewPager, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
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
        if (mViewPager!=null)mViewPager.setCurrentItem(1);
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

        editTextTitle = (EditText) findViewById(R.id.new_forum_title);
        editTextDisc = (EditText) findViewById(R.id.new_forum_title_forum_description);


        fab.setOnClickListener(v -> {
            @SuppressLint("InflateParams") LinearLayout view = (LinearLayout) LayoutInflater
                    .from(this).inflate(R.layout.create_forum, null);
            Log.e("TAG***VIEW",view.toString());
            MaterialStyledDialog show = new MaterialStyledDialog.Builder(context)
                    .setStyle(Style.HEADER_WITH_ICON)
                    .setIcon(R.drawable.pen)
                    .setCustomView(view, 20, 20, 20, 20)
                    .setScrollable(true)
                    .setPositiveText("TUMA")
                    .setNegativeText("GHAIRI")
                    .onPositive((dialog, which) -> {

                    }).onNegative((dialog, which) -> {
                        Snackbar.make(mViewPager,"Umeghairisha...",Snackbar.LENGTH_SHORT).show();
                    }).setCancelable(false)
                    .show();
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
