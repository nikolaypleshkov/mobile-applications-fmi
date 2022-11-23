package com.example.ecommerceapplication.ui.cart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ecommerceapplication.R;
import com.example.ecommerceapplication.data.model.Order;
import com.example.ecommerceapplication.database.DBActivity;
import com.example.ecommerceapplication.holder.CartAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseFirestore db;

    TextView txtTotalPrice;
    Button btnPlaceOrder;

    List<Order> cart = new ArrayList<>();

    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.listCart);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = findViewById(R.id.total);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        btnPlaceOrder.setOnClickListener(view -> {
            //TODO: show alert dialog
        });

        //TODO: load cart items
        loadCartItems();
    }

    private void showAlertDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
    }

    private void loadCartItems(){
        cart = new DBActivity(this).getCartItems();
        cartAdapter = new CartAdapter((ArrayList<Order>) cart, this);
        recyclerView.setAdapter(cartAdapter);

        int total = 0;

        for(Order order : cart){
            total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.GERMAN);
            txtTotalPrice.setText(numberFormat.format(total));
        }
    }
}