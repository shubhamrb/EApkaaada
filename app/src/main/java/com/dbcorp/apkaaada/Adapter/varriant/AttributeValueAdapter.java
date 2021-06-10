package com.dbcorp.apkaaada.Adapter.varriant;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.Varriant.Attribute;
import com.dbcorp.apkaaada.model.Varriant.AttributeValue;
import com.dbcorp.apkaaada.model.shopview.SubCategory;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class AttributeValueAdapter extends RecyclerView.Adapter<AttributeValueAdapter.MyViewHolder> {

    private final OnClickListener onClickListener;
    Context mContext;

    ArrayList<AttributeValue> list;
int selectValue=0;
    public AttributeValueAdapter(ArrayList<AttributeValue> list, OnClickListener listener, Context context) {
        this.list = list;
        this.onClickListener = listener;
        this.mContext = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AttributeValue data = list.get(position);
        holder.tvName.setText(data.getName());

        holder.cardView.setOnClickListener(v -> {
            selectValue=position;

            notifyDataSetChanged();
        });


        if(selectValue==position){
            holder.tvName.setTextColor(Color.RED);
        }else{
            holder.tvName.setTextColor(Color.BLACK);
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView tvName;
        MaterialCardView cardView;

        MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            cardView = view.findViewById(R.id.card_view);

        }
    }

    public interface OnClickListener {
        void onAttributeClick(AttributeValue data, int pos, String type);
    }


}


