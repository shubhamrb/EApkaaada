package com.dbcorp.apkaaada.Adapter.myorder;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class OrderInstruction extends RecyclerView.Adapter<OrderInstruction.MyViewHolder> {


    ArrayList<String> listData;
    static int  getPosition;
    private int row_index;
    Context mContext;
    int select;

    public OrderInstruction(ArrayList<String> list,  Context context) {
        this.listData = list;
        this.mContext=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;


        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.instruction_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

holder.tvName.setText(listData.get(position));



    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView  tvName;
        MaterialCardView cardView;
         MyViewHolder(View view) {
            super(view);
             tvName=view.findViewById(R.id.tvName);

             cardView=view.findViewById(R.id.cardView);
         }
    }



 }


