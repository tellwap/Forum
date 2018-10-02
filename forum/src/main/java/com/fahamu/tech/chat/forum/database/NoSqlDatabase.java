package com.fahamu.tech.chat.forum.database;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.fahamu.tech.chat.forum.adapter.AllChatAdapter;
import com.fahamu.tech.chat.forum.adapter.MyChatAdapter;
import com.fahamu.tech.chat.forum.model.Messages;
import com.fahamu.tech.chat.forum.model.Post;
import com.fahamu.tech.chat.forum.model.User;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.List;

public class NoSqlDatabase {

    private FirebaseFirestore firestore;
    private static String TAG = "NOSQLDATA";

    public NoSqlDatabase() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setSslEnabled(true)
                .build();
        firestore = FirebaseFirestore.getInstance();
        firestore.setFirestoreSettings(settings);
    }

    /**
     * create the user to the database
     *
     * @param user the user object
     */
    public void createUser(User user) {
        Log.e(TAG, "start create user");
        DocumentReference document = firestore.collection(ForumC.FORUM_USER.name())
                .document(user.getEmail());
        document.set(user, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.e(TAG, "successful user added"))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to add user : " + e.getMessage()));
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

    /**
     * create new topic in a chat
     *
     * @param post topic object
     */
    public void createPost(Post post) {

        DocumentReference forum_posts = firestore.collection(ForumC.FORUMS.name()).document();
        post.setDocId(forum_posts.getId());
        forum_posts.set(post, SetOptions.merge());

    }

    /**
     * listener of the user topics
     * get the list of the topic specific user write
     *
     * @param id           user id
     * @param recyclerView the container of the view
     * @param context      the activity to show the list
     */
    public void myPost(String id, RecyclerView recyclerView, Context context) {
//        Query userId = firestore.collection(ForumC.FORUMS.name())
//                .orderBy("time", Query.Direction.DESCENDING)
//                .whereEqualTo("userId",id);
//        userId.get().addOnSuccessListener(queryDocumentSnapshots -> {
//            List<Post> posts = new ArrayList<>();
//
//            if (queryDocumentSnapshots != null)
//                posts = queryDocumentSnapshots.toObjects(Post.class);
//
//
//
//
//            Log.e("TAG****POST","Loading");
//
//
//        });
        firestore.collection(ForumC.FORUMS.name())
                .whereEqualTo("userId", id)
                .orderBy("time", Query.Direction.DESCENDING)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {

                    if (queryDocumentSnapshots != null) {

                        List<Post> posts = queryDocumentSnapshots.toObjects(Post.class);
//                        for (QueryDocumentSnapshot documentChange : queryDocumentSnapshots) {
//
//
//                            String uid = documentChange.getString("userId");
//                            if (uid != null) {
//                                if (uid.equals(FirebaseAuth.getInstance().getUid()))
//                                    posts.add(documentChange.toObject(Post.class));
//                            }
//
//                        }
                        MyChatAdapter adapter = new MyChatAdapter(posts, context);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }

                    if (e != null) {
                        Log.e(TAG, "error getting the post, cause : " + e.getLocalizedMessage());
                    }
                });
//        firestore.collection(ForumC.FORUMS.name()).orderBy("time", Query.Direction.DESCENDING)
//                .whereEqualTo("userId",id)
//                .addSnapshotListener((queryDocumentSnapshots, e) -> {
//
//                 //check if the document is added
//                    if (queryDocumentSnapshots.getDocumentChanges() != null) {
//                        List<Post> posts = queryDocumentSnapshots.toObjects(Post.class);
//                        RecyclerView.Adapter adapter;
//                        adapter = new MyChatAdapter(posts, context);
//                        recyclerView.setAdapter(adapter);
//                    }
//
//        });
    }

    /**
     * delete the post of a specific user
     *
     * @param docId the id of the document to delete
     */
    public void deletePost(String docId) {
        DocumentReference document = firestore.collection(ForumC.FORUMS.name()).document(docId);
        document.delete().addOnSuccessListener(aVoid -> {
            Log.e(TAG, "delete : " + docId);
        }).addOnFailureListener(e -> {
            Log.e(TAG, "no deleted, cause : " + e);
        });
    }

    /**
     * send a message to the specific user id
     *
     * @param docId    the id of the document to send
     * @param messages the message body
     */
    public void sendMessage(String docId, Messages messages) {

        DocumentReference document = firestore.collection(ForumC.FORUMS.name()).document(docId);
        DocumentReference document1 = document.collection(ForumC.FORUM_MESSAGE.name()).document();
        document1.set(messages, SetOptions.merge());
        //todo : add listener if its successful
    }

    /**
     * listener of the received message
     *
     * @param docId    the id of the document
     * @param chatView view of the chat
     * @param senderId id of the user send the message
     */
//    public void receiveMessage(String docId, ChatView chatView, String senderId) {
//
//        firestore.collection(ForumC.FORUMS.name()).document(docId)
//                .collection(ForumC.FORUM_MESSAGE.name())
//                .orderBy("time", Query.Direction.ASCENDING)
//                .addSnapshotListener((queryDocumentSnapshots, e) -> {
//
//                    if (queryDocumentSnapshots != null) {
//
//                        for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
//
//                            if (documentChange.getType() == DocumentChange.Type.ADDED) {
//                                List<Messages> m = queryDocumentSnapshots.toObjects(Messages.class);
//                                chatView.clearMessages();
//                                for (Messages messages : m) {
//                                    ChatMessage chatMessage;
//                                    if (senderId.equals(messages.getSenderId())) {
//                                        chatMessage = new ChatMessage(messages.getMessage(),
//                                                Long.valueOf(messages.getTime()), ChatMessage.Type.SENT);
//                                    } else {
//                                        chatMessage = new ChatMessage(messages.getMessage(),
//                                                Long.valueOf(messages.getTime()), ChatMessage.Type.RECEIVED);
//                                    }
//                                    chatView.addMessage(chatMessage);
//                                }
//                            }
//                        }
//
//                    }
//                });
////        collection.addSnapshotListener((queryDocumentSnapshots, e) -> {
////
////            List<Messages> m;
////
////            if (queryDocumentSnapshots!=null){
////                m = queryDocumentSnapshots.toObjects(Messages.class);
////                for (Messages messages : m) {
////                    ChatMessage chatMessage;
////                    if (senderId.equals(messages.getSenderId())) {
////                        chatMessage = new ChatMessage(messages.getMessage(),
////                                Long.valueOf(messages.getTime()), ChatMessage.Type.SENT);
////                    } else {
////                        chatMessage = new ChatMessage(messages.getMessage(),
////                                Long.valueOf(messages.getTime()), ChatMessage.Type.RECEIVED);
////                    }
////
////                    chatView.addMessage(chatMessage);
////                }
////            }
////
////        });
//    }

    /**
     * get all the topic available in the database
     *
     * @param recyclerView the list view for the topics
     * @param context      the activity to show the view
     */
    public void getAllPosts(RecyclerView recyclerView, Context context) {
//        Query userId = firestore.collection(ForumC.FORUMS.name())
//                .orderBy("time", Query.Direction.DESCENDING);
//
//        userId.get().addOnSuccessListener(queryDocumentSnapshots -> {
//            List<Post> posts = new ArrayList<>();
//
//            if (queryDocumentSnapshots != null)
//                posts = queryDocumentSnapshots.toObjects(Post.class);
//
//
//            RecyclerView.Adapter adapter;
//            adapter = new AllChatAdapter(posts,context);
//            recyclerView.setAdapter(adapter);
//
//            Log.e("TAG****POST","Loading");
//

        //       });

//        firestore.collection(ForumC.FORUMS.name())
//                .addSnapshotListener((queryDocumentSnapshots, e) -> {
//                    if (queryDocumentSnapshots!=null){
//                        List<Post> posts = queryDocumentSnapshots.toObjects(Post.class);
//                        MyChatAdapter zanguAdapter=new MyChatAdapter(posts,context);
//                        recyclerView.setAdapter(zanguAdapter);
//
//                    }
//                });
        firestore.collection(ForumC.FORUMS.name()).orderBy("time", Query.Direction.DESCENDING)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {

                    if (queryDocumentSnapshots != null) {
                        for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {

                            if (documentChange.getType() == DocumentChange.Type.ADDED) {

                                List<Post> posts = queryDocumentSnapshots.toObjects(Post.class);

                                RecyclerView.Adapter adapter;
                                adapter = new AllChatAdapter(posts, context);
                                recyclerView.setAdapter(adapter);
                                // adapter.notifyDataSetChanged();


                            }
                        }
                    }
                });
    }

}
