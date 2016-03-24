package com.mk.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.mk.meetbuddies.R;
import com.mk.meetbuddies.fragments.Meetings;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malek on 3/23/2016.
 */
public class VotesDialog extends Dialog implements View.OnClickListener{
    private MultiSpinner preferences;
    private castVote cast;
    private static Boolean alreadyVoted=false;
    Button vote;
    Context context;
    Meetings meeting;
    SessionManager session;
    public VotesDialog(Context context , Meetings meeting) {
        super(context);
        this.context=context;
        this.meeting=meeting;
        session = new SessionManager(context);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.vote_dialog);
        preferences = (MultiSpinner) findViewById(R.id.preferencesVotes);
        vote=(Button)this.findViewById(R.id.voteBtn);
        vote.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        System.out.println("Clicked");//Insert Vote into Data Base
        List<Integer> votes= preferences.getSelectedIndicies();
        int userId=session.getId();
        String meetignId=meeting.getId();
        for(int i=0;i<votes.size();i++){
            cast= new castVote(meetignId,votes.get(i).toString(),String.valueOf(userId));
            cast.execute();
        }
        Toast.makeText(context, "Your Vote has been submitted Successfully", Toast.LENGTH_LONG).show();
      this.dismiss();
    }
    public class castVote extends AsyncTask<String, String, String> {
        private final String meetingId,prefId, userId;
        private String msg;
        public castVote(String meetingId, String prefId,String userId){
            this.meetingId=meetingId;
            this.prefId=prefId;
            this.userId=userId;
        }
        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> parames = new ArrayList<NameValuePair>();
            parames.add(new BasicNameValuePair("meeting_id", meetingId));
            parames.add(new BasicNameValuePair("pref_id", prefId));
            parames.add(new BasicNameValuePair("user_id", userId));
            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.makeHttpRequest("http://meetbuddies.net16.net/Ws/vote.php", "GET", parames);
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
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
           super.onPreExecute();

        }
        @Override
        protected void onPostExecute(String result) {
            if (result.equals("success")) {
                alreadyVoted=true;
            }

        }

    }
}
