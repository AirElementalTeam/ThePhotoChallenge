package com.telerik.airelementalteam.thephotochallengeapp.views;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.MainPresenter;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.BrowseFragment;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.ChallengesFragment;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.CreateChallengeFragment;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.FavouritesFragment;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.FriendsFragment;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.RankingFragment;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.SettingsFragment;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.UserFragment;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener {

    private MainPresenter presenter;

    private File newImageFile;

    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    @InjectView(R.id.btn_notification) ImageButton notificationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        presenter = new MainPresenter(this);
        presenter.getNameAndMail();

        String extra= "";
        String extraName="";
        String extraMail="";
        String extraUID ="";

        try{
            Bundle data= this.getIntent().getExtras();
            //System.out.println(data);
            extraName = data.getString("name");
            extraMail = data.getString("email");
            extraUID = data.getString("uid");
            extra = data.getString("notification");
        }catch(Exception e){
            e.printStackTrace();
        }

        System.out.println("_______EXTRA_______");
        System.out.println(extra);
        System.out.println("_______EXTRA NAME_______");
        System.out.println(extraName);
        System.out.println("_______EXTRA MAIL_______");
        System.out.println(extraMail);

        if((extra != null) && extra.equals("notificationFriendRequest")){

            UserFragment fragment = new UserFragment();
            fragment.setFriendRequestRecieved(true);
            fragment.setIsFriend(false);
            fragment.setNotFriend(false);
            fragment.setName(extraName);
            fragment.setEmail(extraMail);
            fragment.setUid(extraUID);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.commit();

        } else if((extra != null) && extra.equals("notificationFriendRequest")){
            UserFragment fragment = new UserFragment();
            fragment.setFriendRequestRecieved(false);
            fragment.setIsFriend(true);
            fragment.setNotFriend(false);
            fragment.setName(extraName);
            fragment.setEmail(extraMail);
            fragment.setUid(extraUID);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.commit();

        } else if(savedInstanceState == null) {
            ChallengesFragment fragment = new ChallengesFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.commit();
        }

        toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);

        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home button
        ab.setDisplayShowHomeEnabled(true); // show or hide the default home button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        ab.setDisplayShowTitleEnabled(false); // disable the default title element here (for centered title)

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        presenter.getNameAndMail();
        presenter.listenForChanges();

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: custom notification in app
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        presenter.getNameAndMail();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int count = getFragmentManager().getBackStackEntryCount();

            if (count == 0) {
                super.onBackPressed();
            } else {
                getFragmentManager().popBackStack();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_createChallenge) {
            CreateChallengeFragment fragment = new CreateChallengeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.addToBackStack("CreateChallengeFragment");
            transaction.commit();

        } else if (id == R.id.nav_challenges) {
            ChallengesFragment fragment = new ChallengesFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.addToBackStack("ChallengesFragment");
            transaction.commit();

        } else if (id == R.id.nav_favourites) {
            FavouritesFragment fragment = new FavouritesFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.addToBackStack("FavouritesFragment");
            transaction.commit();

        } else if (id == R.id.nav_friends) {
            FriendsFragment fragment = new FriendsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.addToBackStack("FriendsFragment");
            transaction.commit();

        } else if (id == R.id.nav_ranking) {
            RankingFragment fragment = new RankingFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.addToBackStack("RankingFragment");
            transaction.commit();

        } else if (id == R.id.nav_browse) {
            BrowseFragment fragment = new BrowseFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.addToBackStack("BrowseFragment");
            transaction.commit();

        } else if (id == R.id.nav_settings) {
            SettingsFragment fragment = new SettingsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.addToBackStack("SettingsFragment");
            transaction.commit();

        } else if (id == R.id.nav_logout) {
            presenter.logoutUser();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //presenter.getNameAndMail();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        presenter.getNameAndMail();
        super.onResume();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("AFTER PICTURE TAKEN in MAIN");
        System.out.println("requestcode    " + requestCode );
        System.out.println("resultCode    " + requestCode);
        System.out.println("data    " + data );
        super.onActivityResult(requestCode, resultCode, data);
    }

    public File getNewImageFile() {
        return newImageFile;
    }

    public void setNewImageFile(File newImageFile) {
        this.newImageFile = newImageFile;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
