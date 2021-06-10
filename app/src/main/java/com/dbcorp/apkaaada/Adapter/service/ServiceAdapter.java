package com.dbcorp.apkaaada.Adapter.service;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */
 
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.ServiceModel;
import com.dbcorp.apkaaada.model.card.Instruction;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {


    private final OnClickListener onClickListener;

    Context mContext;


    ArrayList<ServiceModel> list;

    int pos=8;
    public ServiceAdapter(ArrayList<ServiceModel> list, OnClickListener listener, Context context) {
        this.list = list;
        this.onClickListener = listener;
        this.mContext=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_instraction, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ServiceModel data=list.get(position);
        holder.tvName.setText(data.getName());
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
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {


        LinearLayoutCompat card_view;
        MaterialTextView tvName;
          MyViewHolder(View view) {
            super(view);
              card_view=view.findViewById(R.id.card_view);
              tvName=view.findViewById(R.id.tvName);
          }
    }
    public interface OnClickListener{
        void onClickCard(ServiceModel list);
    }


 }


