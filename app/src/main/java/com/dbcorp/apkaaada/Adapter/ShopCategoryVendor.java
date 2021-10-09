package com.dbcorp.apkaaada.Adapter;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */
 
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.HomeShopListCategory;
import com.dbcorp.apkaaada.model.VendorDetails;
import com.dbcorp.apkaaada.model.home.VendorData;
import com.dbcorp.apkaaada.ui.auth.fragments.shop.SingleShopDetails;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import static com.dbcorp.apkaaada.network.ApiService.OFFER_URL;

public class ShopCategoryVendor extends RecyclerView.Adapter<ShopCategoryVendor.MyViewHolder> implements  ShopHome.OnClickListener {

    HomeItemAdapter homeItemAdapter;
    ShopCategoryVendor listner;
    private String arrItems[];
    private String arrItemslist[]={"a","b","c"};
    private final OnClickListener onClickListener;
    static int  getPosition;
    private int row_index;
    Context mContext;
    int select;
    ShopHome shopHome;
    ArrayList<HomeShopListCategory> list;

    public ShopCategoryVendor(String type,ArrayList<HomeShopListCategory> getlist, OnClickListener listener, Context context) {
        this.list = getlist;
        this.onClickListener = listener;
        this.mContext=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_shop_list, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HomeShopListCategory data=list.get(position);
        if(data.getListData().size()>0){
            holder.tvCatName.setVisibility(View.VISIBLE);
            holder.tvCatName.setText(data.getCatName());
            shopHome = new ShopHome("home",data.getListData(), ShopCategoryVendor.this::offerClick, mContext);
            holder.listItem.setAdapter(shopHome);
        }else{
            holder.tvCatName.setVisibility(View.GONE);
            holder.tvCatName.setText("");

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void offerClick(VendorData data, String type, String status) {
        if(type.equalsIgnoreCase("1")){
            Intent mv =new Intent(mContext, SingleShopDetails.class);
            mv.putExtra("MyData", data);
            mContext.startActivity(mv);

        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayoutCompat viewClick;
        MaterialTextView tvCatName;
        RecyclerView listItem;
          MyViewHolder(View view) {
            super(view);
              viewClick=view.findViewById(R.id.viewClick);
              listItem=view.findViewById(R.id.listItem);
              tvCatName=view.findViewById(R.id.tvCatName);
              listItem.setHasFixedSize(true);
              listItem.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

          }
    }
    public interface OnClickListener{
        void clickShopCat(HomeShopListCategory liveTest, int pos, String type);
    }


 }


