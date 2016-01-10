package com.telerik.airelementalteam.thephotochallengeapp.views;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.home.HomePresenter;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeActivity extends AppCompatActivity {

    @InjectView(R.id.logoutBtn) Button logoutButton;
    @InjectView(R.id.mainBtn) Button mainButton;
    @InjectView(R.id.drawer_layout) DrawerLayout drawer;
    @InjectView(R.id.left_drawer) ListView drawerList;

    private String[] titles;
    private HomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);
        presenter = new HomePresenter(this);


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.logoutUser();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
