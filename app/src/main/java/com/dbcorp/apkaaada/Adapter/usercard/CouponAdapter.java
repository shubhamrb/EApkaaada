package com.dbcorp.apkaaada.Adapter.usercard;

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
import com.dbcorp.apkaaada.model.card.Coupon;
import com.dbcorp.apkaaada.model.home.OfferList;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import static com.dbcorp.apkaaada.network.ApiService.OFFER_URL;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.MyViewHolder> {


    private final OnClickListener onClickListener;
    Context mContext;

    ArrayList<Coupon> list;

    public CouponAdapter(ArrayList<Coupon> listData, OnClickListener listener, Context context) {
        this.list = listData;
        this.onClickListener = listener;
        this.mContext = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cupon, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Coupon data = list.get(position);
        holder.tvDis.setText(data.getDescription());
        holder.tvCoupon.setText(data.getCouponCode());
        holder.tvApply.setOnClickListener(v -> {
            onClickListener.clickCoupon(data, position, "");
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView tvDis, tvCoupon, tvApply;

        MyViewHolder(View view) {
            super(view);
            tvDis = view.findViewById(R.id.tvDis);
            tvApply = view.findViewById(R.id.tvApply);
            tvCoupon = view.findViewById(R.id.tvCoupon);
        }
    }

    public interface OnClickListener {
        void clickCoupon(Coupon data, int pos, String type);
    }


}


