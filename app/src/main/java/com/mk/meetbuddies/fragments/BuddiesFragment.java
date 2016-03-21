package com.mk.meetbuddies.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mk.meetbuddies.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuddiesFragment extends Fragment {


    public BuddiesFragment() {
        // Required empty public constructor
        int cas;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     //   this.getParentFragment().
        View view = inflater.inflate(R.layout.fragment_buddies, container, false);
        return view;

    }

}
