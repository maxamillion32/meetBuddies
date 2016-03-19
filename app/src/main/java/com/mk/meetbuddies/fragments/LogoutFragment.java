package com.mk.meetbuddies.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mk.meetbuddies.R;
import com.mk.utils.DataBaseConnector;
import com.mk.utils.SessionManager;

public class LogoutFragment extends Fragment {

    SessionManager sm;
    DataBaseConnector db;

    public LogoutFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logout,
                container, false);
        sm = new SessionManager(view.getContext());
        db = new DataBaseConnector(view.getContext());
        LogoutDialog dialog = LogoutDialog.newInstance(sm, db);
        dialog.show(getFragmentManager(), "LogOut");
        return view;
    }

}
