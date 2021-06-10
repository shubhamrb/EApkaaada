package com.dbcorp.apkaaada.Adapter.order;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.Adapter.service.ServiceAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.ServiceModel;
import com.dbcorp.apkaaada.model.order.OrderModel;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private final OnClickListener onClickListener;
    Context mContext;

    public OrderAdapter(  ArrayList<OrderModel> list,OnClickListener onClickListener,Context mContext) {
        this.onClickListener = onClickListener;
        this.mContext = mContext;
        this.list = list;
    }

    ArrayList<OrderModel> list;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_instraction, parent, false);


        return new ViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderModel data=list.get(position);
       /* holder.tvName.setText(data.getName());
        holder.card_view.setOnClickListener(v->{

            pos=position;
            onClickListener.onClickCard(data);
            notifyDataSetChanged();

        });
        if(pos==position){
            holder.card_view.setBackgroundResource(R.drawable.red_gredient_bg);
            holder.tvName.setTextColor(Color.WHITE);

        }else{
            holder.card_view.setBackgroundResource(R.drawable.white_gredient_bg);
            holder.tvName.setTextColor(Color.BLACK);
        }*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public interface OnClickListener{
        void onClickCard(ServiceModel list);
    }
}
