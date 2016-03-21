package com.mk.meetbuddies.fragments;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mk.meetbuddies.R;
import com.mk.utils.DataBaseConnector;
import com.mk.utils.JSONParser;
import com.mk.utils.MultiSpinner;
import com.mk.utils.SessionManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdditionalInfo extends Dialog implements View.OnClickListener {

    private TextView group;
    private MultiSpinner preferences;
    private CheckBox organizer;
    private String groupName;
    private List<String> pref;
    private Boolean organizerStatus;
    private UpdateTask upTask = null;
    private Button validateBtn;

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
        validateBtn = (Button) findViewById(R.id.validateBtn);
        validateBtn.setOnClickListener(this);
        groupName = group.getText().toString();
        pref = preferences.getSelectedStrings();
        organizerStatus = false;
        if (organizer.isChecked()) organizerStatus = true;

    }

    public boolean verify() {
        if (group.getText().toString().equals("")) {
            group.setError("Empty Field Group Name");
            return false;
        }

        if (preferences.getSelectedIndicies().size() > 5 && preferences.getSelectedIndicies().size() == 0) {
            group.setError("Choose 1 to 5 choices");
            return false;
        }
        return true;

    }

    public class UpdateTask extends AsyncTask<String, String, String> {

        private final String mGroup;
        private final List<String> mPref;
        private final Boolean mOrganizer;
        private int id = 0;
        private String msg;

        public UpdateTask(String group, List<String> pref, Boolean organizer) {
            mGroup = group;
            mPref = pref;
            mOrganizer = organizer;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> parames = new ArrayList<NameValuePair>();
            parames.add(new BasicNameValuePair("group", mGroup));
            parames.add(new BasicNameValuePair("pref1", mPref.get(0)));
            parames.add(new BasicNameValuePair("pref1", mPref.get(1)));
            parames.add(new BasicNameValuePair("pref1", mPref.get(2)));
            parames.add(new BasicNameValuePair("pref1", mPref.get(3)));
            parames.add(new BasicNameValuePair("pref1", mPref.get(4)));
            parames.add(new BasicNameValuePair("organnizer", mOrganizer.toString()));

            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.makeHttpRequest("http://meetbuddies.net16.net/Ws/Update.php", "GET", parames);


            Log.i("response http", json.toString());
            try {
                int success = json.getInt("success");
                if (success == 1) {
                    SessionManager session = new SessionManager(getContext());
                    DataBaseConnector db = new DataBaseConnector(getContext());
                    Cursor cursor = db.getAllUsers();
                    if (cursor.moveToFirst()) {
                        id = cursor.getInt(0);
                    }
                    session.updateUser(mGroup, mPref.get(0), mPref.get(1), mPref.get(2), mPref.get(3), mPref.get(4), mOrganizer);
                    db.addInfo(id, mGroup, mPref.get(0), mPref.get(1), mPref.get(2), mPref.get(3), mPref.get(4), mOrganizer);
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
            if (result.equals("success")) {

            }
            if (result.equals("fail")) {


            }

            super.onPostExecute(result);
        }

    }

    @Override
    public void onClick(View v) {
        if (verify()) {
            upTask = new UpdateTask(groupName, pref, organizerStatus);
            upTask.execute();
        }
    }
}
