package com.dbcorp.apkaaada.Adapter;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */
 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.HomeShopListCategory;
import com.dbcorp.apkaaada.model.VendorDetails;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import static com.dbcorp.apkaaada.network.ApiService.OFFER_URL;

public class ShopCategoryVendor extends RecyclerView.Adapter<ShopCategoryVendor.MyViewHolder> {

    HomeItemAdapter homeItemAdapter;
    ShopCategoryVendor listner;
    private String arrItems[];
    private String arrItemslist[]={"a","b","c"};
    private final OnClickListener onClickListener;
    static int  getPosition;
    private int row_index;
    Context mContext;
    int select;

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
holder.tvCatName.setText(data.getCatName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {


        MaterialTextView tvCatName;
          MyViewHolder(View view) {
            super(view);

              tvCatName=view.findViewById(R.id.tvCatName);
          }
    }
    public interface OnClickListener{
        void clickShopCat(HomeShopListCategory liveTest, int pos, String type);
    }


 }


