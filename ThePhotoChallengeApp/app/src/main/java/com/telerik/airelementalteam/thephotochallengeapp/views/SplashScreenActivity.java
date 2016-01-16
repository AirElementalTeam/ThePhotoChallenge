package com.telerik.airelementalteam.thephotochallengeapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.splashscreen.SplashScreenPresenter;

public class SplashScreenActivity extends AppCompatActivity {
    private SplashScreenPresenter presenter;
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        System.out.println("REfDB --- >");
        presenter = new SplashScreenPresenter(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (presenter.isAuthUser()) {
                    //user is logged in
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    //user is not log in
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
