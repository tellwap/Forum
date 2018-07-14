package com.fahamu.tech.chat.forum.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fahamu.tech.chat.forum.R;
import com.fahamu.tech.chat.forum.adapter.ZanguAdapter;
import com.fahamu.tech.chat.forum.adapter.ZoteAdapter;
import com.fahamu.tech.chat.forum.database.FirestoreUtils;
import com.fahamu.tech.chat.forum.model.PostModal;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2Fragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<PostModal> listItems;

    private FirestoreUtils firestoreUtils = new FirestoreUtils();

    public Tab2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.zote_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firestoreUtils.getAllPosts(recyclerView,getContext());

        return view;
    }


}
