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
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.card.CardProduct;
import com.dbcorp.apkaaada.model.shopview.Product;
import com.google.android.material.textview.MaterialTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderPriceAdapter extends RecyclerView.Adapter<OrderPriceAdapter.MyViewHolder> {


    Context mContext;

    ProductVariantAdapter shopProduct;

    ArrayList<CardProduct> cardProducts;
    ArrayList<Product> listProduct;
    double lati,longi;
    public OrderPriceAdapter(double lati,double longi,ArrayList<CardProduct> listData, Context context) {
        this.cardProducts = listData;
        this.lati=lati;
        this.longi=longi;
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

        holder.tvVendorName.setText(cardProduct.getShop_name());
        double kmDist= Util.distance(lati,longi, Double.parseDouble(cardProduct.getLat()), Double.parseDouble(cardProduct.getLng()),"k");
        holder.tvKm.setText(String.format("%s km",kmDist));
        int kmDistInt = (int)kmDist;
        int charge=kmDistInt*Integer.parseInt(cardProduct.getDelivery_charge_per_km());
        DecimalFormat formater = new DecimalFormat("##.#");
        String km= formater.format(kmDist);
        holder.tvKm.setText(String.format("%s km",km));
        holder.tvCharge.setText(String.format("₹ %s ",charge));



        holder.tvVendProductPrice.setText(String.format("Total Price Of This Shop : ₹ %s ",cardProduct.getTotalProductPrice()));

        if(cardProduct.getDiscountPrice().equalsIgnoreCase("0")){
            holder.tvVendDiscountPrice.setVisibility(View.GONE);
        }else {
            holder.tvVendDiscountPrice.setVisibility(View.VISIBLE);
            holder.tvVendDiscountPrice.setText(String.format("Discount Price Of This Shop : ₹ %s ",cardProduct.getDiscountPrice()));

        }

        shopProduct = new ProductVariantAdapter(cardProduct.getProductList(), mContext);
        holder.itemList.setAdapter(shopProduct);

    }

    @Override
    public int getItemCount() {
        return cardProducts.size();
    }




    class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView tvVendorName,tvVendDiscountPrice,tvCharge,tvKm,tvVendProductPrice;
        RecyclerView itemList;
          MyViewHolder(View view) {
            super(view);
              tvVendorName=view.findViewById(R.id.tvVendorName);
              tvKm=view.findViewById(R.id.tvKm);
              tvCharge=view.findViewById(R.id.tvCharge);
              tvVendDiscountPrice=view.findViewById(R.id.tvVendDiscountPrice);
              itemList=view.findViewById(R.id.itemList);
              tvVendProductPrice=view.findViewById(R.id.tvVendProductPrice);
              itemList.setHasFixedSize(true);
              //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
              itemList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

          }
    }



 }


