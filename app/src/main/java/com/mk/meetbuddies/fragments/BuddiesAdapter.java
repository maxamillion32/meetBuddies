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

public class BuddiesAdapter extends ArrayAdapter<Buddies> {

    public BuddiesAdapter(Context context, List<Buddies> Buddie) {
        super(context, 0, Buddie);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.buddies_list_items,parent, false);
        }

        TweetViewHolder viewHolder = (TweetViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TweetViewHolder();
            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.text = (TextView) convertView.findViewById(R.id.text);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Buddies> buddies
        Buddies buddie = getItem(position);
        viewHolder.pseudo.setText(buddie.getName()+ " "+ buddie.getPrename());
        viewHolder.text.setText(buddie.getLogin());
        DownloadImg down = new DownloadImg();
        down.getImage((ImageView) convertView.findViewById(R.id.avatar), buddie.getPhoto());

        return convertView;
    }

    private class TweetViewHolder{
        public TextView pseudo;
        public TextView text;
        public ImageView avatar;

    }
}
