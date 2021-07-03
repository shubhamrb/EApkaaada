package com.dbcorp.apkaaada.Adapter.varriant;

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
import com.dbcorp.apkaaada.model.shopview.Product;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class varriantProduct extends RecyclerView.Adapter<varriantProduct.MyViewHolder> {

    private ArrayList<Product> listData;

    private final OnMeneuClickListnser onMenuListClicklistener;
    Context mContext;
    private static String productList;

    public varriantProduct(ArrayList<Product> list, OnMeneuClickListnser onLiveTestClickListener, Context context) {
        this.listData = list;
        this.onMenuListClicklistener = onLiveTestClickListener;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product data = listData.get(position);
        holder.showProduct.setOnClickListener(v -> {
            onMenuListClicklistener.productClick("nj", position);
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView showProduct;

        MyViewHolder(View view) {
            super(view);
            showProduct = view.findViewById(R.id.showProduct);


        }
    }

    public interface OnMeneuClickListnser {
        void productClick(String liveTest, int pos);
    }


}


