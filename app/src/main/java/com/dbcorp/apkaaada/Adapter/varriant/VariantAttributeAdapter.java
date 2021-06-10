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
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.Adapter.HomeAdapter;
import com.dbcorp.apkaaada.Adapter.ShopCategory;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.Varriant.Attribute;
import com.dbcorp.apkaaada.model.Varriant.AttributeValue;
import com.dbcorp.apkaaada.model.Varriant.VariantsAttribute;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VariantAttributeAdapter extends RecyclerView.Adapter<VariantAttributeAdapter.MyViewHolder> implements VariantAttributeValueAdapter.OnClickListener {

    private final OnClickListener onClickListener;
    Context mContext;


    ArrayList<VariantsAttribute> list;
    VariantAttributeAdapter listner;
int selectValue=0;
    Map<String,String> selectAttribute;
    VariantAttributeValueAdapter attributeValueAdapter;
    public VariantAttributeAdapter(ArrayList<VariantsAttribute> list,  OnClickListener listener, Context context) {
        this.list = list;
        selectAttribute = new HashMap<>();
        this.onClickListener = listener;
        this.mContext = context;
        listner=this;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.variant_attribute_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VariantsAttribute data = list.get(position);
        holder.tvName.setText(data.getAttributeName());
        attributeValueAdapter = new VariantAttributeValueAdapter(data.getAttributValue(), listner, mContext);
        holder.listAttribute.setAdapter(attributeValueAdapter);

    }



    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public void onClickCard(AttributeValue data, int pos) {

        selectAttribute.put(data.getAttributeName(),data.getName());
        onClickListener.onVariantValue(selectAttribute);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView tvName;
        MaterialCardView cardView;
        RecyclerView listAttribute;

        MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            cardView = view.findViewById(R.id.card_view);
            listAttribute=view.findViewById(R.id.listAttribute);
            listAttribute.setHasFixedSize(true);
            //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            listAttribute.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        }
    }

    public interface OnClickListener {
        void onVariantAttributeClick(VariantsAttribute data, int pos);
        void onVariantValue(Map<String,String> selectAttribute);
    }


}


