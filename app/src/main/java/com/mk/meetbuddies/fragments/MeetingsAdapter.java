package com.mk.meetbuddies.fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mk.meetbuddies.R;
import com.mk.utils.DownloadImg;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class MeetingsAdapter extends ArrayAdapter<Meetings> {

    public MeetingsAdapter(Context context, List<Meetings> Meeting) {
        super(context, 0, Meeting);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.meeting_list_items,parent, false);
        }

        MeetingViewHolder viewHolder = (MeetingViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new MeetingViewHolder();
            viewHolder.location = (TextView) convertView.findViewById(R.id.location);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            viewHolder.description = (TextView) convertView.findViewById(R.id.description);
        }

        //getItem(position) va récupérer l'item [position] de la List<Meetings> meetings
        Meetings meeting = getItem(position);
        viewHolder.location.setText(meeting.getLocation());
        viewHolder.date.setText(meeting.getDate());
        viewHolder.time.setText(meeting.getTime());
        viewHolder.description.setText(meeting.getDescription());

        return convertView;
    }

    private class MeetingViewHolder{
        public TextView location;
        public TextView date;
        public TextView time;
        public TextView description;


    }
}
