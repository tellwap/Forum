package com.fahamu.tech.chat.forum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.fahamu.tech.chat.forum.database.NoSqlDatabase;
import com.fahamu.tech.chat.forum.message.MessageUtils;
import com.fahamu.tech.chat.forum.model.Messages;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.intentservice.chatui.ChatView;

public class ChatActivity extends AppCompatActivity {

    private MessageUtils messageUtils;
    @BindView(R.id.chat_view)
    ChatView chatView;

    private NoSqlDatabase noSqlDatabase = new NoSqlDatabase();
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

        noSqlDatabase.receiveMessage(docId, chatView, senderId);

        //listen for notification
        messageUtils = new MessageUtils();
        messageUtils.subscribe(docId);
    }


    private void sendMessage() {

        chatView.setOnSentMessageListener(chatMessage -> {
            // perform actual message sending
            Log.e("TAG***SEND SMS", chatMessage.getMessage());
//            //send message using fcm
//            RemoteMessage remoteMessage=new RemoteMessage.Builder(docId + "@gcm.googleapis.com")
//                    .setMessageId(String.valueOf(new Date().getTime()))
//                    .addData("message",chatMessage.getMessage())
//                    .addData("senderId",senderId)
//                    .build();


            //messageUtils.sendFCMessage(remoteMessage);
            noSqlDatabase.sendMessage(docId,
                    new Messages(senderId, chatMessage.getMessage(),
                            String.valueOf(chatMessage.getTimestamp())));
            return true;
        });

    }

}
