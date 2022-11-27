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
import com.example.ecommerceapplication.data.model.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{


    public interface ItemClickListener {
        void onClick(Item item);

    }

    private final ArrayList<Item> itemArrayList;
    private Context context;
    private ItemClickListener itemClickListener;

    public ItemAdapter(ArrayList<Item> itemArrayList, Context context, ItemClickListener itemClickListener) {
        this.itemArrayList = itemArrayList;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(itemArrayList.get(position), itemClickListener);

    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView item_name;
        private final TextView item_price;
        private final ImageView item_image;
        private final View view;

        public ViewHolder(View view){
            super(view);
            this.view = view;
            item_name = view.findViewById(R.id.item_name);
            item_image = view.findViewById(R.id.item_image);
            item_price = view.findViewById(R.id.item_price);
        }

        public void bind(final Item item, final ItemClickListener listener){
            item_name.setText(item.getName());
            item_price.setText("€" + item.getPrice());
            Picasso.get().load(item.getImage()).into(item_image);
            view.setOnClickListener(v -> listener.onClick(item));
        }
    }
}
