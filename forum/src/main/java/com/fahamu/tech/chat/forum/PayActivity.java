package com.fahamu.tech.chat.forum;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayActivity extends AppCompatActivity {

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

        fab.setOnClickListener(view ->
                Snackbar.make(view,
                        "Selected amount : " + getIntent().getStringExtra("_amount"),
                        Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show());
    }

    private void swipeInit(){
        swipeRefreshLayout.setRefreshing(true);

    }

    private void pay(){

    }
}
