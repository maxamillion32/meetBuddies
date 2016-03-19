package com.mk.meetbuddies;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by Malek on 3/18/2016.
 */
public class UploadDialog extends Dialog  {
    private Activity parentActivity;
    private Dialog d;
    private Uri filePath;
    private Bitmap bitmap;
    private Button buttonChoose, buttonUpload;
    private customOnClickListener myListener;
    UploadDialog(Context context, customOnClickListener customClick){
        super(context);
        this.myListener = customClick;
    }
    public interface customOnClickListener {//Interface to define the Dialog button onClick functions
        void onChooseButtonClick();
        void onUploadButtonClick();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.upload_dialog);
        buttonChoose = (Button) findViewById(R.id.btn_new);
        buttonUpload = (Button) findViewById(R.id.btn_select);
        buttonUpload.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                myListener.onUploadButtonClick();
            }
        });
        buttonChoose.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                myListener.onChooseButtonClick();
            }
        });
    }
 }

