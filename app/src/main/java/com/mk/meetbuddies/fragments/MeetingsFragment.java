package com.mk.meetbuddies.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mk.meetbuddies.MeetingDetailsActivity;
import com.mk.meetbuddies.R;
import com.mk.utils.JSONParser;
import com.mk.utils.SessionManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeetingsFragment extends Fragment{


    List<Meetings> meetings = new ArrayList<Meetings>();
    ListView mListView;
    private MeetingsTasks mListTask = null;

    public MeetingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meetings, container, false);
        mListView = (ListView) view.findViewById(R.id.meetingsListView);
        afficherListeMeetings();



        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> item, View arg1,
                                    int position, long arg3) {

                Intent t= new Intent(getContext(),MeetingDetailsActivity.class);

                Meetings selectedMeeting= (Meetings)item.getItemAtPosition(position);
                if(selectedMeeting.getDate()!=""){
                    t.putExtra("location", selectedMeeting.getLocation());
                    t.putExtra("date", selectedMeeting.getDate());
                    t.putExtra("time", selectedMeeting.getTime());
                    t.putExtra("description", selectedMeeting.getDescription());
                    t.putExtra("id",selectedMeeting.getId());
                    System.out.println(selectedMeeting.getName());
                    t.putExtra("name",selectedMeeting.getName());
                    t.putExtra("status",selectedMeeting.getStatus());
                    startActivity(t);
                }

             }
        });
        return view;
    }

    private void afficherListeMeetings() {
        mListTask = new MeetingsTasks();
        mListTask.execute();
    }

    public class MeetingsTasks extends AsyncTask<String, String, List<Meetings>> {
        protected List<Meetings> doInBackground(String... params) {
            List<Meetings> meetings = new ArrayList<Meetings>();
            SessionManager session = new SessionManager(getContext());
            String mGroup = session.getGroup();
            int mId = session.getId();
            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("group", mGroup));

            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.makeHttpRequest("http://meetbuddies.net16.net/Ws/meeting.php", "GET", param);
            Log.i("response http", mId + " - " + mGroup);
            try {
                int success = json.getInt("success");
                if (success == 1) {

                    JSONArray users = json.getJSONArray("Meeting");
                    Log.i("users length", users.length()+"");
                    for (int i = 0; i < users.length(); i++) {
                        JSONObject user = users.getJSONObject(i);
                        String location = user.getString("location_name");
                        String date = user.getString("date");
                        String time = user.getString("time");
                        String id=user.getString("meeting_id");
                        String description = user.getString("description");
                        String name=user.getString("meeting_name");
                        String status=user.getString("status");
                        meetings.add(new Meetings(location, date, time, description,id,name,status));
                    }if (users.length()>0)
                        return meetings;
                    else {
                        meetings.add(new Meetings("You Have No Meetings", "", "", "","","",""));
                        return meetings;
                    }
                } else {
                    meetings.add(new Meetings("You Have No Meetings", "", "", "","","",""));

                    return meetings;
                }

            } catch (JSONException e) {
                meetings.add(new Meetings("You Have No Meetings", "", "", "","","",""));
                return meetings;

            }

        }
        protected void onPostExecute(List<Meetings> meetings) {
            MeetingsAdapter adapter = new MeetingsAdapter(getActivity().getApplicationContext(), meetings);

            mListView.setAdapter(adapter);
            super.onPostExecute(meetings);

        }
    }




}
