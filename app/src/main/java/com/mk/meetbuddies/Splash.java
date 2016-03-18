package com.mk.meetbuddies;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.mk.utils.DataBaseConnector;

public class Splash extends AppCompatActivity {
    private Typeface font;
    private Button signIn, signUp;

    public Splash() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);//Getting ActionBar Object
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        DataBaseConnector db = new DataBaseConnector(Splash.this);
        if (db.getAllUsers().getCount() > 0) {
            startActivity(new Intent(Splash.this, MainActivity.class));
        } else {
            getSupportActionBar().hide();//Hiding ActionBar
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_splash);

            signIn = (Button) findViewById(R.id.buttonSignIn);
            signUp = (Button) findViewById(R.id.buttonSignUp);
            font = Typeface.createFromAsset(getAssets(), "lucida.ttf");
            signIn.setTypeface(font);
            signUp.setTypeface(font);
        }
    }

    public void goToLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void goToSignUp(View view) {
        startActivity(new Intent(this, SignUp.class));
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
