package com.example.ecommerceapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerceapplication.Entity.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button signUpBtn, signInBtn;
    TextView textSlogan;

    FirebaseDatabase database;
    DatabaseReference itemList;

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

        Item[] items = new Item[]{
                new Item("Item Name", "", "Item Description", "300", "1"),
                new Item("Item 2 Name", "", "Item 2 Description", "500", "2")
        };

        database = FirebaseDatabase.getInstance("https://ecommerce-30ed3-default-rtdb.europe-west1.firebasedatabase.app/");

        for (Item item : items){
            database.getReference("Items").child(item.getMenuId()).setValue(item);
            Toast.makeText(MainActivity.this, "Item added", Toast.LENGTH_LONG).show();
        }
    }
}
