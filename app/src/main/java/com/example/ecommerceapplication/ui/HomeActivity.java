package com.example.ecommerceapplication.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapplication.ui.itemList.ItemList;
import com.example.ecommerceapplication.MainActivity;
import com.example.ecommerceapplication.R;
import com.example.ecommerceapplication.common.CurrentAuthUser;
import com.example.ecommerceapplication.data.model.Category;
import com.example.ecommerceapplication.holder.CategoryAdapter;
import com.example.ecommerceapplication.ui.cart.CartActivity;
import com.example.ecommerceapplication.ui.orderStatus.OrderStatusActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView txtFullName;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;

    FirebaseFirestore db;

    ArrayList<Category> categoiries;

    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = FirebaseFirestore.getInstance();

        categoiries = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(categoiries,this, null);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingBtn);

        floatingActionButton.setOnClickListener(view -> {
            Intent cartActivity = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(cartActivity);
        });

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggleDrawer = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggleDrawer);
        toggleDrawer.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navbarView = navigationView.getHeaderView(0);
        txtFullName = navbarView.findViewById(R.id.txtFullName);
        txtFullName.setText(CurrentAuthUser.customer.getName());

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadMenu();
        SMSPermission();
    }

    private void SMSPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        0);
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadMenu(){
        db.collection("category").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                List<DocumentSnapshot> categoryList = task.getResult().getDocuments();

                for(DocumentSnapshot documentSnapshot : categoryList) {
                    Category category = documentSnapshot.toObject(Category.class);

                    categoiries.add(category);

                }

                recyclerView.setAdapter(new CategoryAdapter(categoiries,this, category ->{
                    Intent itemList = new Intent(HomeActivity.this, ItemList.class);

                    itemList.putExtra("CategoryId", category.getName());
                    startActivity(itemList);
                }));

                categoryAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(HomeActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(HomeActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.nav_cart){
            Intent cartIntent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(cartIntent);
        } else if(id == R.id.nav_orders){
            Intent orderIntent = new Intent(HomeActivity.this,  OrderStatusActivity.class);
            startActivity(orderIntent);
        } else if(id == R.id.nav_log_out){
            Intent mainIntent = new Intent(HomeActivity.this, MainActivity.class);

            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainIntent);

            FirebaseAuth.getInstance().signOut();
        }

        return false;
    }
}