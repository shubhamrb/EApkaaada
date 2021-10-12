package com.dbcorp.apkaaada.Adapter;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */

import static com.dbcorp.apkaaada.network.ApiService.VENDOR_SHOP_IMG_URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.VendorDetails;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.like.LikeButton;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class NearByShopAdapter extends RecyclerView.Adapter<NearByShopAdapter.MyViewHolder> {

    HomeItemAdapter homeItemAdapter;
    NearByShopAdapter listner;
    private String arrItems[];
    private String arrItemslist[] = {"a", "b", "c"};
    private final OnMeneuClickListnser onMenuListClicklistener;
    static int getPosition;
    private int row_index;
    Context mContext;
    int select = 100000000;

    ArrayList<VendorDetails> listData;
    String currentLat;
    String currentLng;

    public NearByShopAdapter(String cLat, String cLng, ArrayList<VendorDetails> vendorDetailsList, OnMeneuClickListnser onLiveTestClickListener, Context context) {
        this.listData = vendorDetailsList;
        this.onMenuListClicklistener = onLiveTestClickListener;
        this.mContext = context;
        this.currentLng = cLng;
        this.currentLat = cLat;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;


        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vendor, parent, false);


        return new MyViewHolder(itemView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        VendorDetails data = listData.get(position);
        holder.vendor_name.setText(data.getShopName());
        holder.shopDes.setText(data.getDescription());
        Log.e("currentLat", currentLng);
        setRating(holder, data.getRate());
        //double distance=Util.distance(Double.parseDouble(currentLat),Double.parseDouble(currentLng),Double.parseDouble(data.getLat()),Double.parseDouble(data.getLng()),"");
        DecimalFormat formater = new DecimalFormat("##.#");
        String km = formater.format(Double.parseDouble(data.getDistance()));
        holder.tvKm.setText(String.format("%s km", km));
        Log.e("photo", VENDOR_SHOP_IMG_URL + data.getPhoto());
        Glide.with(mContext)
                .load(VENDOR_SHOP_IMG_URL + data.getPhoto()).placeholder(R.drawable.logo)
                .into(holder.imageView);
        if (data.getShopOnOff().equalsIgnoreCase("")) {
            holder.status.setText("Closed");
            holder.status.setTextColor(Color.GRAY);
            holder.shopStatus.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.red));
        } else {
            holder.status.setText("Open");
            holder.shopStatus.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.green));
            holder.status.setTextColor(Color.GRAY);
        }
        holder.likeDislike.setOnClickListener(v -> {
            select = position;
            if (data.getWhislistStatus().equalsIgnoreCase("0")) {
                data.setWhislistStatus("1");
            } else {
                data.setWhislistStatus("0");
            }
            onMenuListClicklistener.onHeartClick(data, data.getWhislistStatus());
            onMenuListClicklistener.onHeartClick(data, data.getWhislistStatus());
            notifyDataSetChanged();
        });
        if (data.getWhislistStatus().equalsIgnoreCase("0")) {
            holder.hearAnimation.setVisibility(View.GONE);
            holder.likeDislike.setVisibility(View.VISIBLE);
            holder.likeDislike.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A3A3A3")));
        } else {
            holder.hearAnimation.setVisibility(View.GONE);
            holder.likeDislike.setVisibility(View.VISIBLE);
            if (select == position) {
                holder.hearAnimation.setVisibility(View.VISIBLE);
                holder.likeDislike.setVisibility(View.GONE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.likeDislike.setVisibility(View.VISIBLE);
                        holder.hearAnimation.setVisibility(View.GONE);
                        // Do something after 5s = 5000ms
                    }
                }, 1000);
            }

            holder.likeDislike.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9423D")));

        }
        holder.shopView.setOnClickListener(v -> {
//            if (data.getShopOnOff().equalsIgnoreCase("")) {
//Util.show(mContext,"Sorry Shop is Close Now");
//            }else{
            onMenuListClicklistener.onOptionClick(data, position);
//            }

        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setRating(MyViewHolder holder, String rate) {

        //Util.show(mContext,rate);
        if (rate.equalsIgnoreCase("5.0")) {
            holder.tvStartFive.setVisibility(View.VISIBLE);
            holder.tvStartFour.setVisibility(View.VISIBLE);
            holder.tvStartThree.setVisibility(View.VISIBLE);
            holder.tvStartTwo.setVisibility(View.VISIBLE);
            holder.tvStartOne.setVisibility(View.VISIBLE);
        } else if (rate.equalsIgnoreCase("4.0")) {
            holder.tvStartFive.setVisibility(View.GONE);
            holder.tvStartFour.setVisibility(View.VISIBLE);
            holder.tvStartThree.setVisibility(View.VISIBLE);
            holder.tvStartTwo.setVisibility(View.VISIBLE);
            holder.tvStartOne.setVisibility(View.VISIBLE);

        } else if (rate.equalsIgnoreCase("3.0")) {
            holder.tvStartFive.setVisibility(View.GONE);
            holder.tvStartFour.setVisibility(View.GONE);
            holder.tvStartThree.setVisibility(View.VISIBLE);
            holder.tvStartTwo.setVisibility(View.VISIBLE);
            holder.tvStartOne.setVisibility(View.VISIBLE);
        } else if (rate.equalsIgnoreCase("2.0")) {
            holder.tvStartFive.setVisibility(View.GONE);
            holder.tvStartFour.setVisibility(View.GONE);
            holder.tvStartThree.setVisibility(View.GONE);
            holder.tvStartTwo.setVisibility(View.VISIBLE);
            holder.tvStartOne.setVisibility(View.VISIBLE);

        } else if (rate.equalsIgnoreCase("1.0")) {
            holder.tvStartFive.setVisibility(View.GONE);
            holder.tvStartFour.setVisibility(View.GONE);
            holder.tvStartThree.setVisibility(View.GONE);
            holder.tvStartTwo.setVisibility(View.GONE);
            holder.tvStartOne.setVisibility(View.VISIBLE);

        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        RecyclerView itemList;
        MaterialCardView shopView;
        MaterialTextView vendor_name, shopDes, tvKm;
        MaterialTextView status, submit_btn;
        ShapeableImageView imageView;
        AppCompatImageView tvStartOne, tvStartTwo, tvStartThree, tvStartFour, tvStartFive;

        AppCompatImageView likeDislike, shopStatus;
        LottieAnimationView hearAnimation;
        LikeButton star_button;

        MyViewHolder(View view) {
            super(view);
            submit_btn = view.findViewById(R.id.submit_btn);
            tvStartOne = view.findViewById(R.id.tvStartOne);
            tvStartTwo = view.findViewById(R.id.tvStartTwo);
            tvStartThree = view.findViewById(R.id.tvStartThree);
            tvStartFour = view.findViewById(R.id.tvStartFour);
            tvStartFive = view.findViewById(R.id.tvStartFive);
            shopView = view.findViewById(R.id.shopView);
            status = view.findViewById(R.id.status);
            shopDes = view.findViewById(R.id.shopDes);
            tvKm = view.findViewById(R.id.tvKm);
            hearAnimation = view.findViewById(R.id.hearAnimation);
            star_button = view.findViewById(R.id.star_button);
            shopStatus = view.findViewById(R.id.shopStatus);
            likeDislike = view.findViewById(R.id.likeDislike);
            vendor_name = view.findViewById(R.id.vendor_name);
            imageView = view.findViewById(R.id.imageView);
        }
    }

    public interface OnMeneuClickListnser {
        void onOptionClick(VendorDetails data, int pos);

        void onHeartClick(VendorDetails data, String status);
    }


}


