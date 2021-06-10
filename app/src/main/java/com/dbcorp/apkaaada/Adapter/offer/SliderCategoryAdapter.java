package com.dbcorp.apkaaada.Adapter.offer;

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
import com.dbcorp.apkaaada.Adapter.HomeItemAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.VendorDetails;
import com.dbcorp.apkaaada.model.home.SliderImage;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

import static com.dbcorp.apkaaada.network.ApiService.OFFER_URL;
import static com.dbcorp.apkaaada.network.ApiService.slider_URL;

public class SliderCategoryAdapter extends RecyclerView.Adapter<SliderCategoryAdapter.MyViewHolder> {

    HomeItemAdapter homeItemAdapter;
    SliderCategoryAdapter listner;
    private String arrItems[];
    private String arrItemslist[]={"a","b","c"};
    private final OnClickListener onClickListener;
    static int  getPosition;
    private int row_index;
    Context mContext;
    int select;

    ArrayList<SliderImage> list;

    public SliderCategoryAdapter(ArrayList<SliderImage> getlist, OnClickListener listener, Context context) {
        this.list = getlist;
        this.onClickListener = listener;
        this.mContext=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offer_shop, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SliderImage data=list.get(position);
        Glide.with(mContext)
                .load(slider_URL+data.getPhoto())
                .into(holder.shapeableImageView);
        holder.frameId.setOnClickListener(v->{
            onClickListener.clickProductCategory(data,position,"");
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

         FrameLayout frameId;
        ShapeableImageView shapeableImageView;
          MyViewHolder(View view) {
            super(view);
              frameId=view.findViewById(R.id.frameId);
              shapeableImageView=view.findViewById(R.id.imageView);
          }
    }
    public interface OnClickListener{
        void clickProductCategory(SliderImage liveTest, int pos, String type);
    }


 }


