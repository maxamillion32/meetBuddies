package com.mk.meetbuddies.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.mk.meetbuddies.R;
import com.mk.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Salim on 23/03/16.
 */
public class VoteResult extends Dialog {

    private String idMeeting;
    TextView pref1, pref2, pref3, pref4, pref5;
    TextView voteP1, voteP2, voteP3, voteP4, voteP5;
    VoteResultTask voteResultTask = null;

    public VoteResult(Context context, String idMeeting) {
        super(context);
        this.idMeeting = idMeeting;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_vote_result);
        pref1 = (TextView) findViewById(R.id.pref1);
        pref2 = (TextView) findViewById(R.id.pref2);
        pref3 = (TextView) findViewById(R.id.pref3);
        pref4 = (TextView) findViewById(R.id.pref4);
        pref5 = (TextView) findViewById(R.id.pref5);
        voteP1 = (TextView) findViewById(R.id.vote1);
        voteP2 = (TextView) findViewById(R.id.vote2);
        voteP3 = (TextView) findViewById(R.id.vote3);
        voteP4 = (TextView) findViewById(R.id.vote4);
        voteP5 = (TextView) findViewById(R.id.vote5);
        voteResultTask = new VoteResultTask(idMeeting);
        voteResultTask.execute();
        String[] pr = voteResultTask.getPrefrences();
        pref1.setText(pr[0]+":");
        pref2.setText(pr[1]+":");
        pref3.setText(pr[2]+":");
        pref4.setText(pr[3]+":");
        pref5.setText(pr[4]+":");
        String[] vo = voteResultTask.getVotes();
        voteP1.setText(vo[0]+" vote(s)");
        voteP2.setText(vo[1]+" vote(s)");
        voteP3.setText(vo[2]+" vote(s)");
        voteP4.setText(vo[3]+" vote(s)");
        voteP5.setText(vo[4]+" vote(s)");
    }

    public class VoteResultTask extends AsyncTask<String, String, String> {

        private String id;
        String[] prefrences, votes;

        public VoteResultTask(String id) {
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("id_usr", id));

            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.makeHttpRequest("http://meetbuddies.net16.net/Ws/VoteResults.php", "GET", param);
            Log.i("response http", json.toString());

            try {
                int success = json.getInt("success");
                if (success == 1) {
                    JSONArray prefs = json.getJSONArray("Prefs");
                    prefrences = new String[prefs.length()];
                    votes = new String[prefs.length()];
                    for (int i = 0; i < prefs.length(); i++) {
                        JSONObject pref = prefs.getJSONObject(i);
                        prefrences[i] = pref.getString("pref");
                        votes[i] = pref.getString("vote");
                    }

                    return "success";

                } else {
                    return "fail";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String success) {

            if (success.equals("success")) {

            }
            if (success.equals("fail")) {
            }
            super.onPostExecute(success);
        }

        public String[] getPrefrences() {
            return prefrences;
        }

        public String[] getVotes() {
            return votes;
        }
    }
}
