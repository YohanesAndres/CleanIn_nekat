package com.Nekat.CleanIn.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nekat.CleanIn.API.Model.APIJson;
import com.Nekat.CleanIn.API.Model.Order;
import com.Nekat.CleanIn.ItemClickListener;
import com.Nekat.CleanIn.R;

import java.util.ArrayList;
import java.util.List;

public class ItemOrderAdapter extends RecyclerView.Adapter<ItemOrderAdapter.ListViewHolder> {
    private APIJson<List<Order>> order;
    private ItemClickListener<Order> itemClickListener;


    public ItemOrderAdapter(ItemClickListener<Order> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setItemOrderAdapter(APIJson<List<Order>> order){
        this.order = order;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Order result = order.getData().get(position);
        holder.tvOrderId.setText("Order ID: "+result.getId());
        switch (result.getStatus()){
            case "1":
                holder.tvKeterangan.setText("New In");
                break;
            case "2":
                holder.tvKeterangan.setText("On Process");
                break;
            case "3":
                holder.tvKeterangan.setText("Order finished");
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.itemClick(order.getData().get(position), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return order.getData().size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvOrderId;
        TextView tvKeterangan;

        ListViewHolder(View itemview) {
            super(itemview);
            tvOrderId = itemview.findViewById(R.id.tv_orderid);
            tvKeterangan = itemview.findViewById(R.id.tv_keterangan);
            imgPhoto = itemview.findViewById(R.id.iv_itemorder);
        }
    }
}
