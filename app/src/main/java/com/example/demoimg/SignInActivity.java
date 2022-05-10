package com.example.demoimg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    private TextView textSignEmail;
    private TextView textSignPassword;
    private TextView textRegisterView;
    private Button buttonSignIn;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        textSignEmail = findViewById(R.id.textEmailSignIn);
        textSignPassword = findViewById(R.id.textPasswordSignIn);

        mAuth = FirebaseAuth.getInstance();

        textRegisterView = (TextView) findViewById(R.id.textViewCallRegisterView);
        textRegisterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterView();
            }
        });

        buttonSignIn = (Button) findViewById(R.id.buttonViewSignInView);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
                openRatingView();
            }
        });
    }
    private void loginUser() {
        String email = textSignEmail.getText().toString();
        String password = textSignPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            textSignEmail.setError("Email cannot be empty");
            textSignEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            textSignPassword.setError("Password cannot be empty");
            textSignPassword.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignInActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignInActivity.this, RatingActivity.class));
                    } else {
                        Toast.makeText(SignInActivity.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void openRegisterView() {
        Intent intentRegisterView = new Intent(this, RegisterActivity.class);
        startActivity(intentRegisterView);
    }
    private void openRatingView() {
        Intent intentRatingView = new Intent(this, RatingActivity.class);
        startActivity(intentRatingView);
    }
}