package com.dbcorp.apkaaada.Adapter.myorder;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */
 
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.CheckTrueFalse;
import com.dbcorp.apkaaada.model.order.CustomerOrderDetails;
import com.dbcorp.apkaaada.model.order.OrderParam;
import com.dbcorp.apkaaada.model.order.Orders;
import com.dbcorp.apkaaada.model.shopview.SubToSubCategory;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;


public class OrdersDetailsAdapter extends RecyclerView.Adapter<OrdersDetailsAdapter.MyViewHolder> implements  OrdersAdapter.OnMeneuClickListnser {

    private final  OnMeneuClickListnser onMenuListClicklistener;
    ArrayList<CustomerOrderDetails> listData;
    OrdersAdapter ordersAdapter;
    OrderInstruction orderInstruction;
    OrderLog orderLog;
    static int  getPosition;
    private int row_index;
    Context mContext;
    int select;
 String status;
    public OrdersDetailsAdapter(ArrayList<CustomerOrderDetails> list, OnMeneuClickListnser onLiveTestClickListener, Context context, String status) {
        this.listData = list;
       // this.list=new ArrayList<CheckTrueFalse>(list.size()+1);

        this.onMenuListClicklistener = onLiveTestClickListener;
        this.mContext=context;
this.status=status;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;


        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_order_list, parent, false);


        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CustomerOrderDetails data=listData.get(position);
        OrderParam orderParam=data.getOrderDetails();
        holder.tvDate.setText(orderParam.getOrderDate());
        holder.tvOrderNumber.setText(orderParam.getOrderNumber());
        holder.tvCusName.setText(orderParam.getVendorName());
        holder.tvTotalPrice.setText(orderParam.getTotal());
holder.vendorCall.setOnClickListener(v->{
    onMenuListClicklistener.vendorCalling(data,position);
});


        holder.tvShow.setOnClickListener(v->{
            if (data.getViewList()){
                data.setViewList(false);
                notifyDataSetChanged();
            }else{
                data.setViewList(true);
                notifyDataSetChanged();
            }

        });
        if(data.getViewList()){
            holder.listItem.setVisibility(View.VISIBLE);
            holder.product.setVisibility(View.VISIBLE);
            holder.instructionOrderLog.setVisibility(View.VISIBLE);
            holder.instruction.setVisibility(View.VISIBLE);
        }else {
            holder.listItem.setVisibility(View.GONE);
            holder.product.setVisibility(View.GONE);
            holder.instructionOrderLog.setVisibility(View.GONE);
            holder.instruction.setVisibility(View.GONE);
        }
//        if (holder.listItem.getVisibility() == View.VISIBLE) {
//            holder.tvShow.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
//        } else {
//            holder.tvShow.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
//        }

        holder.product.setOnClickListener(v->{
            ordersAdapter = new OrdersAdapter(status,data.getProductDetails(), this, mContext);
            holder.listItem.setAdapter(ordersAdapter);

            holder.product.setTextColor(Color.WHITE);
            holder.product.setBackgroundResource(R.drawable.red_gredient_cirl_bg);

            holder.instruction.setTextColor(Color.BLACK);
            holder.instruction.setBackground(null);

            holder.instructionOrderLog.setTextColor(Color.BLACK);
            holder.instructionOrderLog.setBackground(null);
        });

        holder.instruction.setOnClickListener(v->{
            orderInstruction= new OrderInstruction(data.getInstruction(), mContext);
            holder.listItem.setAdapter(orderInstruction);

            holder.instruction.setTextColor(Color.WHITE);
            holder.instruction.setBackgroundResource(R.drawable.red_gredient_cirl_bg);

            holder.product.setTextColor(Color.BLACK);
            holder.product.setBackground(null);

            holder.instructionOrderLog.setTextColor(Color.BLACK);
            holder.instructionOrderLog.setBackground(null);
        });

        holder.instructionOrderLog.setOnClickListener(v->{

            orderLog= new OrderLog(data.getInstructionOrderLog(), mContext);
            holder.listItem.setAdapter(orderLog);


            holder.instructionOrderLog.setTextColor(Color.WHITE);
            holder.instructionOrderLog.setBackgroundResource(R.drawable.red_gredient_cirl_bg);

            holder.instruction.setTextColor(Color.BLACK);
            holder.instruction.setBackground(null);

            holder.product.setTextColor(Color.BLACK);
            holder.product.setBackground(null);
        });


        ordersAdapter = new OrdersAdapter(status,data.getProductDetails(), this, mContext);
        holder.listItem.setAdapter(ordersAdapter);


        holder.tvAddress.setText(orderParam.getAddress());



        String qnty=  String.valueOf(data.getProductDetails().size());
        holder.tvQnty.setText("Quantity : "+qnty);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }



    @Override
    public void onOptionClick(ArrayList<Orders> list, int pos) {
         onMenuListClicklistener.selectProduct(listData);
    }

    @Override
    public void orderAssign(Orders data, int pos) {
        onMenuListClicklistener.viewSingleOrder(data,pos);
    }

    @Override
    public void customerCall(Orders data, int pos) {
        onMenuListClicklistener.deliveryCall(data,pos);
    }

    @Override
    public void vendorCall(Orders data, int pos) {

    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView product,instructionOrderLog, instruction,tvAddress,orderAccept,tvDate,tvQnty,tvOrderNumber,tvCusName,tvTotalPrice;
        RecyclerView listItem;
        AppCompatImageView tvShow,vendorCall;
        LinearLayoutCompat viewDet;
         MyViewHolder(View view) {
            super(view);
             listItem = itemView.findViewById(R.id.listItem);
             tvDate=view.findViewById(R.id.tvDate);
             tvAddress=view.findViewById(R.id.tvAddress);
             product=view.findViewById(R.id.product);
             vendorCall=view.findViewById(R.id.vendorCall);
             instructionOrderLog=view.findViewById(R.id.instructionOrderLog);
             instruction=view.findViewById(R.id.instruction);
             tvShow=view.findViewById(R.id.tvShow);
             tvQnty=view.findViewById(R.id.tvQnty);
             orderAccept=view.findViewById(R.id.orderAccept);
             tvOrderNumber=view.findViewById(R.id.tvOrderNumber);
             viewDet=view.findViewById(R.id.viewDet);
             tvCusName=view.findViewById(R.id.tvCusName);

             tvTotalPrice=view.findViewById(R.id.tvTotalPrice);
         }
    }
    public interface OnMeneuClickListnser{
        void viewSingleOrder(Orders data, int pos);
        void deliveryCall(Orders data, int pos);
        void vendorCalling(CustomerOrderDetails data, int pos);

        void selectProduct(ArrayList<CustomerOrderDetails> listData);
    }


 }


