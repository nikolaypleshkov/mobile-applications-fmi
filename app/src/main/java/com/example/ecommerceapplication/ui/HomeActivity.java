package com.example.ecommerceapplication.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapplication.ItemList;
import com.example.ecommerceapplication.R;
import com.example.ecommerceapplication.data.model.Category;
import com.example.ecommerceapplication.holder.CategoryAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
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
//            Intent cartActivity = new Intent(HomeActivity.this, CartActivity.class);
//            startActivity(cartActivity);
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
        txtFullName.setText("Nikolay Pleshkov");

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadMenu();

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
        Toast.makeText(HomeActivity.this, "Item click", Toast.LENGTH_SHORT).show();

        return false;
    }
}