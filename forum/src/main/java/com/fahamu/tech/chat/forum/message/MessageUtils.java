package com.fahamu.tech.chat.forum.message;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

public class MessageUtils {

    private static String TAG = "FCM SUBSCRIBE";
    private FirebaseMessaging firebaseMessaging;

    public MessageUtils() {
        this.firebaseMessaging = FirebaseMessaging.getInstance();
    }

    /**
     * subscribe the client to the specific document
     *
     * @param docId the id of the chat to listen
     */
    public void subscribe(String docId) {
        firebaseMessaging.subscribeToTopic(docId)
                .addOnCompleteListener(task -> {
                    String msg = "IMEFANIKIWA++++++";
                    if (!task.isSuccessful()) {
                        msg = "IMESHINDWA++++++";
                    }
                    Log.e(TAG, msg);
                });

    }

    public void sendFCMessage(RemoteMessage remoteMessage) {
        firebaseMessaging.send(remoteMessage);
    }

}
