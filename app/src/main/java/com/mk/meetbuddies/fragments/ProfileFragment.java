package com.mk.meetbuddies.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mk.meetbuddies.R;
import com.mk.utils.DownloadImg;
import com.mk.utils.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    EditText lastName, firstName,address,phone;
    Button btnOk,btnCancel;
    ImageView profilePic;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        lastName=(EditText)view.findViewById(R.id.tnamePro);
        firstName=(EditText)view.findViewById(R.id.tprenamePro);
        address=(EditText)view.findViewById(R.id.tadressPro);
        phone=(EditText)view.findViewById(R.id.tphone);
        profilePic=(ImageView)view.findViewById(R.id.profile_picture);

        btnOk=(Button)view.findViewById(R.id.bokPro);
        btnCancel=(Button)view.findViewById(R.id.bcancel);
     /*   btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                updateUserInfo();
            }

        });
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                clear();
            }

        });
        profilePic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

            }

        });*/
        SessionManager session= new SessionManager(getContext());

        lastName.setText(session.getName());
        firstName.setText(session.getPrename());
        address.setText(session.getAdress());
        DownloadImg down= new DownloadImg();
        down.getImage(profilePic,session.getPhotourl());

        return view;
    }


    private void clear(){
        lastName.setText("");
        firstName.setText("");
        address.setText("");
        phone.setText("");
    }
    private void updateUserInfo(){

    }
}
