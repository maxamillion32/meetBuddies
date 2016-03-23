package com.mk.meetbuddies.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mk.meetbuddies.MainActivity;
import com.mk.meetbuddies.R;
import com.mk.utils.DownloadImg;
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

    ListView mListView;
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

    private void afficherListeBuddies(){
        List<Buddies> buddies = genererBuddies();
        BuddiesAdapter adapter = new BuddiesAdapter(getActivity().getApplicationContext(), buddies);
        mListView.setAdapter(adapter);
    }

    private List<Buddies> genererBuddies(){
        List<Buddies> buddies = new ArrayList<Buddies>();
        SessionManager session = new SessionManager(getContext());
        String mGroup=session.getGroup();
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
                JSONObject user = users.getJSONObject(0);
                buddies.add(new Buddies("", "kk", "mourad_mamlouk@gmail.com"));
                return buddies;
            }
            else{
                return null;
            }

        }catch(JSONException e){
            return null;
        }

    }


}
