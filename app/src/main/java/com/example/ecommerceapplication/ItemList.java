package com.example.ecommerceapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ecommerceapplication.data.model.Item;
import com.example.ecommerceapplication.holder.CategoryAdapter;
import com.example.ecommerceapplication.holder.ItemAdapter;
import com.example.ecommerceapplication.ui.HomeActivity;
import com.example.ecommerceapplication.ui.details.ItemDetails;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ItemList extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseFirestore db;

    ArrayList<Item> items;

    String categoryId;

    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        db = FirebaseFirestore.getInstance();

        items = new ArrayList<>();
        itemAdapter = new ItemAdapter(items,this, null);


        recyclerView = findViewById(R.id.recycler_item);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent() != null){
            categoryId = getIntent().getStringExtra("CategoryId");
        } if(!categoryId.isEmpty() && categoryId != null){
            loadItemList(categoryId);
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadItemList(String categoryId){
        db.collection("item").whereEqualTo("menuID", categoryId).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                List<DocumentSnapshot> itemList = task.getResult().getDocuments();

                for(DocumentSnapshot documentSnapshot : itemList){
                    Item item = documentSnapshot.toObject(Item.class);

                    items.add(item);
                }

                recyclerView.setAdapter(new ItemAdapter(items, this, item -> {
                    Intent itemDetails = new Intent(ItemList.this, ItemDetails.class);
                    itemDetails.putExtra("ItemId", item.getName());

                    startActivity(itemDetails);
                }));

                itemAdapter.notifyDataSetChanged();


            } else {
                Toast.makeText(ItemList.this, "No data found in Database", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(ItemList.this, "Fail to get the data.", Toast.LENGTH_SHORT).show());
    }
}