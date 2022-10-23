package com.example.ecommerceapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button signUpBtn, signInBtn;
    TextView textSlogan;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();


        signUpBtn = (Button) findViewById(R.id.btnSignUp);
        signInBtn = (Button) findViewById(R.id.btnSignIn);

        textSlogan = (TextView) findViewById(R.id.textSlogan);

        signInBtn.setOnClickListener(view -> {
            Intent signIn = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(signIn);
        });

        signUpBtn.setOnClickListener(view -> {
            Intent signUp = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(signUp);
        });
    }
}
