package com.example.ecommerceapplication.ui.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapplication.R;
import com.example.ecommerceapplication.common.CurrentAuthUser;
import com.example.ecommerceapplication.data.model.Order;
import com.example.ecommerceapplication.data.model.SubmitOrder;
import com.example.ecommerceapplication.database.DBActivity;
import com.example.ecommerceapplication.holder.CartAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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
            showAlertDialog();
        });

        //TODO: load cart items
        loadCartItems();
    }

    private void showAlertDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
        alertDialog.setTitle("One last step...");
        alertDialog.setMessage("Enter delivery address: ");

        final EditText txtEditAdress = new EditText(CartActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        txtEditAdress.setLayoutParams(layoutParams);
        alertDialog.setView(txtEditAdress);
        alertDialog.setPositiveButton("Yes", (dialogInterface, i) -> {
            Random rnd = new Random();
            int number = rnd.nextInt(99999999);
            @SuppressLint("DefaultLocale") String OrderNumber =  String.format("%08d", number);

            SubmitOrder submitOrder = new SubmitOrder(
                    OrderNumber,
                    CurrentAuthUser.customer.getEmail(),
                    "0",
                    txtEditAdress.getText().toString(),
                    txtTotalPrice.getText().toString(),
                    cart
            );
            //TODO: submit order to firebase
            db.collection("submit_orders").add(submitOrder).addOnCompleteListener(task -> {
                new DBActivity(getBaseContext()).clearCart();
                Toast.makeText(CartActivity.this, "Thank you for your purchase!", Toast.LENGTH_SHORT).show();

            }).addOnFailureListener(e -> Toast.makeText(CartActivity.this, "Submitting order failed! Please try again!", Toast.LENGTH_SHORT).show());

            finish();

        });
        alertDialog.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        alertDialog.show();
    }

    private void loadCartItems(){
        cart = new DBActivity(this).getCartItems();
        cartAdapter = new CartAdapter((ArrayList<Order>) cart, this, order -> {
            new DBActivity(getBaseContext()).removeItemFromCart(order.getItemName());
            finish();
            startActivity(getIntent());
        });
        recyclerView.setAdapter(cartAdapter);

        int total = 0;

        for(Order order : cart){
            total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.ITALY);
            txtTotalPrice.setText(numberFormat.format(total));
        }
    }
}