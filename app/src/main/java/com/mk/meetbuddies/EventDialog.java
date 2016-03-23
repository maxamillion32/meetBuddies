package com.mk.meetbuddies;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by Malek on 3/23/2016.
 */
public class EventDialog extends Dialog {
    TextView title, date, time;
    HashMap<String,String> eventDetails;
    String eventDate;
    public EventDialog(Context context, String date, HashMap<String,String> info) {
        super(context);
        eventDetails=info;
        eventDate=date;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.events_dialog);

        title=(TextView)findViewById(R.id.title_event_dialog);
        date=(TextView)findViewById(R.id.date_event_dialog);
        time=(TextView)findViewById(R.id.time_event_Dialog);

        title.setText(eventDetails.get("title"));
        time.setText(eventDetails.get("time"));
        date.setText(eventDate);
    }



}
