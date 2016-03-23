package com.mk.meetbuddies.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.mk.meetbuddies.R;
import com.mk.utils.DataBaseConnector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
//import com.mk.utils.CalendarApi;



/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {


    public CalendarFragment() {
        // Required empty public constructor


    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        CalendarView c= (CalendarView)view.findViewById(R.id.calendarView);
      //  c.setDate(new Date );
        DataBaseConnector db= new DataBaseConnector(getContext());
        List<String> calendar= db.getCalendarData();
        for(int i=0;i<calendar.size();i++){
            System.out.println(calendar.get(i));
            String[] details=calendar.get(i).split("/");
            System.out.println("Date: "+details[1]);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
          //  c.set


            try {
                Date convertedDate = dateFormat.parse(details[1]);
                System.out.println("Date Converted: "+convertedDate);
                c.setDate(convertedDate.getTime(), true, false);

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return view;
    }

}
