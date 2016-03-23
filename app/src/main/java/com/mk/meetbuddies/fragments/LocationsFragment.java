package com.mk.meetbuddies.fragments;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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
public class LocationsFragment extends Fragment {

    private SupportMapFragment fragment;
    private GoogleMap map;
    private GetBuddiesLocationTask locationTask = null;
    private String[] names, prenames, logins;
    private Double[] lat, lon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_locations, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.location_map);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.location_map, fragment).commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (map == null) {
            map = fragment.getMap();
            locationTask = new GetBuddiesLocationTask(map);
            locationTask.execute();

        }
    }

    public class GetBuddiesLocationTask extends AsyncTask<String, String, String> {

        private int id = 0;
        private String[] names, prenames, logins;
        private Double[] lat, lon;
        private GoogleMap map;

        public GetBuddiesLocationTask(GoogleMap map) {
            this.map = map;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            SessionManager session = new SessionManager(getContext());
            DataBaseConnector db = new DataBaseConnector(getContext());
            Cursor cursor = db.getAllUsers();
            if (cursor.moveToFirst()) {
                id = cursor.getInt(0);
            }
            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("id_usr", Integer.toString(id)));

            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.makeHttpRequest("http://meetbuddies.net16.net/Ws/GetBuddies.php", "GET", param);
            Log.i("response http", json.toString());

            try {
                int success = json.getInt("success");
                if (success == 1) {
                    JSONArray users = json.getJSONArray("User");
                    names = new String[users.length()];
                    prenames = new String[users.length()];
                    logins = new String[users.length()];
                    lat = new Double[users.length()];
                    lon = new Double[users.length()];
                    for (int i = 0; i < users.length(); i++) {
                        JSONObject user = users.getJSONObject(i);
                        names[i] = user.getString("name");
                        prenames[i] = user.getString("prename");
                        logins[i] = user.getString("login");
                        String location = user.getString("location");
                        if (location.equals("null")) {
                            lat[i] = Double.parseDouble("0");
                            lon[i] = Double.parseDouble("0");
                        } else {
                            String[] l = location.split("-");
                            lat[i] = Double.parseDouble(l[0]);
                            lon[i] = Double.parseDouble(l[1]);
                        }

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
            MarkerOptions[] markeurs;
            LatLngBounds.Builder builder;
            final CameraUpdate cu;
            List<Marker> markersList = new ArrayList<Marker>();
            if (success.equals("success")) {
                markeurs = new MarkerOptions[names.length];
                for (int i = 0; i < names.length; i++) {
                    markeurs[i] = new MarkerOptions().position(new LatLng(lat[i], lon[i])).title(names[i] + " " + prenames[i]);
                    map.addMarker(markeurs[i]);
                    markersList.add(map.addMarker(markeurs[i]));
                }

                builder = new LatLngBounds.Builder();
                for (Marker m : markersList) {
                    builder.include(m.getPosition());
                }
                int padding = 20;
                LatLngBounds bounds = builder.build();
                cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                        map.animateCamera(cu);
                    }
                });

            }
            if (success.equals("fail")) {
            }
            super.onPostExecute(success);
        }
    }
}
