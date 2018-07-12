package com.fahamu.waptell.chat.forum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.fahamu.waptell.chat.forum.database.FirestoreUtils;
import com.fahamu.waptell.chat.forum.model.Messages;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.chat_view)
    ChatView chatView;

    private FirestoreUtils firestoreUtils = new FirestoreUtils();
    private String docId;
    private String senderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            Intent i = getIntent();
            getSupportActionBar().setTitle(i.getStringExtra("title"));
            getSupportActionBar().setSubtitle(i.getStringExtra("description"));

            docId = i.getStringExtra("docId");

        }

        senderId = FirebaseAuth.getInstance().getUid();

        sendMessage();

        firestoreUtils.receiveMessgae(docId,chatView,senderId);

    }


    private void sendMessage() {
        chatView.setOnSentMessageListener(chatMessage -> {
            // perform actual message sending
            Log.e("TAG***SEND SMS", chatMessage.getMessage());

            firestoreUtils.sendMessage(docId,
                    new Messages(senderId, chatMessage.getMessage(),
                            String.valueOf(chatMessage.getTimestamp())));
            return true;
        });

    }

}
