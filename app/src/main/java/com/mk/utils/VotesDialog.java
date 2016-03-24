package com.mk.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.mk.meetbuddies.R;

/**
 * Created by Malek on 3/23/2016.
 */
public class VotesDialog extends Dialog {
    private MultiSpinner preferences;
    public VotesDialog(Context context) {
        super(context);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.vote_dialog);
        preferences = (MultiSpinner) findViewById(R.id.preferencesVotes);


    }
}
