package com.fahamu.tech.chat.forum;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fahamu.tech.chat.forum.adapter.ReceiptsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceiptsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.receipt_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.receipt_recy)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar!=null){
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle("Payment History");
        }

        //debug
        fabAction();
        //initialize ui with fake data
        initUI();
        //testing
        swipeRefreshLayout.setOnRefreshListener(this::initUI);
    }

    private void fabAction(){
        fab.setOnClickListener(view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());
    }

    private void initUI(){

        //fake data
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ReceiptsAdapter(this,null));
    }

}
