package com.blogspot.waptell.www.forum.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.waptell.www.forum.R;
import com.blogspot.waptell.www.forum.adapter.ZanguAdapter;
import com.blogspot.waptell.www.forum.model.PostModal;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Fragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<PostModal> listItems;


    public Tab1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_tab1, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.zangu_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listItems = new ArrayList<>();

        //for test purpose
        //test recycler view
        for (int i=0; i<10; i++){

            PostModal postModal = new PostModal(
                    "post title",
                    "post description",
                    "",
                    ""
            );
            listItems.add(postModal);
        }

        adapter = new ZanguAdapter(listItems, getActivity());
        recyclerView.setAdapter(adapter);


        return view;
    }

}
