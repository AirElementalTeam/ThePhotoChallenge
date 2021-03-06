package com.telerik.airelementalteam.thephotochallengeapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.login.LoginPresenter;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.emailInput) AutoCompleteTextView emailInput;
    @InjectView(R.id.passInput) AutoCompleteTextView passwordInput;
    @InjectView(R.id.btnLogin) Button loginButton;
    @InjectView(R.id.link_signup) TextView linkSignUp;

    private LoginPresenter presenter;
    //DatabaseAdapter SQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        presenter = new LoginPresenter(this);

        presenter.emailAutocomplete(emailInput);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                presenter.attemptLogin(email, password);

            }
        });

        linkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
