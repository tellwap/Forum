package com.blogspot.waptell.www.forum.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;

/**
 * Created by chami o n 7/11/18.
 */

public class FirestoreUtils {

    // Access a Cloud Firestore instance from your Activity
    public  FirebaseFirestore db;

    public FirestoreUtils(){

        db = FirebaseFirestore.getInstance();
    }

    public void createUser(String name,String email, String id,String photo){

        DocumentReference forum_user = db.collection("forum_user").document();

        HashMap<String,Object> data=new HashMap<>();
        data.put("name",name);
        data.put("email",email);
        data.put("id",id);
        data.put("photo",photo);

        forum_user.set(data, SetOptions.merge());
    }
}
