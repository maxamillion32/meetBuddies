package com.mk.meetbuddies;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mk.utils.ImageUtils;
import com.mk.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SignUp extends AppCompatActivity{

    private SubscribeTask subTask = null;
    private Button bOk, bCancel;
    private TextView name, prename, login, photoUrl, adress, title,confirmPass, password;
    private Typeface font;
    private ImageView uploadPic;
    private UploadDialog dialog;
    private UploadDialog.customOnClickListener dialogListener;
    private int PICK_IMAGE_REQUEST = 1;
    private static Uri filePath;
    private static Bitmap bitmap=null;
    private ImageUtils img;
    private ImageView userPic;
    private String UPLOAD_URL ="http://meetbuddies.net16.net/images/users/upload.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        font = Typeface.createFromAsset(getAssets(), "lucida.ttf");
        name = (TextView) findViewById(R.id.tname);
        title= (TextView) findViewById(R.id.sign_up_label);
        prename = (TextView) findViewById(R.id.tprename);
        login = (TextView) findViewById(R.id.tlogin);
        password = (TextView) findViewById(R.id.tpassword);
        confirmPass=(TextView) findViewById(R.id.tConfirmPassword);
        uploadPic = (ImageView) findViewById(R.id.upload_picture);
        adress = (TextView) findViewById(R.id.tadress);
        userPic= (ImageView)findViewById(R.id.upload_picture);
        this.setDialogButtonsClickBehavior();//Define Dialog buttons onCLickListener
        dialog = new UploadDialog(this,dialogListener);
        title.setTypeface(font);
        bOk = (Button) findViewById(R.id.bok);
        bOk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (verify()) {
                    final String nom = name.getText().toString();
                    final String prenom = prename.getText().toString();
                    final String log = login.getText().toString();
                    final String pass = password.getText().toString();
                    final String photo ;
                    final String adr = adress.getText().toString();
                        if(filePath!=null){
                            photo = "img_user_"+log;
                       }
                    else{
                       photo =null;
                    }
                    subTask = new SubscribeTask(nom, prenom, log, pass, photo, adr);
                    subTask.execute();

                }
            }

        });
        uploadPic.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
               getImageUrl();
            }

        });

        bCancel = (Button) findViewById(R.id.bcancel);
        bCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }

        });

    }
/*
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        // TODO Auto-generated method stub
        if (android.R.id.home == item.getItemId()) {
            Intent idash = new Intent(SignUp.this, LoginActivity.class);
            startActivity(idash);

        }

        return super.onMenuItemSelected(featureId, item);
    }
*/
    public boolean verify() {
        if (name.getText().toString().equals("")) {
            name.setError("Empty Field Last Name");
            return false;
        }

        if (prename.getText().toString().equals("")) {
            prename.setError("Empty Field First Name");
            return false;
        }

        if (login.getText().toString().equals("")) {
            login.setError("Empty Field E-mail");
            return false;
        }
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(login.getText()).matches()==false){
            login.setError("Invalid E-mail Format");
            return false;
        }
        if (password.getText().toString().equals("")) {
            password.setError("Empty Field password");
            return false;
        }

         if (adress.getText().toString().equals("")) {
             adress.setError("Empty Field address");
            return false;
        }
        if(password.getText().toString().equals(confirmPass.getText().toString())==false){
            System.out.println(password.getText());
            System.out.println(confirmPass.getText());
            confirmPass.setError("Password do not match confirmation");
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
            mPhotoUrl =photo ;
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
            parames.add(new BasicNameValuePair("photo", "http://meetbuddies.net16.net/images/users/"+mPhotoUrl+".png"));
            parames.add(new BasicNameValuePair("adress", mAdress));

            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.makeHttpRequest("http://meetbuddies.net16.net/Ws/Subscribe.php", "GET", parames);


            Log.i("response http", json.toString());
            if(filePath!= null){

                Thread t= new Thread(new Runnable() {
                    @Override
                    public void run() {

                        img= new ImageUtils(UPLOAD_URL,bitmap, mPhotoUrl,SignUp.this);
                        img.uploadImage();
                    }


                });
                t.start();


            }

            try {
                synchronized (this) {
                    wait(8000);
                    int success = json.getInt("success");

                    if (success == 1) {
                        msg = json.getString("message");

                        return "success";

                    } else {
                        msg = json.getString("message");
                        return "fail";

                    }

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (JSONException e) {
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
                new Handler().postDelayed(new Runnable() {//Starting the LOgin activity after a delay to allow the profile picture to be uploaded
                    @Override
                    public void run() {
                        final Intent mainIntent = new Intent(SignUp.this, LoginActivity.class);
                        startActivity(mainIntent);
                    }
                }, 8000);
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

    public void getImageUrl(){
     dialog.show();
    }

    @Override
    public void onBackPressed() {
        finish();
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
    private void setDialogButtonsClickBehavior(){
        dialogListener = new UploadDialog.customOnClickListener() {
            @Override
            public void onChooseButtonClick() {
                showFileChooser();
                dialog.dismiss();
            }

            @Override
            public void onUploadButtonClick() {
              uploadImage();
                dialog.dismiss();
            }
        };

    }

    /**
     *display native file chooser to select an image
     */
    private void showFileChooser() {//Displaying native
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    /**
     *Take a new photo using device's Camera
     */
    private void uploadImage(){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            //Toast.makeText(SignUp.this,filePath.toString(), Toast.LENGTH_SHORT).show();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                userPic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {//Take image using camera
            filePath = data.getData();
            //Toast.makeText(SignUp.this,filePath.toString(), Toast.LENGTH_SHORT).show();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                userPic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
