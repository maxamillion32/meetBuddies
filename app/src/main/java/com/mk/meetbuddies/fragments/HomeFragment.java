package com.mk.meetbuddies.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mk.meetbuddies.R;
import com.mk.utils.MyGpsLocationListener;
import com.mk.utils.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    MyGpsLocationListener gps;
    private TextView user, groupLabel, position,next, welcome;
    private Typeface font,welcomeFont;
    private Button updateLocation;
    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        user = (TextView)view.findViewById(R.id.user_name);
        groupLabel=(TextView)view.findViewById(R.id.groupName);
        position =(TextView)view.findViewById(R.id.position);
        //next=(TextView)view.findViewById(R.id.nextMeeting);
        welcome=(TextView)view.findViewById(R.id.wlc);
        font = Typeface.createFromAsset(getActivity().getAssets(), "bariol.ttf");
        welcomeFont= Typeface.createFromAsset(getActivity().getAssets(), "ubuntu.ttf");
        updateLocation= (Button)view.findViewById(R.id.button_update_position);
        gps= new MyGpsLocationListener(getContext());
        setUpDashboard();
        return view;
    }
    private void setUpDashboard(){

        System.out.println("Dashbord Init............");
        SessionManager session = new SessionManager(getContext());
        System.out.println(session.getName() + " " + session.getPrename());
        user.setText(session.getName() + " " + session.getPrename());
        user.setTypeface(font);
        welcome.setTypeface(welcomeFont);
        groupLabel.setText(session.getGroup());
        double latitude=0;
        double longitude=0;
        if(gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            Toast.makeText(
                    getContext(),
                    "Your Location is -\nLat: " + latitude + "\nLong: "
                            + longitude, Toast.LENGTH_LONG).show();
        } else {
            gps.showSettingsAlert();
        }
        position.setText(gps.getLocationName(latitude,longitude));
 }

    @Override
    public void onClick(View v) {
        double latitude=0;
        double longitude=0;
        if(gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        } else {
            gps.showSettingsAlert();
        }

        //TODO Save Coordonate in Data Base
    }

}
