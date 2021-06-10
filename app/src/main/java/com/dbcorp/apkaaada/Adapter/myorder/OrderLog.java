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
import com.dbcorp.apkaaada.model.order.InstructionLog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class OrderLog extends RecyclerView.Adapter<OrderLog.MyViewHolder> {


    ArrayList<InstructionLog> listData;
    static int  getPosition;
    private int row_index;
    Context mContext;
    int select;

    public OrderLog(ArrayList<InstructionLog> list, Context context) {
        this.listData = list;

        this.mContext=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;


        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.instraction_log_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        InstructionLog data=listData.get(position);

holder.remarkBy.setText(data.getCreatedBy());
        holder.remark.setText(data.getRemark());
        holder.tvpProduct.setText(data.getProductName());


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView  remarkBy,tvpProduct,remark ;
        MaterialCardView cardView;
         MyViewHolder(View view) {
            super(view);
             remarkBy=view.findViewById(R.id.remarkBy);
             tvpProduct=view.findViewById(R.id.tvpProduct);
             remark=view.findViewById(R.id.remark);
             cardView=view.findViewById(R.id.cardView);
         }
    }



 }


