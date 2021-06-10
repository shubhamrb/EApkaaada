package com.dbcorp.apkaaada.Adapter.myorder;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.order.Orders;
import com.dbcorp.apkaaada.ui.auth.Home.HomeActivity;
import com.dbcorp.apkaaada.ui.auth.fragments.order.myorder.SingleProductActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.dbcorp.apkaaada.network.ApiService.IMG_PRODUCT_URL;


public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder> {


    ArrayList<Orders> listData;
    private final OnMeneuClickListnser onMenuListClicklistener;
    static int  getPosition;
    private int row_index;
    Context mContext;
    int select;
String status;
    public OrdersAdapter(String status, ArrayList<Orders> list, OnMeneuClickListnser onLiveTestClickListener, Context context) {
        this.listData = list;
        this.status=status;
        this.onMenuListClicklistener = onLiveTestClickListener;
        this.mContext=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;


        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Orders data=listData.get(position);

holder.cardView.setOnClickListener(v->{
    Intent intent = new Intent(mContext, SingleProductActivity.class);
    intent.putExtra("MyData", data);
// To retrieve object in second Activity
    mContext.startActivity(intent);

});

        holder.tvPrice.setText("₹ "+data.getProductPrice()+".00");
        int tp=Integer.parseInt(data.getQuantity())*Integer.parseInt(data.getProductPrice());
        holder.tvTotalPrice.setText(data.getQuantity()+"X"+data.getProductPrice()+" = ₹ "+tp+".00");


        holder.tvStatus.setTextColor(Color.parseColor(data.getStatusColor()));


        if(data.getStatus().equalsIgnoreCase("1")){
            holder.tvStatus.setText("Order Placed");
            holder.track_dot_1.setImageResource(R.drawable.track_dot_2);
            holder.track_line_1.setBackgroundResource(R.drawable.track_line_2);
            holder.statusLayout.setVisibility(View.VISIBLE);
        }else if(data.getStatus().equalsIgnoreCase("2")){
            holder.tvStatus.setText(data.getStatusName());
            holder.track_dot_2.setImageResource(R.drawable.track_dot_2);
            holder.track_line_2.setBackgroundResource(R.drawable.track_line_2);
            holder.statusLayout.setVisibility(View.VISIBLE);
        }else if(data.getStatus().equalsIgnoreCase("3")){
            holder.tvStatus.setText(data.getStatusName());
            holder.statusLayout.setVisibility(View.GONE);
        }else if(data.getStatus().equalsIgnoreCase("6") || data.getStatus().equalsIgnoreCase("5") || data.getStatus().equalsIgnoreCase("4")){
            holder.tvStatus.setText(data.getStatusName());
            holder.track_dot_1.setImageResource(R.drawable.track_dot_2);
            holder.track_line_1.setBackgroundResource(R.drawable.track_line_2);
            holder.track_dot_2.setImageResource(R.drawable.track_dot_2);
            holder.track_line_2.setBackgroundResource(R.drawable.track_line_2);
            holder.track_dot_3.setImageResource(R.drawable.track_dot_2);
            holder.track_line_3.setBackgroundResource(R.drawable.track_line_2);
            holder.statusLayout.setVisibility(View.VISIBLE);
        }else if(data.getStatus().equalsIgnoreCase("7")){
            holder.tvStatus.setText(data.getStatusName());

            holder.track_dot_1.setImageResource(R.drawable.track_dot_2);
            holder.track_line_1.setBackgroundResource(R.drawable.track_line_2);
            holder.track_dot_2.setImageResource(R.drawable.track_dot_2);
            holder.track_line_2.setBackgroundResource(R.drawable.track_line_2);
            holder.track_dot_3.setImageResource(R.drawable.track_dot_2);
            holder.track_line_3.setBackgroundResource(R.drawable.track_line_2);
            holder.track_dot_4.setImageResource(R.drawable.track_dot_2);
            holder.track_line_4.setBackgroundResource(R.drawable.track_line_2);

            holder.statusLayout.setVisibility(View.VISIBLE);
        }else if(data.getStatus().equalsIgnoreCase("8")){
            holder.tvStatus.setText(data.getStatusName());
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
            holder.statusLayout.setVisibility(View.VISIBLE);
        }else if(data.getStatus().equalsIgnoreCase("9")){
            holder.tvStatus.setText(data.getStatusName());

            holder.statusLayout.setVisibility(View.GONE);
        }else if(data.getStatus().equalsIgnoreCase("9")){
            holder.tvStatus.setText(data.getStatusName());

            holder.statusLayout.setVisibility(View.GONE);
        }else if(data.getStatus().equalsIgnoreCase("10")){
            holder.tvStatus.setText(data.getStatusName());

            holder.statusLayout.setVisibility(View.GONE);
        }else if(data.getStatus().equalsIgnoreCase("11") ||  data.getStatus().equalsIgnoreCase("12")){
            holder.tvStatus.setText("");

            holder.statusLayout.setVisibility(View.GONE);
        }


        holder.tvProductAttribute.setText(data.getOrderAttribute());
        holder.tvProductName.setText(data.getProductName());

        holder.catName.setText(data.getCategoryName());
        holder.subCat.setText(data.getSubCategoryName());
        holder.subtosubCat.setText(data.getSubsubCategoryName());
        holder.tvDeliverBoy.setText(data.getDeliveryBoyName());
        holder.deliveryBoyCall.setVisibility(View.VISIBLE);
        holder.deliveryBoyCall.setOnClickListener(v->{
            onMenuListClicklistener.customerCall(data,position);
        });
    holder.tvCheckBox.setVisibility(View.GONE);

        holder.tvCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(data.getCheckbox().equalsIgnoreCase("0")){
                    data.setCheckbox("1");
                }else{
                    data.setCheckbox("0");
                }


                onMenuListClicklistener.onOptionClick(listData,position);
                notifyDataSetChanged();
            }
        });
        if(data.getCheckbox().equalsIgnoreCase("1")){
            holder.tvCheckBox.setChecked(true);
        }else{
            holder.tvCheckBox.setChecked(false);
        }

        holder.orderAssign.setVisibility(View.GONE);
        Glide.with(mContext)
                .load(IMG_PRODUCT_URL+data.getProductPhoto())
                .into(holder.productImg);

//        holder.orderAssign.setOnClickListener(v->{
//            onMenuListClicklistener.orderAssign(data,position);
//        });



    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView tvTotalPrice,tvDeliverBoy,tvProductName,tvProductAttribute,catName,subCat,subtosubCat, tvDate,tvOrderNumber,tvCustName,orderAssign,tvPrice,tvStatus;
        MaterialCardView cardView;
        LinearLayoutCompat statusLayout;
        View track_line_1,track_line_2,track_line_3,track_line_4,track_line_5;
        AppCompatImageView deliveryBoyCall,track_dot_1,track_dot_2,track_dot_3,track_dot_4,track_dot_5;
        AppCompatCheckBox tvCheckBox;
        LinearLayoutCompat detailsShow;
        CircleImageView productImg;
         MyViewHolder(View view) {
            super(view);
             deliveryBoyCall=view.findViewById(R.id.deliveryBoyCall);
             track_line_1=view.findViewById(R.id.track_line_1);
             track_dot_1=view.findViewById(R.id.track_dot_1);
             statusLayout=view.findViewById(R.id.statusLayout);
             track_line_2=view.findViewById(R.id.track_line_2);
             track_dot_2=view.findViewById(R.id.track_dot_2);

             track_line_2=view.findViewById(R.id.track_line_2);
             track_dot_2=view.findViewById(R.id.track_dot_2);

             track_line_3=view.findViewById(R.id.track_line_3);
             track_dot_3=view.findViewById(R.id.track_dot_3);

             track_line_4=view.findViewById(R.id.track_line_4);
             track_dot_4=view.findViewById(R.id.track_dot_4);

             track_line_5=view.findViewById(R.id.track_line_5);
             track_dot_5=view.findViewById(R.id.track_dot_5);

             tvTotalPrice=view.findViewById(R.id.tvTotalPrice);
             tvProductName=view.findViewById(R.id.tvProductName);
             detailsShow=view.findViewById(R.id.detailsShow);
             tvProductAttribute=view.findViewById(R.id.tvProductAttribute);
             catName=view.findViewById(R.id.catName);
             tvCheckBox=view.findViewById(R.id.tvCheckBox);
             productImg=view.findViewById(R.id.productImg);
             tvDeliverBoy=view.findViewById(R.id.tvDeliverBoy);
             subCat=view.findViewById(R.id.subCat);
             subtosubCat=view.findViewById(R.id.subtosubCat);
             tvStatus=view.findViewById(R.id.tvStatus);
             orderAssign=view.findViewById(R.id.orderAssign);


             tvPrice=view.findViewById(R.id.tvPrice);
             cardView=view.findViewById(R.id.cardView);

         }
    }
    public interface OnMeneuClickListnser{
        void onOptionClick(ArrayList<Orders>  list, int pos);
        void orderAssign(Orders data, int pos);
        void customerCall(Orders data, int pos);
        void vendorCall(Orders data, int pos);
    }


 }


