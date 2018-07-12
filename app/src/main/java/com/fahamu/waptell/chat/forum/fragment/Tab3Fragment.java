package com.fahamu.waptell.chat.forum.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.fahamu.waptell.chat.forum.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        EditText name=(EditText)view.findViewById(R.id.profile_username);
        EditText email=view.findViewById(R.id.profile_e);
        CircleImageView image=view.findViewById(R.id.profile_image);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (getContext()!=null) {
            assert currentUser != null;
            Glide.with(getContext()).load(currentUser.getPhotoUrl())
                    .into(image);
        }

        assert currentUser != null;
        name.setText(currentUser.getDisplayName());
        email.setText(currentUser.getEmail());

    }
}
