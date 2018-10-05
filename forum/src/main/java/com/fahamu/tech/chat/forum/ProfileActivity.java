package com.fahamu.tech.chat.forum;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fahamu.tech.chat.forum.config.Config;
import com.fahamu.tech.chat.forum.database.NoSqlDatabase;
import com.fahamu.tech.chat.forum.model.Receipt;
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

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.profile_phone)
    TextInputEditText phoneEdotText;
    @BindView(R.id.profile_address)
    TextInputEditText addressTextEdit;
    @BindView(R.id.profile_update_profile)
    Button updateProfileButton;
    @BindView(R.id.profile_amount)
    TextView amountTextView;
    @BindView(R.id.profile_subscription)
    TextInputEditText subscriptionTextEdit;
    @BindView(R.id.profile_status)
    TextInputEditText statusTextEdit;
    @BindView(R.id.profile_payment)
    Button payButton;
    @BindView(R.id.profile_payment_history)
    Button receiptButton;


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

        //noSqlDatabase = new NoSqlDatabase();

        //for testing
        contactUs();

        //listener
        buttons();

    }

    private void buttons() {
        receiptButton.setOnClickListener(v -> {
            startActivity(new Intent(this, ReceiptsActivity.class));
        });

        payButton.setOnClickListener(v -> {
            payDialog();
        });

        updateProfileButton.setOnClickListener(v -> {
            Snackbar.make(v, "Update profile", Snackbar.LENGTH_SHORT).show();
        });
    }

    private void payDialog() {
        new MaterialDialog.Builder(this)
                .title("Choose Package")
                .customView(R.layout.checkout, true)
                .positiveText("Pay")
                .negativeText("Cancel")
                .autoDismiss(false)
                .canceledOnTouchOutside(false)
                .onNegative((dialog, which) -> {
                    Snackbar.make(payButton,
                            "Payment Canceled", Snackbar.LENGTH_SHORT).show();
                    dialog.dismiss();
                })
                .onPositive((dialog, which) -> {
                    View customView = dialog.getCustomView();
                    if (customView != null) {
                        String amount;
                        Intent intent = new Intent(this, PayActivity.class);
                        RadioButton month = customView.findViewById(R.id.pay_monthly);
                        RadioButton sixMonth = customView.findViewById(R.id.pay_six_month);
                        RadioButton year = customView.findViewById(R.id.pay_twelve_month);

                        if (month.isChecked()) {
                            amount = "10000";
                            intent.putExtra("_amount", amount);
                            dialog.dismiss();
                            startActivity(intent);
                            //Snackbar.make(customView, "Month", Snackbar.LENGTH_SHORT).show();
                        } else if (sixMonth.isChecked()) {
                            amount = "50000";
                            intent.putExtra("_amount", amount);
                            dialog.dismiss();
                            startActivity(intent);
                            //Snackbar.make(customView, "6 month", Snackbar.LENGTH_SHORT).show();
                        } else if (year.isChecked()) {
                            amount = "100000";
                            intent.putExtra("_amount", amount);
                            dialog.dismiss();
                            startActivity(intent);
                            //Snackbar.make(customView, "year", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(customView, "Choose package first", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
    }


    private void contactUs() {
        fab.setOnClickListener(view ->
                Snackbar.make(view, "Help text", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());
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


    private void logout() {
        // FirebaseAuth mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut();
        FirebaseAuth.getInstance().signOut();
        //go to the main activity
        startActivity(new Intent(this, SignUpActivity.class));
        finish();
    }

}
