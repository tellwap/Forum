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

/**
 * A simple {@link Fragment} subclass.
 */
public class AllChartFragment extends Fragment {

    private NoSqlDatabase noSqlDatabase = new NoSqlDatabase();

    public AllChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.all_chart_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.all_chart_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        noSqlDatabase.getAllPosts(recyclerView,getContext());
        return view;
    }


}
