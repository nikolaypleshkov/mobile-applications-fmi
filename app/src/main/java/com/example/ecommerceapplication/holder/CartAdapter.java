package com.example.ecommerceapplication.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapplication.R;
import com.example.ecommerceapplication.data.model.Category;
import com.example.ecommerceapplication.data.model.Item;
import com.example.ecommerceapplication.data.model.Order;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{

    private final List<Order> orderArrayList;
    private Context context;

    public CartAdapter(ArrayList<Order> orderArrayList, Context context) {
        this.orderArrayList = orderArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(orderArrayList, position);
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txt_cart_name, txt_cart_price, txt_cart_count;
        public final View view;

        public ViewHolder(View view){
            super(view);
            this.view = view;
            txt_cart_name = view.findViewById(R.id.cart_item_name);
            txt_cart_price = view.findViewById(R.id.cart_item_price);
            txt_cart_count = view.findViewById(R.id.cart_item_count);

        }
        public void bind(final List<Order> orderDetails, int position){
            txt_cart_count.setText(orderDetails.get(position).getQuantity());
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.GERMAN);
            int price = (Integer.parseInt(orderDetails.get(position).getPrice()))*(Integer.parseInt(orderDetails.get(position).getQuantity()));
            txt_cart_price.setText(numberFormat.format(price));
            txt_cart_name.setText(orderDetails.get(position).getItemName());

        }

    }
}
