package com.mk.meetbuddies;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mk.meetbuddies.fragments.AdditionalInfo;
import com.mk.meetbuddies.fragments.BuddiesFragment;
import com.mk.meetbuddies.fragments.CalendarFragment;
import com.mk.meetbuddies.fragments.HomeFragment;
import com.mk.meetbuddies.fragments.LocationsFragment;
import com.mk.meetbuddies.fragments.LogoutFragment;
import com.mk.meetbuddies.fragments.MeetingsFragment;
import com.mk.meetbuddies.fragments.ProfileFragment;
import com.mk.utils.DataBaseConnector;

import com.mk.utils.DownloadImg;
import com.mk.utils.ImageUtils;
import com.mk.utils.SessionManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ImageView profilepic;
    private TextView userFull_name, user_group;
    private SessionManager session;
    private NavigationView navigationView;
    private View nav_header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profilepic=(ImageView)findViewById(R.id.user_photo);

        session=new SessionManager(MainActivity.this);


        DataBaseConnector db = new DataBaseConnector(MainActivity.this);
        Cursor cursor = db.getAllUsers();
        if (cursor.moveToFirst()) {
            do {
                if ((cursor.getString(7).equals("")) && (cursor.getString(8).equals(""))) {
                    // open popup
                    FragmentManager fm = getSupportFragmentManager();
                    AdditionalInfo fd = new AdditionalInfo(this);
                    //  fd.show();
                    fd.setCanceledOnTouchOutside(false);
                }
            } while (cursor.moveToNext());
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (cursor.moveToFirst()) {
            if (cursor.getString(7).equals("false")) fab.hide();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_home));

        nav_header = navigationView.getHeaderView(0);

        userFull_name=((TextView) nav_header.findViewById(R.id.user_fullName));
        user_group=((TextView) nav_header.findViewById(R.id.user_group_name));
        profilepic=((ImageView) nav_header.findViewById(R.id.user_photo));
        setUserInfoInMenu();
  }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure,You Want to Quit");

            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

            alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // finish();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (id == R.id.nav_home) {
            fragment = new HomeFragment();
        } else if (id == R.id.nav_profile) {
            fragment = new ProfileFragment();
        } else if (id == R.id.nav_calendar) {
            fragment = new CalendarFragment();
        } else if (id == R.id.nav_buddies) {
            fragment = new BuddiesFragment();
        } else if (id == R.id.nav_locations) {
            fragment = new LocationsFragment();
        } else if (id == R.id.nav_meeting) {
            fragment = new MeetingsFragment();
        } else if (id == R.id.nav_logout) {
            fragment = new LogoutFragment();
        }
        setTitle(item.getTitle());

        fragmentManager.beginTransaction()
                .replace(R.id.mainFragment, fragment)
                        //.set
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        // Highlight the selected item has been done by NavigationView
        // menuItem.setChecked(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void setUserInfoInMenu(){ // Setting User Data (Namen Group and profile picture) in header
        userFull_name.setText(session.getName() + " " + session.getPrename());
        user_group.setText("Group : " + session.getGroup());
        String imageUrl=session.getPhotourl();
        System.out.println(imageUrl);
        DownloadImg down= new DownloadImg();
        down.getImage(profilepic, imageUrl);

     }

}
