package com.example.ecommerceapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.ecommerceapplication.Entity.Category;
import com.example.ecommerceapplication.Entity.Item;
import com.example.ecommerceapplication.Holder.ItemHolder;
import com.example.ecommerceapplication.Listener.ItemClickListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapplication.databinding.ActivityItemListBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ItemList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference itemList;

    String categoryId;

    FirebaseRecyclerAdapter<Item, ItemHolder> adapter;
    Query query;
    FirebaseRecyclerOptions<Item> options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        database = FirebaseDatabase.getInstance();
        itemList = database.getReference("Items");

        query = FirebaseDatabase.getInstance().getReference("Items").orderByChild("MenuId");
        options = new FirebaseRecyclerOptions.Builder<Item>().setQuery(query, Item.class).build();

        recyclerView = findViewById(R.id.recyler_item);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent() != null){
            categoryId = getIntent().getStringExtra("CategoryId");
        }
        if(!categoryId.isEmpty() && categoryId != null){
            adapter =  new FirebaseRecyclerAdapter<Item, ItemHolder>(
                    options
            ) {
                @Override
                protected void onBindViewHolder(@NonNull ItemHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Item model) {
                    holder.itemName.setText(model.getName());

                    final Item local = model;
                    holder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int pos, boolean isLong) {
                            Intent itemDetails = new Intent(ItemList.this, ItemDetails.class);

                            itemDetails.putExtra("ItemId", adapter.getRef(position).getKey());
                            startActivity(itemDetails);
                        }
                    });
                }
                @NonNull
                @Override
                public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

                    return new ItemHolder(view);
                }

            };
            recyclerView.setAdapter(adapter);
        }
    }
}