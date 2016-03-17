package com.mk.meetbuddies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
    @Override
    public void onBackPressed() {
        finish();
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


}
