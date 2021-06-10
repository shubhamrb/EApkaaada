package com.dbcorp.apkaaada.Adapter.myorder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.order.ProductDetail;
import com.dbcorp.apkaaada.model.order.UserOrderModel;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.dbcorp.apkaaada.network.ApiService.BASE_IMG_CAT_URL;
import static com.dbcorp.apkaaada.network.ApiService.IMG_PRODUCT_URL;
import static com.dbcorp.apkaaada.network.ApiService.PRODUCT_IMG_URL;

public class MyOrderProduct extends RecyclerView.Adapter<MyOrderProduct.ViewHolder> {
    private final OnClickListener onClickListener;
    Context mContext;
    ArrayList<ProductDetail> list;

    public MyOrderProduct(ArrayList<ProductDetail> list, OnClickListener onClickListener, Context mContext) {
        this.onClickListener = onClickListener;
        this.mContext = mContext;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_product, parent, false);


        return new ViewHolder(itemView);    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDetail data=list.get(position);
        Glide.with(mContext)
                .load(IMG_PRODUCT_URL+data.getProductPhoto())
                .into(holder.productImg);

        holder.tvTotPrice.setText("₹ "+data.getPrice());
        holder.catName.setText(data.getCategoryName());
        holder.subCat.setText(data.getSubCategoryName());
        holder.subtosubCat.setText(data.getSubsubCategoryName());
       // holder.tvVariantValue.setText(data.get());
        holder.productName.setText(data.getSubsubCategoryName());

        holder.tvColor.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.colorPrimary));
        holder.tvStatus.setText(data.getStatusName());
        int status= Integer.parseInt(data.getStatus());
        switch(status) {
            case 1:
                holder.track_dot_1.setImageResource(R.drawable.track_dot_2);
                holder.track_line_1.setBackgroundResource(R.drawable.track_line_2);
                break;
            case 2:
                holder.track_dot_1.setImageResource(R.drawable.track_dot_2);
                holder.track_line_1.setBackgroundResource(R.drawable.track_line_2);
                holder.track_dot_2.setImageResource(R.drawable.track_dot_2);
                holder.track_line_2.setBackgroundResource(R.drawable.track_line_2);
                break;
            case 4:
                holder.track_dot_1.setImageResource(R.drawable.track_dot_2);
                holder.track_line_1.setBackgroundResource(R.drawable.track_line_2);
                holder.track_dot_2.setImageResource(R.drawable.track_dot_2);
                holder.track_line_2.setBackgroundResource(R.drawable.track_line_2);
                holder.track_dot_3.setImageResource(R.drawable.track_dot_2);
                holder.track_line_3.setBackgroundResource(R.drawable.track_line_2);
                break;
            case 5:
                holder.track_dot_1.setImageResource(R.drawable.track_dot_2);
                holder.track_line_1.setBackgroundResource(R.drawable.track_line_2);
                holder.track_dot_2.setImageResource(R.drawable.track_dot_2);
                holder.track_line_2.setBackgroundResource(R.drawable.track_line_2);
                holder.track_dot_3.setImageResource(R.drawable.track_dot_2);
                holder.track_line_3.setBackgroundResource(R.drawable.track_line_2);
                holder.track_dot_4.setImageResource(R.drawable.track_dot_2);
                holder.track_line_4.setBackgroundResource(R.drawable.track_line_2);
                break;
            case 6:
                holder.track_dot_1.setImageResource(R.drawable.track_dot_2);
                holder.track_line_1.setBackgroundResource(R.drawable.track_line_2);
                holder.track_dot_2.setImageResource(R.drawable.track_dot_2);
                holder.track_line_2.setBackgroundResource(R.drawable.track_line_2);
                holder.track_dot_3.setImageResource(R.drawable.track_dot_2);
                holder.track_line_3.setBackgroundResource(R.drawable.track_line_2);
                holder.track_dot_4.setImageResource(R.drawable.track_dot_2);
                holder.track_line_4.setBackgroundResource(R.drawable.track_line_2);
                holder.track_dot_5.setImageResource(R.drawable.track_dot_2);
                holder.track_line_5.setBackgroundResource(R.drawable.track_line_2);
                break;

            default:
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

        CircleImageView productImg;
        AppCompatImageView track_dot_1,track_dot_2,track_dot_3,track_dot_4,track_dot_5,track_dot_6,track_dot_7;
        View track_line_1,track_line_2,track_line_3,track_line_4,track_line_5,track_line_6,track_line_7;
        MaterialTextView  productName,tvColor,tvTotPrice,tvStatus,tvVariantValue,catName,subCat,subtosubCat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            track_dot_1=itemView.findViewById(R.id.track_dot_1);
            track_dot_2=itemView.findViewById(R.id.track_dot_2);
            track_dot_3=itemView.findViewById(R.id.track_dot_3);
            track_dot_4=itemView.findViewById(R.id.track_dot_4);
            track_dot_5=itemView.findViewById(R.id.track_dot_5);
            track_dot_6=itemView.findViewById(R.id.track_dot_6);
            track_dot_7=itemView.findViewById(R.id.track_dot_7);

            track_line_1=itemView.findViewById(R.id.track_line_1);
            track_line_2=itemView.findViewById(R.id.track_line_2);
            track_line_3=itemView.findViewById(R.id.track_line_3);
            track_line_4=itemView.findViewById(R.id.track_line_4);
            track_line_5=itemView.findViewById(R.id.track_line_5);
            track_line_6=itemView.findViewById(R.id.track_line_6);




            productName=itemView.findViewById(R.id.productName);
            productImg=itemView.findViewById(R.id.productImg);
            tvStatus=itemView.findViewById(R.id.tvStatus);
            catName=itemView.findViewById(R.id.catName);
            subCat=itemView.findViewById(R.id.subCat);
            tvColor=itemView.findViewById(R.id.tvColor);
            subtosubCat=itemView.findViewById(R.id.subtosubCat);
            tvTotPrice=itemView.findViewById(R.id.tvTotPrice);
            tvVariantValue=itemView.findViewById(R.id.tvVariantValue);


        }
    }
    public interface OnClickListener{
        void onClickCard(UserOrderModel list);
    }
}
