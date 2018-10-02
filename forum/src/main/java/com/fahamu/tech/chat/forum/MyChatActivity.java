package com.fahamu.tech.chat.forum;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.fahamu.tech.chat.forum.adapter.MyChatViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyChatActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.chat_add_image)
    FloatingActionButton addImageFab;
    @BindView(R.id.chat_send_message)
    FloatingActionButton sendMessageFab;
    @BindView(R.id.chat_input_message)
    EditText messageInput;
    @BindView(R.id.chat_recycleview)
    RecyclerView recyclerView;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chat);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            actionBar = supportActionBar;
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setSubtitle("online");
            actionBar.setTitle("Joshua Mshana");
        }

        //fab
        fabActions();
        //testing
        fakedata();
        addTypeListener();
    }

    private void fabActions() {
        sendMessageFab.setOnClickListener(view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());

        addImageFab.setOnClickListener(view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());
    }

    private void fakedata() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyChatViewAdapter(null, this));
        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);

    }

    private void addTypeListener() {
        messageInput.setOnClickListener(v -> {
            recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.chat_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.chat_menu_topic) {
            Snackbar.make(recyclerView, "Topic will be displayed here", Snackbar.LENGTH_SHORT)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }
}
