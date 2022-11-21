package com.example.ecommerceapplication.ui.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerceapplication.R;
import com.example.ecommerceapplication.ui.HomeActivity;
import com.example.ecommerceapplication.ui.register.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText editEmail, editPassword;
    Button btnToRegisterActivity, btnLogin;


    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnToRegisterActivity = findViewById(R.id.btnToRegisterActivity);

        btnToRegisterActivity.setOnClickListener(view -> {

            Intent signIn = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(signIn);
        });

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view -> {
            final ProgressDialog messageDialog = new ProgressDialog(LoginActivity.this);

            messageDialog.setMessage("Please wait...");
            messageDialog.show();

            String email = editEmail.getText().toString();
            String password = editPassword.getText().toString();

            if (TextUtils.isEmpty(email) ||
                    TextUtils.isEmpty(password)) {
                messageDialog.dismiss();
                Toast.makeText(getApplicationContext(), "All Fields are required!", Toast.LENGTH_LONG).show();
            }
            else{
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                                task -> {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                                        messageDialog.dismiss();

                                        Intent homeActivity = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(homeActivity);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                                        messageDialog.dismiss();
                                    }
                                }
                        );
            }
        });

    }

}