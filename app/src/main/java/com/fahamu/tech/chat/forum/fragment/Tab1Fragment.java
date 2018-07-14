package com.fahamu.tech.chat.forum.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fahamu.tech.chat.forum.R;
import com.fahamu.tech.chat.forum.adapter.ZanguAdapter;
import com.fahamu.tech.chat.forum.database.FirestoreUtils;
import com.fahamu.tech.chat.forum.model.PostModal;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Fragment extends Fragment {

    private FirestoreUtils firestoreUtils = new FirestoreUtils();

    public Tab1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_tab1, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.zangu_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firestoreUtils.myPost(FirebaseAuth.getInstance().getUid(), recyclerView,getContext());
        return view;
    }


}
