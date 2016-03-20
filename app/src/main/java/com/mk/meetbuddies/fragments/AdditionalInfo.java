package com.mk.meetbuddies.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mk.meetbuddies.R;
import com.mk.utils.MultiSpinner;

import java.util.List;

public class AdditionalInfo extends DialogFragment {

    TextView group;
    MultiSpinner preferences;
    CheckBox organizer;
    Button save;

    public AdditionalInfo() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setTitle("Required Additional Informations");

        builder.setView(inflater.inflate(R.layout.activity_additional_info, null))
                .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog dia = (Dialog) dialog;
                        group = (TextView) dia.findViewById(R.id.group_name);
                        preferences = (MultiSpinner) dia.findViewById(R.id.preferences);
                        organizer = (CheckBox) dia.findViewById(R.id.organizer);
                        String groupName = group.getText().toString();
                        List<String> pref = preferences.getSelectedStrings();
                        Boolean organizerStatus = false;
                        if (organizer.isChecked()) organizerStatus = true;
                    }
                });

        return builder.create();
    }


}
