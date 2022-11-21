package com.example.ecommerceapplication;

import android.os.Bundle;
import com.example.ecommerceapplication.databinding.ActivityDrawerBinding;
import androidx.appcompat.app.AppCompatActivity;


public class DrawerActivity extends AppCompatActivity {

    private ActivityDrawerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}