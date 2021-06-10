package com.dbcorp.apkaaada.Adapter.usercard;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */
 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.card.CardProduct;
import com.dbcorp.apkaaada.model.shopview.Product;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class OrderPriceAdapter extends RecyclerView.Adapter<OrderPriceAdapter.MyViewHolder> {


    Context mContext;

    ProductVariantAdapter shopProduct;

    ArrayList<CardProduct> cardProducts;
    ArrayList<Product> listProduct;

    public OrderPriceAdapter(ArrayList<CardProduct> listData, Context context) {
        this.cardProducts = listData;
        this.mContext=context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_final_oder, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CardProduct cardProduct=cardProducts.get(position);

        holder.tvVendorName.setText(cardProduct.getVendorName());
        shopProduct = new ProductVariantAdapter(cardProduct.getProductList(), mContext);
        holder.itemList.setAdapter(shopProduct);

    }

    @Override
    public int getItemCount() {
        return cardProducts.size();
    }




    class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView tvVendorName;
        RecyclerView itemList;
          MyViewHolder(View view) {
            super(view);
              tvVendorName=view.findViewById(R.id.tvVendorName);

              itemList=view.findViewById(R.id.itemList);

              itemList.setHasFixedSize(true);
              //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
              itemList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

          }
    }



 }


