package com.dbcorp.apkaaada.Adapter;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.card.UserAddress;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.MyViewHolder> {

    private ArrayList<UserAddress> list;
    private final OnMeneuClickListnser onMenuListClicklistener;
    static int  getPosition;
    private int row_index;
    Context mContext;
    int select;
String type;
    public AddressListAdapter(String type,ArrayList<UserAddress> getList, OnMeneuClickListnser onLiveTestClickListener, Context context) {
        this.list = getList;
        this.type=type;
        this.onMenuListClicklistener = onLiveTestClickListener;

        this.mContext=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;


        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_address, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserAddress data=list.get(position);

if(type.equalsIgnoreCase("green")){
    holder.tvAddress.setVisibility(View.GONE);
    holder.tvAddresstwo.setVisibility(View.VISIBLE);
    holder.tvAddresstwo.setTextColor(Color.parseColor("#3c8b00"));
    holder.tvAddresstwo.setText(data.getAddress());
}else{
    holder.tvAddress.setVisibility(View.VISIBLE);
    holder.tvAddresstwo.setVisibility(View.GONE);
    holder.tvAddress.setTextColor(Color.parseColor("#F41F1F"));
    holder.tvAddress.setText(data.getAddress());
}



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView  tvAddress,tvAddresstwo;
        MyViewHolder(View view) {
            super(view);

            tvAddresstwo=view.findViewById(R.id.tvAddresstwo);
            tvAddress=view.findViewById(R.id.tvAddress);




        }
    }
    public interface OnMeneuClickListnser{
        void onOptionClick(String liveTest,int pos);
    }


 }


