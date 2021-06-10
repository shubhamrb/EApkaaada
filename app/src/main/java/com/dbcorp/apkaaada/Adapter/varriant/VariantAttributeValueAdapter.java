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
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.Varriant.Attribute;
import com.dbcorp.apkaaada.model.Varriant.AttributeValue;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class VariantAttributeValueAdapter extends RecyclerView.Adapter<VariantAttributeValueAdapter.MyViewHolder> implements AttributeValueAdapter.OnClickListener {

    private final OnClickListener onClickListener;
    Context mContext;

    ArrayList<AttributeValue> list;

    int selectValue=10000;
    VariantAttributeValueAdapter listner;

    public VariantAttributeValueAdapter(ArrayList<AttributeValue> list,OnClickListener listener, Context context) {
        this.list = list;

        this.onClickListener = listener;
        this.mContext = context;
         listner=this;

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
        AttributeValue data = list.get(position);
        holder.tvName.setText(data.getName());

        holder.cardView.setOnClickListener(v->{

            selectValue=position;


            if(selectValue==position){
                data.setActive("0");
            }else{
                data.setActive("1");
            }

            notifyDataSetChanged();
onClickListener.onClickCard(data,position);
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
        return list.size();
    }



    @Override
    public void onAttributeClick(AttributeValue data, int pos, String type) {

    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView tvName;
        LinearLayoutCompat cardView;
        RecyclerView attributeList;

        MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            cardView=view.findViewById(R.id.cardView);
//            attributeList=view.findViewById(R.id.attributeList);
//            attributeList.setHasFixedSize(true);
//            //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
//            attributeList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));


        }
    }

    public interface OnClickListener {
        void onClickCard(AttributeValue data, int pos);
    }


}


