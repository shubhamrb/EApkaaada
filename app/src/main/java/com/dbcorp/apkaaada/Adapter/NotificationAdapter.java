package com.dbcorp.apkaaada.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.NotificationModel;
import com.dbcorp.apkaaada.model.OfferModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {


    private final OnClickListener onClickListener;

    Context mContext;


    ArrayList<NotificationModel> list;

    int pos=8;
    public NotificationAdapter(ArrayList<NotificationModel> list, OnClickListener listener, Context context) {
        this.list = list;
        this.onClickListener = listener;
        this.mContext=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item_view, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NotificationModel data=list.get(position);
        holder.tvName.setText(data.getRemark());
        holder.tvDes.setText(data.getRemark());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {


        MaterialCardView card_view;
        MaterialTextView tvName,tvDes;
        MyViewHolder(View view) {
            super(view);
            card_view=view.findViewById(R.id.card_view);
            tvName=view.findViewById(R.id.tvName);
            tvDes=view.findViewById(R.id.tvDes);
        }
    }
    public interface OnClickListener{
        void onClickCard(OfferModel list);
    }


}


