package com.fahamu.tech.chat.forum.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fahamu.tech.chat.forum.R;
import com.fahamu.tech.chat.forum.database.NoSqlDatabase;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyChatFragment extends Fragment {

    private NoSqlDatabase noSqlDatabase = new NoSqlDatabase();

    public MyChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.my_chart_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.my_chart_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //add listener to my chart fragment
        noSqlDatabase.myPost(FirebaseAuth.getInstance().getUid(), recyclerView,getContext());

        return view;
    }


}
