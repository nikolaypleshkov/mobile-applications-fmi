package com.example.ecommerceapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ecommerceapplication.Database.DBActivity;
import com.example.ecommerceapplication.Entity.Item;
import com.example.ecommerceapplication.Entity.Order;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ItemDetails extends AppCompatActivity {

    TextView itemName, itemPrice, itemDescription;
    ImageView itemImage;

    CollapsingToolbarLayout collapsingToolbarLayout;

    FloatingActionButton cartBtn;

    ElegantNumberButton numberPicker;

    String itemId = "";

    FirebaseDatabase database;
    DatabaseReference itemReference;

    Item currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        database = FirebaseDatabase.getInstance("https://ecommerce-30ed3-default-rtdb.europe-west1.firebasedatabase.app");
        itemReference = database.getReference("Items");

        numberPicker = findViewById(R.id.number_button);
        cartBtn = findViewById(R.id.btnCart);

        cartBtn.setOnClickListener(view -> {
            new DBActivity(getBaseContext()).SaveToCart(new Order(
                    itemId,
                    currentItem.getName(),
                    numberPicker.getNumber(),
                    currentItem.getPrice()
            ));
            Toast.makeText(ItemDetails.this, "Added to Cart", Toast.LENGTH_LONG).show();
        });

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        itemDescription = findViewById(R.id.item_description);
        itemPrice = findViewById(R.id.item_price);
        itemName = findViewById(R.id.itemName);
        itemImage = findViewById(R.id.itemImage);

        if (getIntent() != null){
            itemId = getIntent().getStringExtra("ItemId");
        }
        if (!itemId.isEmpty()){
            itemReference.child(itemId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    currentItem = snapshot.getValue(Item.class);

                    //TODO: set image

                    collapsingToolbarLayout.setTitle(currentItem.getName());

                    itemName.setText(currentItem.getName());
                    itemPrice.setText(currentItem.getPrice());
                    itemDescription.setText(currentItem.getDescription());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}