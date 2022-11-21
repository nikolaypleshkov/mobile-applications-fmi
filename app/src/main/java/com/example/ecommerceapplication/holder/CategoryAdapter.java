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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    public interface ItemClickListener {
        void onClick(Category category);

    }

    private final ArrayList<Category> categoryArrayList;
    private Context context;
    private ItemClickListener itemClickListener;

    public CategoryAdapter(ArrayList<Category> categoryArrayList, Context context, ItemClickListener itemClickListener){
        this.categoryArrayList = categoryArrayList;
        this.itemClickListener = itemClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(categoryArrayList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textMenuName;
        private final ImageView imageView;
        private final View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            textMenuName = view.findViewById(R.id.menu_name);
            imageView = view.findViewById(R.id.menu_image);
        }

        public void bind(final Category category, final ItemClickListener listener){
            textMenuName.setText(category.getName());
            Picasso.get().load(category.getImage()).into(imageView);
            view.setOnClickListener(v -> listener.onClick(category));

        }
    }
}


