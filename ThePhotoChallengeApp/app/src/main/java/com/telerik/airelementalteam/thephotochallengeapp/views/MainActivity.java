package com.telerik.airelementalteam.thephotochallengeapp.views;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
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

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MainPresenter presenter;

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

        String extra= "";
        String extraName="";
        String extraMail="";
        String extraUID ="";

        try{
            Bundle data= this.getIntent().getExtras();
            System.out.println(data);
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
        }

        else if(savedInstanceState == null) {
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
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
            transaction.commit();

        } else if (id == R.id.nav_challenges) {
            ChallengesFragment fragment = new ChallengesFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.commit();

        } else if (id == R.id.nav_favourites) {
            FavouritesFragment fragment = new FavouritesFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.commit();

        } else if (id == R.id.nav_friends) {
            FriendsFragment fragment = new FriendsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.commit();

        } else if (id == R.id.nav_ranking) {
            RankingFragment fragment = new RankingFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.commit();

        } else if (id == R.id.nav_browse) {
            BrowseFragment fragment = new BrowseFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.commit();

        } else if (id == R.id.nav_settings) {
            SettingsFragment fragment = new SettingsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.commit();

        } else if (id == R.id.nav_logout) {
            presenter.logoutUser();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
}
