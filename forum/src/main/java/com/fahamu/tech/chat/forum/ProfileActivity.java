package com.fahamu.tech.chat.forum;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fahamu.tech.chat.forum.config.Config;
import com.fahamu.tech.chat.forum.database.NoSqlDatabase;
import com.fahamu.tech.chat.forum.model.User;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private int RC_SIGN_IN = 221;
    private FirebaseAuth mAuth;
    private NoSqlDatabase noSqlDatabase;
    private MaterialDialog loginDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle("Profile");
        }

        //initiate dialog
        loginDialog = new MaterialDialog.Builder(this)
                .progress(true, 1)
                .content("Please wait...")
                .canceledOnTouchOutside(false)
                .build();

        noSqlDatabase = new NoSqlDatabase();
        //for testing
        contactUs();

        //start sign in
        checkUser();

    }

    private void checkUser() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            signIn();
        } else {
            updateUI();
            Log.e(TAG, "User is already sign in");
        }
    }

    private void contactUs() {
        fab.setOnClickListener(view ->
                Snackbar.make(view, "Help text", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());
    }

    private void signIn() {
        // Configure Google Sign In
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * called after sign in using google account
     *
     * @param requestCode the code requested
     * @param resultCode  the results code
     * @param data        from the intent return the result
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                //authenticate account to firebase
                //start login dialog
                showDialog();
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.e(TAG, "Google sign in failed", e);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.profile_logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * login to firebase using the google account created
     *
     * @param acct the google account
     */
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.e(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String photo;
                            if (user.getPhotoUrl() != null) {
                                photo = user.getPhotoUrl().toString();
                            } else photo = "";

                            noSqlDatabase.createUser(new User(
                                    user.getDisplayName(),
                                    user.getEmail(),
                                    mAuth.getUid(),
                                    photo
                            ));
                            //update profile
                            updateUI();
                            //hide login dialog
                            hideDialog();

                        }

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithCredential:failure", task.getException());
                        errorGetUser();
                    }
                });
    }

    private void hideDialog() {
        loginDialog.hide();
    }

    private void showDialog() {
        loginDialog.show();
    }

    private void errorGetUser() {

    }

    private void updateUI() {

    }

    private void logout() {
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut();
        FirebaseAuth.getInstance().signOut();
        //go to the main activity
        Class<? extends AppCompatActivity> mainActivity = Config.getMainActivity();
        if (mainActivity == null) {
            finish();
        } else {
            startActivity(new Intent(this, mainActivity.getClass()));
            finish();
        }
    }

}
