package com.mk.meetbuddies.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mk.meetbuddies.MainActivity;
import com.mk.meetbuddies.R;
import com.mk.utils.DatePickerFragment;
import com.mk.utils.JSONParser;
import com.mk.utils.SessionManager;
import com.mk.utils.TimePickerFragment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Salim on 23/03/16.
 */
public class VerifyGroupActivity extends AppCompatActivity {

    TextView group, meetingName, meetingDesc;
    Button createGroup, createMeeting, setTime, setDate;
    String time, date;
    CreateMeetingTask createMeetingTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_group);
        group = (TextView) findViewById(R.id.group);
        createGroup = (Button) findViewById(R.id.button_create_group);
        meetingName = (TextView) findViewById(R.id.tmeeting);
        createMeeting = (Button) findViewById(R.id.button_create_meeting);
        meetingDesc = (TextView) findViewById(R.id.tDescription);
        setTime = (Button) findViewById(R.id.tTime);
        setDate = (Button) findViewById(R.id.tDate);
        SessionManager sm = new SessionManager(this);
        final String groupName = sm.getGroup();
        if (groupName.equals("no_group")) {
            group.setText("You don't have a group");
            meetingName.setVisibility(View.GONE);
            createMeeting.setVisibility(View.GONE);
            meetingDesc.setVisibility(View.GONE);
            setTime.setVisibility(View.GONE);
            setDate.setVisibility(View.GONE);
        } else {
            group.setText(groupName);
            createGroup.setVisibility(View.GONE);
        }
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdditionalInfo fd = new AdditionalInfo(v.getContext());
                fd.show();
                fd.setCanceledOnTouchOutside(false);
            }
        });
        createMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verify()) {
                    final String nomMeeting = meetingName.getText().toString();
                    final String descMeeting = meetingDesc.getText().toString();

                    createMeetingTask = new CreateMeetingTask(groupName, nomMeeting, descMeeting);
                    createMeetingTask.execute();

                }
            }
        });
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public boolean verify() {
        if (meetingName.getText().toString().equals("")) {
            meetingName.setError("Empty Field Meeting Name");
            return false;
        }

        if (meetingDesc.getText().toString().equals("")) {
            meetingDesc.setError("Empty Field Meeting Description");
            return false;
        }

        return true;
    }

    public class CreateMeetingTask extends AsyncTask<String, String, String> {

        String groupName, nomMeeting, descMeeting, date, time;

        public CreateMeetingTask(String groupName, String nomMeeting, String descMeeting) {
            this.groupName = groupName;
            this.nomMeeting = nomMeeting;
            this.descMeeting = descMeeting;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> parames = new ArrayList<NameValuePair>();
            parames.add(new BasicNameValuePair("group", groupName));
            parames.add(new BasicNameValuePair("nomMeeting", nomMeeting));
            parames.add(new BasicNameValuePair("desMeeting", descMeeting));
            SessionManager sm = new SessionManager(getBaseContext());
            date = sm.getDate();
            time = sm.getTime();
            parames.add(new BasicNameValuePair("date", date));
            parames.add(new BasicNameValuePair("time", time));

            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.makeHttpRequest("http://meetbuddies.net16.net/Ws/CreateMeeting.php", "GET", parames);
            Log.i("response http", json.toString());

            try {
                int success = json.getInt("success");
                if (success == 1) {
                    return "success";

                } else {
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
             //   Intent t= new Intent(VerifyGroupActivity.class, MainActivity.class);
             /*   FragmentManager fragmentManager = getSupportFragmentManager();
               Fragment fragment = new ProfileFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.mainFragment, fragment)
                                //.set
                        .commit();*/
            }
            if (result.equals("fail")) {
            }

            super.onPostExecute(result);
        }
    }
}
