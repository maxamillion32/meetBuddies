package com.mk.meetbuddies.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.HashSet;

import com.mk.meetbuddies.EventDialog;
import com.mk.meetbuddies.R;
import com.mk.utils.CalendarView;
import com.mk.utils.DataBaseConnector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

        DataBaseConnector db= new DataBaseConnector(getContext());
        final List<String> calendar= db.getCalendarData();
        final HashSet<Date> events = new HashSet<Date>();
        for(int i=0;i<calendar.size();i++){

            String[] details=calendar.get(i).split("/");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
            Date convertedDate = dateFormat.parse(details[1]);
            events.add(convertedDate);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
          }
        }


        CalendarView cv = ((CalendarView)view.findViewById(R.id.calendarView));
        cv.updateCalendar(events);
        // assign event handler
        cv.setEventHandler(new CalendarView.EventHandler() {
            @Override
            public void onDayLongPress(Date date) {
                // show returned day
                date.setHours(0); //Setting time to 0 to be able t compare the selected date and the date from Google Calendar
                date.setMinutes(0);
                date.setSeconds(0);
                DateFormat df = SimpleDateFormat.getDateInstance();
                HashMap<String,String> ev=getEventInfo(date, calendar);
                if(ev.size()>0) {
                    EventDialog eventDetails = new EventDialog(getContext(), df.format(date), ev);
                    eventDetails.show();
                }else{
                    Toast.makeText(getContext(), df.format(date), Toast.LENGTH_SHORT).show();
                }
            }
 });
        return view;
    }

    private HashMap<String,String> getEventInfo(Date date, List<String> events) {
        HashMap<String,String> event= new HashMap<String,String>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for(int i=0;i<events.size();i++){
            String[] details=events.get(i).split("/");

            try {
                Date tmp=dateFormat.parse(details[1]);
                tmp.setHours(0); tmp.setMinutes(0); tmp.setSeconds(0);
                //  System.out.println(date.getTime()+" This "+tmp.getTime());

                if(tmp.getYear()==date.getYear() && tmp.getMonth()==date.getMonth() && tmp.getDay()==date.getDay()){
                    event.put("title",details[0]);
                    event.put("time",details[2]);

                }else{
                    System.out.println("Not it");
                }



            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        //System.out.println(event);
        return event;
    }
}
