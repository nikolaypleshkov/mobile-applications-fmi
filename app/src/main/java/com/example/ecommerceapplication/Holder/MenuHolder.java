package com.example.ecommerceapplication.Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapplication.Listener.ItemClickListener;
import com.example.ecommerceapplication.R;

public class MenuHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textView;
    public ImageView imageView;

    private ItemClickListener itemClickListener;

    public MenuHolder(View view){
        super(view);

        textView = view.findViewById(R.id.menu_name);
        imageView = view.findViewById(R.id.menu_image);

        view.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }



    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getBindingAdapterPosition(), false);
    }
}
