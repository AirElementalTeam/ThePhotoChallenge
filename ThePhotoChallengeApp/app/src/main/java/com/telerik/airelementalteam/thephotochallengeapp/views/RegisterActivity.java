package com.telerik.airelementalteam.thephotochallengeapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.register.RegisterPresenter;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RegisterActivity extends AppCompatActivity {

    @InjectView(R.id.emailInput) EditText emailInput;
    @InjectView(R.id.nameInput) EditText nameInput;
    @InjectView(R.id.passInput) EditText passInput;
    @InjectView(R.id.passConfirmInput) EditText passConfirmText;
    @InjectView(R.id.btnRegister) Button registerButton;
    @InjectView(R.id.link_login)TextView loginLink;

    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.inject(this);
        presenter = new RegisterPresenter(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email  = emailInput.getText().toString();
                String name = nameInput.getText().toString();
                String password = passInput.getText().toString();
                String confirmPassword = passConfirmText.getText().toString();

                presenter.attemptRegistration(email, name, password, confirmPassword);
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
