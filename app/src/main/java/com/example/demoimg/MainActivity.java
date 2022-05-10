package com.example.demoimg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button buttonSignin;
    private Button buttonRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        buttonSignin = (Button) findViewById(R.id.buttonSignIn);
        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignInActivity();
            }
        });

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterView();
            }
        });
    }

    private void openRegisterView() {
        Intent intentRegister = new Intent(this, RegisterActivity.class);
        startActivity(intentRegister);
    }

    private void openSignInActivity() {
        Intent intentSignIn = new Intent(this, SignInActivity.class);
        startActivity(intentSignIn);
    }
}