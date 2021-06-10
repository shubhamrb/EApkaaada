package com.dbcorp.apkaaada.Adapter;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.home.OfferList;
import com.dbcorp.apkaaada.model.home.VendorData;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import static com.dbcorp.apkaaada.network.ApiService.VENDOR_IMG_URL;

public class ShopHome extends RecyclerView.Adapter<ShopHome.MyViewHolder> {

    HomeItemAdapter homeItemAdapter;
    ShopHome listner;
    private String arrItems[];
    private String arrItemslist[] = {"a", "b", "c"};
    private final OnClickListener onClickListener;
    static int getPosition;
    private int row_index;
    Context mContext;
    int select;

    ArrayList<VendorData> list;
    String typeView;

    public ShopHome(String type,ArrayList<VendorData> getlist, OnClickListener listener, Context context) {
        this.list = getlist;
        this.onClickListener = listener;
        this.mContext = context;
        this.typeView=type;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;

        if(typeView.equalsIgnoreCase("Home")){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop, parent, false);

        }else{
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_list, parent, false);

        }


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VendorData data = list.get(position);
        Glide.with(mContext)
                .load(VENDOR_IMG_URL+data.getPhoto())
                .into(holder.shapeableImageView);
        holder.tvName.setText(data.getShop_name());
        holder.tvDescription.setText(data.getDescription());

        if(data.getRate().equalsIgnoreCase("1.0")){
            holder.starOne.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FBBC04")));

        }else if(data.getRate().equalsIgnoreCase("2.0")){
            holder.starOne.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FBBC04")));
            holder.starTwo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FBBC04")));
        }else if(data.getRate().equalsIgnoreCase("3.0")){
            holder.starOne.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FBBC04")));
            holder.starTwo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FBBC04")));
            holder.starThree.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FBBC04")));

        }else if(data.getRate().equalsIgnoreCase("4.0")){
            holder.starOne.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FBBC04")));
            holder.starTwo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FBBC04")));
            holder.starThree.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FBBC04")));
            holder.starFour.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FBBC04")));

        }else if(data.getRate().equalsIgnoreCase("5.0")){
            holder.starOne.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FBBC04")));
            holder.starTwo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FBBC04")));
            holder.starThree.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FBBC04")));
            holder.starFour.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FBBC04")));
            holder.starFive.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FBBC04")));

        }

        holder.likeDislike.setOnClickListener(v -> {
            select=position;
            if(data.getWhislistStatus().equalsIgnoreCase("0")){
                data.setWhislistStatus("1");
            }else{
                data.setWhislistStatus("0");
            }
            notifyDataSetChanged();
            onClickListener.offerClick(data,"2",data.getWhislistStatus());

        });




        if(data.getWhislistStatus().equalsIgnoreCase("0")){


            holder.hearAnimation.setVisibility(View.GONE);
            holder.likeDislike.setVisibility(View.VISIBLE);

            holder.likeDislike.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A3A3A3")));

        }else{
            holder.hearAnimation.setVisibility(View.GONE);
            holder.likeDislike.setVisibility(View.VISIBLE);
            if(select==position){
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

        holder.viewClick.setOnClickListener(v->{
            onClickListener.offerClick(data,"1",data.getWhislistStatus());

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayoutCompat viewClick;
        ShapeableImageView shapeableImageView;
        MaterialTextView tvName,tvDescription;
        AppCompatImageView likeDislike,starOne,starTwo,starThree,starFour,starFive;
        LottieAnimationView hearAnimation;
        MyViewHolder(View view) {
            super(view);
            viewClick = view.findViewById(R.id.viewClick);
            tvName=view.findViewById(R.id.tvName);
            likeDislike=view.findViewById(R.id.likeDislike);
            hearAnimation=view.findViewById(R.id.hearAnimation);
            tvDescription=view.findViewById(R.id.tvDescription);
            starOne=view.findViewById(R.id.starOne);
            starTwo=view.findViewById(R.id.starTwo);
            starThree=view.findViewById(R.id.starThree);
            starFour=view.findViewById(R.id.starFour);
            starFive=view.findViewById(R.id.starFive);


            shapeableImageView = view.findViewById(R.id.imageView);
        }
    }

    public interface OnClickListener {
        void offerClick(VendorData liveTest, String type, String status);
    }


}


