package com.example.ecommerceapplication.Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapplication.Listener.ItemClickListener;
import com.example.ecommerceapplication.R;

public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView itemName;
    public ImageView itemImage;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public ItemHolder(@NonNull View itemView) {
        super(itemView);

        itemName = itemView.findViewById(R.id.itemName);
        itemImage = itemView.findViewById(R.id.itemImage);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAbsoluteAdapterPosition(), false);
    }
}
