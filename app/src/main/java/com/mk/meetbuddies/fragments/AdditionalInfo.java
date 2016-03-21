package com.mk.meetbuddies.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mk.meetbuddies.R;
import com.mk.utils.MultiSpinner;

import java.util.List;

public class AdditionalInfo extends Dialog  implements View.OnClickListener {

    TextView group;
    MultiSpinner preferences;
    CheckBox organizer;

    Button validateBtn;

    public AdditionalInfo(Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_additional_info);

        group = (TextView) findViewById(R.id.group_name);
        preferences = (MultiSpinner) findViewById(R.id.preferences);
        organizer = (CheckBox) findViewById(R.id.organizer);
         preferences.setSelection(0);
        validateBtn= (Button)findViewById(R.id.validateBtn);
        validateBtn.setOnClickListener(this);
        String groupName = group.getText().toString();
        List<String> pref = preferences.getSelectedStrings();
        Boolean organizerStatus = false;
        if (organizer.isChecked()) organizerStatus = true;

    }


    @Override
    public void onClick(View v) {

    }
}
