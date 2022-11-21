package com.example.ecommerceapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerceapplication.ui.login.LoginActivity;
import com.example.ecommerceapplication.ui.register.RegisterActivity;

public class MainActivity extends AppCompatActivity {
    Button btnSignIn, btnSignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignIn.setOnClickListener(view -> {
            Intent signIn = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(signIn);
        });

        btnSignUp.setOnClickListener(view -> {
            Intent signUp = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(signUp);
        });

    }
}
