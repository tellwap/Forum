package com.fahamu.tech.chat.forum;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayActivity extends AppCompatActivity {
    /**
     * parameters
     * 1.email
     * 2.number
     * 3.dsc
     * 4.amount
     * 5.callback
     */
    private static String PAY = "https://us-central1-money-fast-firebase.cloudfunctions.net/send_money?";

    private FirebaseAuth firebaseAuth;

    @BindView(R.id.pay_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.pay_webview)
    WebView webView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle("Pay");
        }

        //auth
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, SignUpActivity.class));
            finish();
        } else {
            //swipe layout
            initUI();

            fab.setOnClickListener(view ->
                    Snackbar.make(view,
                            "Selected amount : " + getIntent().getStringExtra("_amount"),
                            Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show());
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initUI() {
        String params = "email=" + firebaseAuth.getCurrentUser().getEmail() +
                "&dsc=Payment For Kemifra App&amount=1000&callback=doctor-fahamutech.firebaseapp.com";
        webView.loadUrl(PAY + params);
        webView.setWebViewClient(new MyWebClient());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            webView.loadUrl(PAY + params);
        });
    }

    private class MyWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            swipeRefreshLayout.setRefreshing(false);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            Snackbar.make(view, "Error loading page", Snackbar.LENGTH_LONG).show();
        }
    }
}
