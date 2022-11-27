package com.example.ecommerceapplication.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapplication.R;
import com.example.ecommerceapplication.data.model.Order;
import com.example.ecommerceapplication.data.model.SubmitOrder;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private final List<SubmitOrder> submitOrders;
    private final Context context;

    public OrderAdapter(List<SubmitOrder> submitOrders, Context context) {
        this.submitOrders = submitOrders;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.order_status_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(submitOrders, position);
    }

    @Override
    public int getItemCount() {
        return submitOrders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtOrderNumber, txtOrderStatus, getTxtOrderAddress;
        public final View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            txtOrderNumber = (TextView)itemView.findViewById(R.id.order_number);
            txtOrderStatus = (TextView)itemView.findViewById(R.id.order_status);
            getTxtOrderAddress = (TextView)itemView.findViewById(R.id.order_address);

        }
        public void bind(final List<SubmitOrder> submitOrders, int position){
            txtOrderStatus.setText(getOrderStatus(submitOrders.get(position).getStatus()));
            getTxtOrderAddress.setText(submitOrders.get(position).getAddress());
            txtOrderNumber.setText(submitOrders.get(position).getNumber());
        }

        private String getOrderStatus(String statusCode){
            switch (statusCode){
                case "0": return "Order Placed";
                case "1": return "Ready for dispatch";
                case "2": return "Dispatched";
                case "3": return "Delivered";
                default: return "Order Failed";
            }
        }
    }
}
