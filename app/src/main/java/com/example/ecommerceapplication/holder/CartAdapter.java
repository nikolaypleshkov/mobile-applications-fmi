package com.example.ecommerceapplication.holder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapplication.R;
import com.example.ecommerceapplication.data.model.Item;
import com.example.ecommerceapplication.data.model.Order;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{

    public interface ItemClickListener {
        void onClick(Order order);

    }

    private final List<Order> orderArrayList;
    private Context context;

    private CartAdapter.ItemClickListener itemClickListener;

    public CartAdapter(ArrayList<Order> orderArrayList, Context context, ItemClickListener itemClickListener) {
        this.orderArrayList = orderArrayList;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(orderArrayList, position,  itemClickListener);
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txt_cart_name, txt_cart_price;
        public Button removeItem;
        public final View view;

        public ViewHolder(View view){
            super(view);
            this.view = view;
            txt_cart_name = view.findViewById(R.id.cart_item_name);
            txt_cart_price = view.findViewById(R.id.cart_item_price);
            removeItem = view.findViewById(R.id.removeItem);

        }
        @SuppressLint("SetTextI18n")
        public void bind(final List<Order> orderDetails, int position, final CartAdapter.ItemClickListener listener){
            int price = (Integer.parseInt(orderDetails.get(position).getPrice()))*(Integer.parseInt(orderDetails.get(position).getQuantity()));
            txt_cart_price.setText("€" + price);
            txt_cart_name.setText(orderDetails.get(position).getItemName());
            removeItem.setOnClickListener(v -> listener.onClick(orderDetails.get(position)));

        }

    }
}
