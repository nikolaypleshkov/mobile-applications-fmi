package com.example.ecommerceapplication.Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapplication.R;

public class CartHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView cartName, cartPrice;
    public ImageView cartCount;

    public CartHolder(View view){
        super(view);
        cartName = view.findViewById(R.id.cart_name);
        cartPrice = view.findViewById(R.id.cart_price);
        cartCount = view.findViewById(R.id.cart_count);
    }

    @Override
    public void onClick(View view) {

    }
}
