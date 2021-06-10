package com.dbcorp.apkaaada.Adapter.usercard;

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
import com.dbcorp.apkaaada.model.card.Coupon;
import com.dbcorp.apkaaada.model.card.UserAddress;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class UserAddressAdapter extends RecyclerView.Adapter<UserAddressAdapter.MyViewHolder> {


    private final OnClickListener onClickListener;
    Context mContext;

    ArrayList<UserAddress> list;

    public UserAddressAdapter(ArrayList<UserAddress> listData, OnClickListener listener, Context context) {
        this.list = listData;
        this.onClickListener = listener;
        this.mContext = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        UserAddress data = list.get(position);
        holder.tvAddress.setText(data.getAddress());
        holder.tvAddType.setText(data.getAddressType().equalsIgnoreCase("1") ? "Home" : "Work");

        holder.card_view.setOnClickListener(v -> {
            onClickListener.clickOnAddress(data, position, "");
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView tvAddress, tvAddType, tvApply;
 MaterialCardView card_view;
        MyViewHolder(View view) {
            super(view);
            tvAddress = view.findViewById(R.id.tvAddress);
            tvAddType=view.findViewById(R.id.tvAddType);
            card_view=view.findViewById(R.id.card_view);
        }
    }

    public interface OnClickListener {
        void clickOnAddress(UserAddress data, int pos, String type);
    }


}


