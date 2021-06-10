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
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.Varriant.Attribute;
import com.dbcorp.apkaaada.model.Varriant.AttributeValue;
import com.dbcorp.apkaaada.model.shopview.Product;
import com.dbcorp.apkaaada.model.shopview.SubCategory;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class AttributeAdapter extends RecyclerView.Adapter<AttributeAdapter.MyViewHolder> implements AttributeValueAdapter.OnClickListener {

    private final OnClickListener onClickListener;
    Context mContext;

    ArrayList<Attribute> list;
    ArrayList<AttributeValue> attributesValue;
    ArrayList<AttributeValue> getattributesValue;
    AttributeAdapter listner;
int selectValue=0;
    AttributeValueAdapter attributeValueAdapter;
    public AttributeAdapter(ArrayList<Attribute> list,ArrayList<AttributeValue> attributesValue, OnClickListener listener, Context context) {
        this.list = list;
        this.attributesValue=attributesValue;
        this.getattributesValue=attributesValue;
        this.onClickListener = listener;
        this.mContext = context;
         listner=this;

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
        Attribute data = list.get(position);
        holder.tvName.setText(data.getName());

        holder.cardView.setOnClickListener(v -> {
            selectValue=position;
onClickListener.onAttributeClick(data,position);
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



    @Override
    public void onAttributeClick(AttributeValue data, int pos, String type) {

    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView tvName;
        MaterialCardView cardView;
        RecyclerView attributeList;

        MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            cardView = view.findViewById(R.id.card_view);
//            attributeList=view.findViewById(R.id.attributeList);
//            attributeList.setHasFixedSize(true);
//            //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
//            attributeList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));


        }
    }

    public interface OnClickListener {
        void onAttributeClick(Attribute data, int pos);
    }


}


