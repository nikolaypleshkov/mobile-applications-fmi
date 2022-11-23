package com.example.ecommerceapplication.ui.details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ecommerceapplication.R;
import com.example.ecommerceapplication.data.model.Item;
import com.example.ecommerceapplication.data.model.Order;
import com.example.ecommerceapplication.database.DBActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ItemDetails extends AppCompatActivity {
    TextView item_name, item_price, item_description;
    ImageView item_image;

    CollapsingToolbarLayout collapsingToolbarLayout;

    FloatingActionButton btnAddToCart;

    ElegantNumberButton numberButton;

    String itemId = "";

    FirebaseFirestore db;

    ArrayList<Item> items;
    Item selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        db = FirebaseFirestore.getInstance();
        numberButton = findViewById(R.id.number_button);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        btnAddToCart.setOnClickListener(view -> {
            //TODO: add to local DB
            new DBActivity(getBaseContext()).addItemToCart(
                    new Order(
                            itemId,
                            selectedItem.getName(),
                            numberButton.getNumber(),
                            selectedItem.getPrice()
                    )
            );
            Toast.makeText(ItemDetails.this, selectedItem.getName() + " added to card", Toast.LENGTH_SHORT).show();
        });

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        item_name = findViewById(R.id.item_name);
        item_image = findViewById(R.id.item_image);
        item_price = findViewById(R.id.item_price);
        item_description = findViewById(R.id.item_description);

        if(getIntent() != null){
            itemId = getIntent().getStringExtra("ItemId");
        }
        if(!itemId.isEmpty()){
            loadItemDetails(itemId);
        }

    }

    private void loadItemDetails(String itemId){
        db.collection("item").whereEqualTo("name", itemId).get().addOnCompleteListener(task -> {
            DocumentSnapshot itemList = task.getResult().getDocuments().get(0);

            if(task.isSuccessful()){
                Item item = itemList.toObject(Item.class);

                selectedItem = item;

                Picasso.get().load(selectedItem.getImage()).into(item_image);

                collapsingToolbarLayout.setTitle(selectedItem.getName());

                item_name.setText(selectedItem.getName());
                item_price.setText(selectedItem.getPrice());
                item_description.setText(selectedItem.getDescription());

            }
        }).addOnFailureListener(e -> Toast.makeText(ItemDetails.this, "Something went wrong! Please try again.", Toast.LENGTH_SHORT).show());
    }
}