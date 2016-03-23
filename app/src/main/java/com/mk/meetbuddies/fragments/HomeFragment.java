package com.mk.meetbuddies.fragments;


import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mk.meetbuddies.R;
import com.mk.utils.DataBaseConnector;
import com.mk.utils.JSONParser;
import com.mk.utils.MyGpsLocationListener;
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
public class HomeFragment extends Fragment implements View.OnClickListener {

    MyGpsLocationListener gps;
    private TextView user, groupLabel, position, next, welcome;
    private Typeface font, welcomeFont;
    private Button updateLocation;
    private UpdateLocationTask mUpdateLocation = null;

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        user = (TextView) view.findViewById(R.id.user_name);
        groupLabel = (TextView) view.findViewById(R.id.groupName);
        position = (TextView) view.findViewById(R.id.position);
        //next=(TextView)view.findViewById(R.id.nextMeeting);
        welcome = (TextView) view.findViewById(R.id.wlc);
        font = Typeface.createFromAsset(getActivity().getAssets(), "bariol.ttf");
        welcomeFont = Typeface.createFromAsset(getActivity().getAssets(), "ubuntu.ttf");
        updateLocation = (Button) view.findViewById(R.id.button_update_position);
        gps = new MyGpsLocationListener(getContext());
        setUpDashboard();
        return view;
    }

    private void setUpDashboard() {

        System.out.println("Dashbord Init............");
        SessionManager session = new SessionManager(getContext());
        System.out.println(session.getName() + " " + session.getPrename());
        user.setText(session.getName() + " " + session.getPrename());
        user.setTypeface(font);
        welcome.setTypeface(welcomeFont);
        groupLabel.setText(session.getGroup());

        double latitude = 0;
        double longitude = 0;
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            Toast.makeText(
                    getContext(),
                    "Your Location is -\nLat: " + latitude + "\nLong: "
                            + longitude, Toast.LENGTH_LONG).show();
        } else {
            gps.showSettingsAlert();
        }
        position.setText(gps.getLocationName(latitude, longitude));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_update_position:
                double latitude = 0;
                double longitude = 0;
                if (gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    position.setText(gps.getLocationName(latitude, longitude));
                    mUpdateLocation = new UpdateLocationTask(latitude, longitude);
                } else {
                    gps.showSettingsAlert();
                }
                break;
            default:
                break;
        }
    }

    public class UpdateLocationTask extends AsyncTask<String, String, String> {
        private double lat, lon;
        private int id = 0;

        public UpdateLocationTask(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
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
            String current_loc = lat + "-" + lon;
            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("id_usr", Integer.toString(id)));
            param.add(new BasicNameValuePair("current_location", current_loc));

            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.makeHttpRequest("http://meetbuddies.net16.net/Ws/UpdateLocation.php", "GET", param);
            Log.i("response http", json.toString());

            try {
                int success = json.getInt("success");
                if (success == 1) {
                    JSONArray users = json.getJSONArray("User");
                    JSONObject user = users.getJSONObject(0);
                    float la = (float) lat;
                    float lo = (float) lon;
                    session.modifyPosition(la, lo);
                    db.modifyPosition(id, lat, lon);
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
            super.onPostExecute(success);
        }
    }

}
