package com.fahamu.tech.chat.forum.database;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.fahamu.tech.chat.forum.adapter.ZanguAdapter;
import com.fahamu.tech.chat.forum.adapter.ZoteAdapter;
import com.fahamu.tech.chat.forum.model.Messages;
import com.fahamu.tech.chat.forum.model.Post;
import com.fahamu.tech.chat.forum.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

public class FirestoreUtils {

    public FirebaseFirestore firestore;

    public FirestoreUtils() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        firestore = FirebaseFirestore.getInstance();
        firestore.setFirestoreSettings(settings);
    }

    public void createUser(User user) {
        Log.e("TAG**CREATE USER", "start");
        DocumentReference document = firestore.collection(ForumC.FORUM_USER.name())
                .document(user.getEmail());
        document.set(user, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.e("TAG**SUCESS", "sucessfull added"))
                .addOnFailureListener(e -> Log.e("TAG**Fail", e.getMessage()));
    }

    public void getAllUsers(String id) {
        Query query = firestore.collection(ForumC.FORUM_USER.name())
                .whereEqualTo(ForumC.USER_ID.name(), id);
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (queryDocumentSnapshots != null) {
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {

                }
            }
        });
    }

    public void createPost(Post post) {

        DocumentReference forum_posts = firestore.collection(ForumC.FORUMS.name()).document();
        post.setDocId(forum_posts.getId());
        forum_posts.set(post, SetOptions.merge());

    }

    public void myPost(String id, RecyclerView recyclerView, Context context) {
        Query userId = firestore.collection(ForumC.FORUMS.name())
                .orderBy("time", Query.Direction.DESCENDING)
                .whereEqualTo("userId",id);
        userId.get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<Post> posts = new ArrayList<>();

            if (queryDocumentSnapshots != null)
                posts = queryDocumentSnapshots.toObjects(Post.class);


            RecyclerView.Adapter adapter;
            adapter = new ZanguAdapter(posts,context);
            recyclerView.setAdapter(adapter);

            Log.e("TAG****POST","Loading");


        });

        firestore.collection(ForumC.FORUMS.name())
                .whereEqualTo("userId",id)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (queryDocumentSnapshots!=null){
                List<Post> posts = queryDocumentSnapshots.toObjects(Post.class);
                ZanguAdapter zanguAdapter=new ZanguAdapter(posts,context);
                recyclerView.setAdapter(zanguAdapter);

            }
        });
    }

    public void deletePost(String docId){
        DocumentReference document = firestore.collection(ForumC.FORUMS.name()).document(docId);
        document.delete();
    }


    public void sendMessage(String docId, Messages messages){

        DocumentReference document = firestore.collection(ForumC.FORUMS.name()).document(docId);
        DocumentReference document1 = document.collection(ForumC.FORUM_MESSAGE.name()).document();
        document1.set(messages,SetOptions.merge());
    }

    public void receiveMessgae(String docId, ChatView chatView,String senderId){
        CollectionReference collection = firestore.collection(ForumC.FORUMS.name()).document(docId)
                .collection(ForumC.FORUM_MESSAGE.name());
        collection.orderBy("time");
        collection.get().addOnSuccessListener(queryDocumentSnapshots -> {


            List<Messages> m;

            if (queryDocumentSnapshots!=null){
                m = queryDocumentSnapshots.toObjects(Messages.class);
                for (Messages messages : m) {
                    ChatMessage chatMessage;
                    if (senderId.equals(messages.getSenderId())) {
                        chatMessage = new ChatMessage(messages.getMessage(),
                                Long.valueOf(messages.getTime()), ChatMessage.Type.SENT);
                    } else {
                        chatMessage = new ChatMessage(messages.getMessage(),
                                Long.valueOf(messages.getTime()), ChatMessage.Type.RECEIVED);
                    }

                    chatView.addMessage(chatMessage);
                }
            }

        });


//        collection.addSnapshotListener((queryDocumentSnapshots, e) -> {
//
//            List<Messages> m;
//
//            if (queryDocumentSnapshots!=null){
//                m = queryDocumentSnapshots.toObjects(Messages.class);
//                for (Messages messages : m) {
//                    ChatMessage chatMessage;
//                    if (senderId.equals(messages.getSenderId())) {
//                        chatMessage = new ChatMessage(messages.getMessage(),
//                                Long.valueOf(messages.getTime()), ChatMessage.Type.SENT);
//                    } else {
//                        chatMessage = new ChatMessage(messages.getMessage(),
//                                Long.valueOf(messages.getTime()), ChatMessage.Type.RECEIVED);
//                    }
//
//                    chatView.addMessage(chatMessage);
//                }
//            }
//
//        });
    }


    public void getAllPosts(RecyclerView recyclerView, Context context){
        Query userId = firestore.collection(ForumC.FORUMS.name())
                .orderBy("time", Query.Direction.DESCENDING);

        userId.get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<Post> posts = new ArrayList<>();

            if (queryDocumentSnapshots != null)
                posts = queryDocumentSnapshots.toObjects(Post.class);


            RecyclerView.Adapter adapter;
            adapter = new ZoteAdapter(posts,context);
            recyclerView.setAdapter(adapter);

            Log.e("TAG****POST","Loading");


        });

        firestore.collection(ForumC.FORUMS.name())
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (queryDocumentSnapshots!=null){
                        List<Post> posts = queryDocumentSnapshots.toObjects(Post.class);
                        ZanguAdapter zanguAdapter=new ZanguAdapter(posts,context);
                        recyclerView.setAdapter(zanguAdapter);

                    }
                });
    }


}
