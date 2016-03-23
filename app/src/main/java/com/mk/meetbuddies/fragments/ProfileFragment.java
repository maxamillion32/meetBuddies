package com.mk.meetbuddies.fragments;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mk.meetbuddies.R;
import com.mk.utils.DataBaseConnector;
import com.mk.utils.DownloadImg;
import com.mk.utils.JSONParser;
import com.mk.utils.SessionManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    EditText lastName, firstName, address, phone;
    Button btnOk, btnCancel;
    ImageView profilePic;
    private UpdateUserTask mUpdateUser = null;
    private int id = 0;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        lastName = (EditText) view.findViewById(R.id.tnamePro);
        firstName = (EditText) view.findViewById(R.id.tprenamePro);
        address = (EditText) view.findViewById(R.id.tadressPro);
        phone = (EditText) view.findViewById(R.id.tphone);
        profilePic = (ImageView) view.findViewById(R.id.profile_picture);

        btnOk = (Button) view.findViewById(R.id.bokPro);
        btnCancel = (Button) view.findViewById(R.id.bcancel);

        /*profilePic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

            }

        });*/
        SessionManager session = new SessionManager(getContext());
        id = session.getId();
        lastName.setText(session.getName());
        firstName.setText(session.getPrename());
        address.setText(session.getAdress());
        DownloadImg down = new DownloadImg();
        down.getImage(profilePic, session.getPhotourl());

        return view;
    }


    private void clear() {
        lastName.setText("");
        firstName.setText("");
        address.setText("");
        phone.setText("");
    }

    private void updateUserInfo() {
        View focusView = null;
        Boolean cancel = false;

        if (lastName.getText().toString().equals("")) {
            lastName.setError("This field is required");
            focusView = lastName;
            cancel = true;
        }

        if (firstName.getText().toString().equals("")) {
            firstName.setError("This field is required");
            focusView = firstName;
            cancel = true;
        }

        if (address.getText().toString().equals("")) {
            address.setError("This field is required");
            focusView = address;
            cancel = true;
        }

        if (phone.getText().toString().equals("")) {
            phone.setError("This field is required");
            focusView = phone;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            mUpdateUser = new UpdateUserTask(id, firstName.getText().toString(), lastName.getText().toString(), address.getText().toString(), phone.getText().toString());
            mUpdateUser.execute();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bokPro:
                updateUserInfo();
                break;
            case R.id.bcancelPro:
                clear();
                break;
            default:
                break;
        }
    }

    public class UpdateUserTask extends AsyncTask<String, String, String> {

        private ProgressDialog pdialog;
        private int id = 0;
        private String name, prename, address, phone, photoUrl;
        private String msg;

        public UpdateUserTask(int id, String name, String prename, String address, String phone) {
            this.id = id;
            this.name = name;
            this.prename = prename;
            this.address = address;
            this.phone = phone;
            // this.photoUrl = photoUrl;
        }

        @Override
        protected void onPreExecute() {
            pdialog = new ProgressDialog(getActivity());
            pdialog.setMessage("Loading... Please Wait");
            pdialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("id_usr", Integer.toString(id)));
            param.add(new BasicNameValuePair("name", name));
            param.add(new BasicNameValuePair("prename", prename));
            param.add(new BasicNameValuePair("adress", address));
            param.add(new BasicNameValuePair("phone", phone));
            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.makeHttpRequest("http://meetbuddies.net16.net/Ws/UpdateUser.php", "GET", param);
            Log.i("response http", json.toString());

            try {
                int success = json.getInt("success");
                if (success == 1) {
                    JSONArray users = json.getJSONArray("User");
                    JSONObject user = users.getJSONObject(0);
                    SessionManager session = new SessionManager(getContext());
                    session.modifyUser(name, prename, photoUrl, address, phone);
                    DataBaseConnector db = new DataBaseConnector(getContext());
                    db.modifyUser(id, name, prename, photoUrl, address, phone);
                    return "success";

                } else {
                    msg = json.getString("message");
                    return "fail";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String success) {
            //mAuthTask = null;

            pdialog.dismiss();
            if (success.equals("success")) {
                Fragment newFragment = new HomeFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_home, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                //Intent idash = new Intent(LoginActivity.this, MainActivity.class);
                //startActivity(idash);

            }
            if (success.equals("fail")) {
            }
            super.onPostExecute(success);
        }
    }
}
