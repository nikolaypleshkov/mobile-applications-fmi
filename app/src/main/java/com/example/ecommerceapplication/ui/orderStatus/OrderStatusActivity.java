package com.example.ecommerceapplication.ui.orderStatus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ecommerceapplication.R;
import com.example.ecommerceapplication.common.CurrentAuthUser;
import com.example.ecommerceapplication.data.model.SubmitOrder;
import com.example.ecommerceapplication.holder.OrderAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderStatusActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseFirestore db;
    List<SubmitOrder> orderList;

    OrderAdapter orderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        db = FirebaseFirestore.getInstance();

        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(orderList, this);

        recyclerView = findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(CurrentAuthUser.customer.getEmail());
        orderList = new ArrayList<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadOrders(String email){
        db.collection("submit_orders").whereEqualTo("email", email).addSnapshotListener((value, error) -> {

            if(error != null){
                Toast.makeText(this, "You don't have any submitted orders", Toast.LENGTH_SHORT).show();
                return;
            }

            for(QueryDocumentSnapshot doc : value){
                SubmitOrder order = doc.toObject(SubmitOrder.class);

                orderList.add(order);

            }

            recyclerView.setAdapter(new OrderAdapter(orderList, this));

            orderAdapter.notifyDataSetChanged();

            orderList = new ArrayList<>();
        });
    }
}