package com.example.ecommerceapplication;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.ecommerceapplication.Entity.Order;
import com.example.ecommerceapplication.Holder.CartAdapter;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapplication.databinding.ActivityCartBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference cartReference;

    TextView editTotalPrice;
    Button placeOrderBtn;

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        database = FirebaseDatabase.getInstance();
        cartReference = database.getReference("Orders");


        recyclerView = findViewById(R.id.orderCart);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        editTotalPrice = findViewById(R.id.total);
        placeOrderBtn = findViewById(R.id.placeOrderBtn);

        placeOrderBtn.setOnClickListener(view -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
            alertDialog.setTitle("One last step...");
            alertDialog.setMessage("Please enter your Delivery Address: ");

            final EditText editAddress = new EditText(CartActivity.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            editAddress.setLayoutParams(layoutParams);
            alertDialog.setPositiveButton("Yes", (dialogInterface, i) -> {
                //TODO: submit order
                //TODO: Submit order to Firebase Database
                //TODO: Clear the cart

                Toast.makeText(CartActivity.this, "Thank You, Order Placed Successfully!", Toast.LENGTH_LONG).show();
                finish();
            });

            alertDialog.setNegativeButton("NO", (dialogInterface, i) -> dialogInterface.dismiss());
            alertDialog.show();
        });

    }
}