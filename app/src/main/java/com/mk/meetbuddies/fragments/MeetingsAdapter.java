package com.mk.meetbuddies.fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

            viewHolder.locationLabel = (TextView) convertView.findViewById(R.id.textViewLocation);
            viewHolder.dateLabel = (TextView) convertView.findViewById(R.id.textView2);
            viewHolder.timeLabel = (TextView) convertView.findViewById(R.id.textViewTime);
            viewHolder.descriptionLabel = (TextView) convertView.findViewById(R.id.textViewDescription);
            viewHolder.status = (TextView) convertView.findViewById(R.id.textViewStatus);

        }

        //getItem(position) va récupérer l'item [position] de la List<Meetings> meetings
        Meetings meeting = getItem(position);
        viewHolder.location.setText(meeting.getLocation());
        viewHolder.date.setText(meeting.getDate());
        viewHolder.time.setText(meeting.getTime());
        viewHolder.status.setText(meeting.getStatus());
        viewHolder.description.setText(meeting.getDescription());
        if(viewHolder.location.getText()=="You Have No Meetings"){
            viewHolder.locationLabel.setVisibility(View.INVISIBLE);
            viewHolder.dateLabel.setVisibility(View.INVISIBLE);
            viewHolder.timeLabel.setVisibility(View.INVISIBLE);
            viewHolder.descriptionLabel.setVisibility(View.INVISIBLE);
            viewHolder.status.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.locationLabel.setVisibility(View.VISIBLE);
            viewHolder.dateLabel.setVisibility(View.VISIBLE);
            viewHolder.timeLabel.setVisibility(View.VISIBLE);
            viewHolder.descriptionLabel.setVisibility(View.VISIBLE);
            viewHolder.status.setVisibility(View.VISIBLE);
            if(meeting.getStatus().equals("Confirmed")){
                viewHolder.status.setTextColor(Color.GREEN);
            }else{
                viewHolder.status.setTextColor(Color.RED);
            }
        }

        return convertView;
    }

    private class MeetingViewHolder{
        public TextView location;
        public TextView date;
        public TextView time;
        public TextView description;
        public TextView dateLabel;
        public TextView timeLabel;
        public TextView descriptionLabel;
        public TextView locationLabel;
        public TextView status;
    }
}
