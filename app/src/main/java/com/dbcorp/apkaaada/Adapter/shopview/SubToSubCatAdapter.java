package com.dbcorp.apkaaada.Adapter.shopview;

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
import com.dbcorp.apkaaada.model.shopview.SubCategory;
import com.dbcorp.apkaaada.model.shopview.SubToSubCategory;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class SubToSubCatAdapter extends RecyclerView.Adapter<SubToSubCatAdapter.MyViewHolder> {

    private final OnClickListener onClickListener;
    Context mContext;

    ArrayList<SubToSubCategory> list;

    int selectValue = 0;

    public SubToSubCatAdapter(ArrayList<SubToSubCategory> list, OnClickListener listener, Context context) {
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
        SubToSubCategory data = list.get(position);
        holder.tvName.setText(data.getName());
        holder.cardView.setOnClickListener(v -> {
            selectValue = position;
            onClickListener.subCatOnClick(data, position, "");
            notifyDataSetChanged();
        });
        if(selectValue==position){
            //   holder.tvName.setTextColor(Color.RED);
            holder.cardView.setBackgroundResource(R.drawable.red_rounded_rect_bg);
            holder.tvName.setTextColor(Color.parseColor("#FFFFFF"));
        }else{
            holder.tvName.setTextColor(Color.BLACK);
            holder.cardView.setBackgroundResource(R.drawable.gray_rounded_bg);
            holder.tvName.setTextColor(Color.parseColor("#000000"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView tvName;
        LinearLayoutCompat cardView;

        MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            cardView = view.findViewById(R.id.cardView);

        }
    }

    public interface OnClickListener {
        void subCatOnClick(SubToSubCategory data, int pos, String type);
    }


}


