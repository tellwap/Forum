package com.blogspot.waptell.www.forum.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.waptell.www.forum.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab3Fragment extends Fragment {

    public Tab3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_tab3, container, false);
    }

}
