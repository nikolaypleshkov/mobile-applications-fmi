package com.example.ecommerceapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerceapplication.Entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class SignUpActivity extends AppCompatActivity {

    EditText editName, editEmail, editPhone, editPassword;
    Button signUpBtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editName = (EditText) findViewById(R.id.editName);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editPassword = (EditText) findViewById(R.id.editPassword);

        signUpBtn = (Button) findViewById(R.id.signUp);

        firebaseAuth = FirebaseAuth.getInstance();

        signUpBtn.setOnClickListener(view -> {
            final ProgressDialog messageDialog = new ProgressDialog(SignUpActivity.this);

            messageDialog.setMessage("Please wait...");
            messageDialog.show();

            String email = editEmail.getText().toString();
            String name = editName.getText().toString();
            String phone = editPhone.getText().toString();
            String password = editPassword.getText().toString();
            if (TextUtils.isEmpty(email) ||
                    TextUtils.isEmpty(name) ||
                    TextUtils.isEmpty(phone) ||
                    TextUtils.isEmpty(password)) {
                messageDialog.dismiss();
                Toast.makeText(getApplicationContext(), "All Fields are required!", Toast.LENGTH_LONG).show();
            }
            else {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Sign up successful!", Toast.LENGTH_LONG).show();
                                messageDialog.dismiss();

                                Intent signInActivity = new Intent(SignUpActivity.this, SignInActivity.class);
                                startActivity(signInActivity);
                            }
                            else{
                                Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                                messageDialog.dismiss();
                            }
                        });
            }
        });
    }

}

