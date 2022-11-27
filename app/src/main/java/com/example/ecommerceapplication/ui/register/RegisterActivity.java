package com.example.ecommerceapplication.ui.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerceapplication.R;
import com.example.ecommerceapplication.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;


public class RegisterActivity extends AppCompatActivity {

    EditText editName, editEmail, editPassword;
    Button btnRegister;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editName = findViewById(R.id.editFullname);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        btnRegister = findViewById(R.id.btnRegister);

        firebaseAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(view -> {
            final ProgressDialog messageDialog = new ProgressDialog(RegisterActivity.this);

            messageDialog.setMessage("Please wait...");
            messageDialog.show();

            String email = editEmail.getText().toString();
            String name = editName.getText().toString();
            String password = editPassword.getText().toString();
            if (TextUtils.isEmpty(email) ||
                    TextUtils.isEmpty(name) ||
                    TextUtils.isEmpty(password)) {
                messageDialog.dismiss();
                Toast.makeText(getApplicationContext(), "All Fields are required!", Toast.LENGTH_LONG).show();
            }
            else {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                FirebaseUser user = task.getResult().getUser();

                                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();

                                assert user != null;
                                user.updateProfile(profileChangeRequest).addOnCompleteListener(updateTask -> {
                                   if(updateTask.isSuccessful()){
                                       Toast.makeText(getApplicationContext(), "Sign up successful!", Toast.LENGTH_LONG).show();
                                       messageDialog.dismiss();

                                       Intent signInActivity = new Intent(RegisterActivity.this, LoginActivity.class);
                                       startActivity(signInActivity);
                                   } else {

                                       Toast.makeText(getApplicationContext(), Objects.requireNonNull(updateTask.getException()).getMessage(), Toast.LENGTH_LONG).show();
                                       messageDialog.dismiss();
                                   }
                                });
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