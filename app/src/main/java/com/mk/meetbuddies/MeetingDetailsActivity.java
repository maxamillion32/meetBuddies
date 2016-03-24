package com.mk.meetbuddies;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mk.meetbuddies.fragments.Meetings;
import com.mk.meetbuddies.fragments.VoteResult;
import com.mk.utils.SessionManager;
import com.mk.utils.VotesDialog;

public class MeetingDetailsActivity extends AppCompatActivity {
    Meetings meeting;
    private TextView title,locationLab,date,time,description, status, organizer;
    private ImageButton back;
    private Button voteConfirm;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_details);
        title=(TextView)findViewById(R.id.meeting_title);
        locationLab=(TextView)findViewById(R.id.meetingLocation);
        date=(TextView)findViewById(R.id.meetingDate);
        time=(TextView)findViewById(R.id.meetingTime);
        description=(TextView)findViewById(R.id.metingDescription);
        status=(TextView)findViewById(R.id.status);
        back=(ImageButton)findViewById(R.id.backButton);
        voteConfirm=(Button)findViewById(R.id.button_v_c);
        session= new SessionManager(MeetingDetailsActivity.this);
        voteConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  session.gethisGroupOrg();
                    if(session.gethisGroupOrg()){
                        //Todo open Vote Results
                        meeting.getId();
                     //   FragmentManager fm = getSupportFragmentManager();
                        VoteResult viteResult = new VoteResult(MeetingDetailsActivity.this, meeting.getId());
                        viteResult.show();
                       // viteResult.setCanceledOnTouchOutside(false);
                    }else{
                        //TODO Open Vote Dialog
                        VotesDialog votes = new VotesDialog(MeetingDetailsActivity.this,meeting);
                        votes.show();
                    }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            String location;
            if(extras == null) {

            } else {
                location= extras.getString("location");
                meeting= new Meetings(extras.getString("location"),extras.getString("date"),extras.getString("time"),extras.getString("description"),
                        extras.getString("id"), extras.getString("name"),extras.getString("status"));

                title.setText(meeting.getName());
                date.setText(meeting.getDate());
                time.setText(meeting.getTime());
                description.setText(meeting.getDescription());

                status.setText(meeting.getStatus());
                if(meeting.getStatus().equals("Confirmed")){
                    status.setTextColor(Color.GREEN);
                    locationLab.setText(meeting.getLocation());
                    voteConfirm.setEnabled(false);
                    voteConfirm.setBackgroundColor(Color.DKGRAY);

                }else{

                    locationLab.setText("Not Confirmed");
                }
                if(session.gethisGroupOrg()){
                    //Todo open Vote Results
                    voteConfirm.setText("See Vote Results");
                }else{
                    voteConfirm.setText("Vote for Your favorite location");

                }

            }
        }
    }
}
