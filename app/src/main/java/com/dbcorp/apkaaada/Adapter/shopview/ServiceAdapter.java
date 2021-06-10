package com.dbcorp.apkaaada.Adapter.shopview;

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
import com.dbcorp.apkaaada.model.home.OfferList;
import com.dbcorp.apkaaada.model.shopview.VendorService;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import static com.dbcorp.apkaaada.network.ApiService.OFFER_URL;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {



    Context mContext;


    ArrayList<VendorService> list;

    public ServiceAdapter(ArrayList<VendorService> listData ,Context context) {
        this.list = listData;

        this.mContext=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item_cat, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VendorService data=list.get(position);
        holder.tvName.setText(data.getServiceName());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

          MaterialTextView tvName;
          MyViewHolder(View view) {
            super(view);
              tvName=view.findViewById(R.id.tvName);

          }
    }



 }


