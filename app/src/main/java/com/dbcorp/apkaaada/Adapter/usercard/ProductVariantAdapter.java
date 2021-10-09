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
import com.dbcorp.apkaaada.model.shopview.Product;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ProductVariantAdapter extends RecyclerView.Adapter<ProductVariantAdapter.MyViewHolder> {



    Context mContext;
    ArrayList<Product> list;

    public ProductVariantAdapter(ArrayList<Product> listData, Context context) {
        this.list = listData;
         this.mContext=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_final_oder_product_varriant, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product data=list.get(position);
        int cartQuantity= Integer.parseInt(data.getCartQuantity());
        int productPrice= Integer.parseInt(data.getPrice());
        int totalPrice=cartQuantity*productPrice;
        holder.tvPrice.setText("₹ "+totalPrice);
        holder.allOrderItem.setText(data.getName()+" | "+data.getValueName()+"|" +cartQuantity+"X"+productPrice);
        holder.gstValue.setText(data.getGst_name());



        \\pal
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView tvPrice,allOrderItem;
        MaterialTextView gstPercentage,gstValue,grossAmount;
          MyViewHolder(View view) {
            super(view);
              tvPrice=view.findViewById(R.id.tvPrice);
              gstValue=view.findViewById(R.id.gstValue);
              grossAmount=view.findViewById(R.id.grossAmount);

              gstPercentage=view.findViewById(R.id.gstPercentage);
              allOrderItem=view.findViewById(R.id.allOrderItem);
          }
    }


    void gstPercentagePrice(){

    }


 }


