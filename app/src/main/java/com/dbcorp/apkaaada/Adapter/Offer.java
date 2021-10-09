package com.dbcorp.apkaaada.Adapter;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */
 
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.VendorDetails;
import com.dbcorp.apkaaada.model.home.Category;
import com.dbcorp.apkaaada.model.home.OfferList;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

import static com.dbcorp.apkaaada.network.ApiService.OFFER_URL;

public class Offer extends RecyclerView.Adapter<Offer.MyViewHolder> {

    HomeItemAdapter homeItemAdapter;
    Offer listner;
    private String arrItems[];
    private String arrItemslist[]={"a","b","c"};
    private final OnClickListener onClickListener;
    static int  getPosition;
    private int row_index;
    Context mContext;
    int select;

    ArrayList<VendorDetails> list;

    public Offer(ArrayList<VendorDetails> getlist, OnClickListener listener, Context context) {
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
        VendorDetails data=list.get(position);
        Glide.with(mContext)
                .load(OFFER_URL+data.getPhoto()).placeholder(R.drawable.logo)
                .into(holder.shapeableImageView);

        holder.frameId.setOnClickListener(v->{
            onClickListener.offerClickView(data,position,"");
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
        void offerClickView(VendorDetails liveTest, int pos, String type);
    }


 }


