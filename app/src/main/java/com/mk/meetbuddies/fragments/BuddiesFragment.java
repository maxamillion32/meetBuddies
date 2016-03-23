package com.mk.meetbuddies.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mk.meetbuddies.R;
import com.mk.utils.DataBaseConnector;
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
public class BuddiesFragment extends Fragment {

    List<Buddies> buddies = new ArrayList<Buddies>();
    ListView mListView;
    private BuddiesTasks mListTask = null;

    public BuddiesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buddies, container, false);
        mListView = (ListView) view.findViewById(R.id.buddiesListView);
        afficherListeBuddies();
        return view;
    }

    private void afficherListeBuddies() {
        mListTask = new BuddiesTasks();
        mListTask.execute();

        //List<Buddies> buddies = genererBuddies();
        //BuddiesAdapter adapter = new BuddiesAdapter(getActivity().getApplicationContext(), buddies);
        //mListView.setAdapter(adapter);


    }

    public void setList(List<Buddies> buddies) {
        this.buddies = buddies;
    }


    public class BuddiesTasks extends AsyncTask<String, String, List<Buddies>> {
        //private List<Buddies> genererBuddies() {
        protected List<Buddies> doInBackground(String... params) {
            List<Buddies> buddies = new ArrayList<Buddies>();
            SessionManager session = new SessionManager(getContext());
            String mGroup = session.getGroup();
            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("group", mGroup));

        /*buddies.add(new Buddies(session.getPhotourl(), session.getGroup(), "mourad_mamlouk@gmail.com"));
        return buddies;*/

            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.makeHttpRequest("http://meetbuddies.net16.net/buddies.php", "GET", param);
            Log.i("response http", json.toString());
            try {
                int success = json.getInt("success");
                if (success == 1) {
                    JSONArray users = json.getJSONArray("User");
                    for (int i = 0; i < users.length(); i++) {
                        JSONObject user = users.getJSONObject(i);
                        String login = user.getString("login");
                        String name = user.getString("name");
                        String prename = user.getString("prename");
                        String photo = user.getString("photo");
                        buddies.add(new Buddies(login, name, prename, photo));
                    }
                    return buddies;
                } else {
                    return null;
                }

            } catch (JSONException e) {
                return null;
            }

        }
        protected void onPostExecute(List<Buddies> buddies) {
            BuddiesAdapter adapter = new BuddiesAdapter(getActivity().getApplicationContext(), buddies);
            mListView.setAdapter(adapter);
            super.onPostExecute(buddies);

        }
    }

}
