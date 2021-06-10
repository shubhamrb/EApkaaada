package com.dbcorp.apkaaada.Adapter.offer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.OfferModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import static com.dbcorp.apkaaada.network.ApiService.BASE_IMG_CAT_URL;
import static com.dbcorp.apkaaada.network.ApiService.OFFER_URL;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {


    private final OnClickListener onClickListener;

    Context mContext;


    ArrayList<OfferModel> list;

    int pos=8;
    public OfferAdapter(ArrayList<OfferModel> list, OnClickListener listener, Context context) {
        this.list = list;
        this.onClickListener = listener;
        this.mContext=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item_view, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OfferModel data=list.get(position);
        holder.tvName.setText(data.getTitle());
        holder.tvDes.setText(data.getDescription());
        holder.submit_btn.setOnClickListener(v->{
            onClickListener.onClickCard(data);
        });

        Glide.with(mContext)
                .load(OFFER_URL+data.getPhoto())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {


        LinearLayoutCompat card_view;
        MaterialTextView tvName,tvDes,submit_btn;
        ShapeableImageView imageView;
        MyViewHolder(View view) {
            super(view);
            card_view=view.findViewById(R.id.card_view);
            tvName=view.findViewById(R.id.tvName);
            tvDes=view.findViewById(R.id.tvDes);
            imageView=view.findViewById(R.id.imageView);
            submit_btn=view.findViewById(R.id.submit_btn);
        }
    }
    public interface OnClickListener{
        void onClickCard(OfferModel list);
    }


}


