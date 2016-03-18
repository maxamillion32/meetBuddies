package com.mk.meetbuddies;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.mk.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SignUp extends Activity {

    private SubscribeTask subTask = null;
    private Button bOk;
    private TextView name, prename, login, password, photoUrl, adress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = (TextView) findViewById(R.id.tname);
        prename = (TextView) findViewById(R.id.tprename);
        login = (TextView) findViewById(R.id.tlogin);
        password = (TextView) findViewById(R.id.tpassword);
        photoUrl = (TextView) findViewById(R.id.tphotourl);
        adress = (TextView) findViewById(R.id.tadress);

        final String nom = name.getText().toString();
        final String prenom = prename.getText().toString();
        final String log = login.getText().toString();
        final String pass = password.getText().toString();
        final String photo = photoUrl.getText().toString();
        final String adr = adress.getText().toString();

        bOk = (Button) findViewById(R.id.bok);
        bOk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (verify()) {
                    subTask = new SubscribeTask(nom, prenom, log, pass, photo, adr);
                    subTask.execute();
                }
            }

        });
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        // TODO Auto-generated method stub
        if (android.R.id.home == item.getItemId()) {
            Intent idash = new Intent(SignUp.this, LoginActivity.class);
            startActivity(idash);

        }

        return super.onMenuItemSelected(featureId, item);
    }

    public boolean verify() {
        if (name.getText().toString().equals("")) {
            name.setError("Empty Field name");
            return false;
        }

        if (prename.getText().toString().equals("")) {
            prename.setError("Empty Field prename");
            return false;
        }

        if (login.getText().toString().equals("")) {
            login.setError("Empty Field login");
            return false;
        }
        if (password.getText().toString().equals("")) {
            password.setError("Empty Field password");
            return false;
        }

        if (adress.getText().toString().equals("")) {
            password.setError("Empty Field adress");
            return false;
        }

        return true;

    }

    public class SubscribeTask extends AsyncTask<String, String, String> {

        private ProgressDialog pdialog;
        private String msg;
        private final String mName, mPrename, mLogin, mPass, mPhotoUrl, mAdress;

        public SubscribeTask(String nom, String prenom, String log, String pass, String photo, String adr) {
            mName = nom;
            mPrename = prenom;
            mLogin = log;
            mPass = pass;
            mPhotoUrl = photo;
            mAdress = adr;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            pdialog = new ProgressDialog(SignUp.this);
            pdialog.setMessage("Loading Please Wait ...");
            pdialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            ArrayList<NameValuePair> parames = new ArrayList<NameValuePair>();
            parames.add(new BasicNameValuePair("login", mLogin));
            parames.add(new BasicNameValuePair("password", mPass));
            parames.add(new BasicNameValuePair("name", mName));
            parames.add(new BasicNameValuePair("prename", mPrename));
            parames.add(new BasicNameValuePair("photo", mPhotoUrl));
            parames.add(new BasicNameValuePair("adres", mAdress));

            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.makeHttpRequest("http://meetbuddies.net16.net/Ws/Subscribe.php", "GET", parames);

            Log.i("response http", json.toString());

            try {
                int success = json.getInt("success");

                if (success == 1) {
                    msg = json.getString("message");
                    return "success";

                } else {
                    msg = json.getString("message");
                    return "fail";

                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            pdialog.dismiss();

            if (result.equals("success")) {

                Toast.makeText(SignUp.this, msg, Toast.LENGTH_LONG).show();
                clear();

            }
            if (result.equals("fail")) {

                Toast.makeText(SignUp.this, msg, Toast.LENGTH_LONG).show();

            }
            super.onPostExecute(result);
        }

    }

    public void clear() {
        name.setText("");
        prename.setText("");
        login.setText("");
        password.setText("");
        adress.setText("");
    }

    @Override
    public void onBackPressed() {
        finish();
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


}
