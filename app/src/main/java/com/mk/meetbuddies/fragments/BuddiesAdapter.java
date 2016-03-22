package com.mk.meetbuddies.fragments;

/**
 * Created by mourad on 2016-03-21.
 */
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mk.meetbuddies.R;

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

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Buddies buddie = getItem(position);
        viewHolder.pseudo.setText(buddie.getPseudo());
        viewHolder.text.setText(buddie.getText());
        viewHolder.avatar.setImageDrawable(new ColorDrawable(buddie.getColor()));

        return convertView;
    }

    private class TweetViewHolder{
        public TextView pseudo;
        public TextView text;
        public ImageView avatar;

    }
}
