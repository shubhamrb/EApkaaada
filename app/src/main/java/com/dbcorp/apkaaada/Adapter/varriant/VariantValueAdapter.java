package com.dbcorp.apkaaada.Adapter.varriant;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.Varriant.AttributeValue;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class VariantValueAdapter extends RecyclerView.Adapter<VariantValueAdapter.MyViewHolder> {

    private ArrayList<String> arrItems;
    private final OnMeneuClickListnser onMenuListClicklistener;
    static int  getPosition;
    private int row_index;
    Context mContext;
    int selectValue;

    public VariantValueAdapter(ArrayList<String> listArray, OnMeneuClickListnser onLiveTestClickListener, Context context) {
        this.arrItems = listArray;
        this.onMenuListClicklistener = onLiveTestClickListener;

        this.mContext=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;


        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.variant_attribute_value_layout, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String name = arrItems.get(position);
        holder.tvName.setText(name);

        holder.cardView.setOnClickListener(v->{
            selectValue=position;
            notifyDataSetChanged();
            onMenuListClicklistener.onVariantValue(name,position);
        });

        if(selectValue==position){

            holder.cardView.setBackgroundResource(R.drawable.red_rounded_rect_bg);
            holder.tvName.setTextColor(Color.parseColor("#FFFFFF"));

        }else{

            holder.cardView.setBackgroundResource(R.drawable.gray_rounded_bg);
            holder.tvName.setTextColor(Color.parseColor("#000000"));
        }


    }

    @Override
    public int getItemCount() {
        return arrItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView tvName;
        LinearLayoutCompat cardView;
        RecyclerView attributeList;

        MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            cardView=view.findViewById(R.id.cardView);


        }
    }
    public interface OnMeneuClickListnser{
        void onVariantValue(String liveTest,int pos);
    }


 }


