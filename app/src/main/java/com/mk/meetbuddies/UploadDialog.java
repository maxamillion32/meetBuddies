package com.mk.meetbuddies;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by Malek on 3/18/2016.
 */
public class UploadDialog extends Dialog implements
        android.view.View.OnClickListener {
    private Activity parentActivity;
    private Dialog d;
    private Button takePicture, selectExisting;

    UploadDialog(Activity a){
        super(a);
        this.parentActivity=a;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.upload_dialog);
        takePicture = (Button) findViewById(R.id.btn_new);
        selectExisting = (Button) findViewById(R.id.btn_select);
        takePicture.setOnClickListener(this);
        selectExisting.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_new:
                parentActivity.finish();
                break;
            case R.id.btn_select:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}

