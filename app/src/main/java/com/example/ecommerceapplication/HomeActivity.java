package com.example.ecommerceapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.ecommerceapplication.Entity.Category;
import com.example.ecommerceapplication.Holder.MenuHolder;
import com.example.ecommerceapplication.Listener.ItemClickListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private FirebaseAuth firebaseAuth;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference categoryReference;

    TextView userDisplayName;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category, MenuHolder> adapter;
    Query query;
    FirebaseRecyclerOptions<Category> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        //TODO: why is crashing :D?
//        setSupportActionBar(toolbar);

        firebaseDatabase = FirebaseDatabase.getInstance();
        categoryReference = firebaseDatabase.getReference("category");
        firebaseAuth = FirebaseAuth.getInstance();
        query = FirebaseDatabase.getInstance().getReference("Category");
        options = new FirebaseRecyclerOptions.Builder<Category>().setQuery(query, Category.class).build();

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
        userDisplayName = navbarView.findViewById(R.id.userDisplayName);

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadMenu();
    }

    private void  loadMenu(){
        adapter = new FirebaseRecyclerAdapter<Category, MenuHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull MenuHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Category model) {
                holder.textView.setText(model.getName());

                final Category clickitem = model;

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int pos, boolean isLong) {
                        Intent itemList = new Intent(HomeActivity.this, ItemList.class);
                        itemList.putExtra("CategoryId", adapter.getRef(position).getKey());

                        startActivity(itemList);
                    }
                });
            }

            @NonNull
            @Override
            public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);

                return new MenuHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
