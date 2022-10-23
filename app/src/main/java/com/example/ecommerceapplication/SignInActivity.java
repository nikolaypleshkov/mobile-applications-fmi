package com.example.ecommerceapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {

    EditText editEmail, editPassword;
    Button signInBtn;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        signInBtn = (Button) findViewById(R.id.btnSignIn);

        firebaseAuth = FirebaseAuth.getInstance();

        signInBtn.setOnClickListener(view -> {
            final ProgressDialog messageDialog = new ProgressDialog(SignInActivity.this);

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

                                        Intent homeActivity = new Intent(SignInActivity.this, HomeActivity.class);
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
