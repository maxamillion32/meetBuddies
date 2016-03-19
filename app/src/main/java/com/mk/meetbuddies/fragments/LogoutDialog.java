package com.mk.meetbuddies.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.mk.meetbuddies.Splash;
import com.mk.utils.DataBaseConnector;
import com.mk.utils.SessionManager;

public class LogoutDialog extends DialogFragment {

    Context mContext;
    static SessionManager sessionManager;
    static DataBaseConnector dataBaseConnector;

    public LogoutDialog() {
        mContext = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to Logout ?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sessionManager.logoutUser();
                        dataBaseConnector.deleteAll();
                        Intent intent = new Intent(getActivity(), Splash.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public static LogoutDialog newInstance(SessionManager sm, DataBaseConnector db) {
        sessionManager = sm;
        dataBaseConnector = db;
        LogoutDialog f = new LogoutDialog();
        return f;
    }
}
