package com.blogspot.waptell.www.forum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.chat_view)
     ChatView chatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        Toolbar toolbar=findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null)getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sendMessage();
    }


    private void sendMessage(){
        chatView.setOnSentMessageListener(chatMessage -> {
            // perform actual message sending
            Log.e("TAG***SEND SMS",chatMessage.getMessage());
            return true;
        });

    }


    private void receiveMesage(ChatMessage chatMessage){
        chatView.addMessage(chatMessage);
    }
}
