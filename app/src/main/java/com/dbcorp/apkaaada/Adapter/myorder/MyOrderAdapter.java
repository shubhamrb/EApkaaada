package com.dbcorp.apkaaada.Adapter.myorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.BooleanModel;
import com.dbcorp.apkaaada.model.ServiceModel;
import com.dbcorp.apkaaada.model.order.OrderDetails;
import com.dbcorp.apkaaada.model.order.OrderModel;
import com.dbcorp.apkaaada.model.order.ProductDetail;
import com.dbcorp.apkaaada.model.order.UserOrderModel;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> implements MyOrderProduct.OnClickListener {
    private final OnClickListener onClickListener;
    Context mContext;

    ArrayList<UserOrderModel> list;
    MyOrderProduct myOrderProduct;

    ArrayList<BooleanModel> showProduct;
    ArrayList<ProductDetail> productDetailsList;

    public MyOrderAdapter(ArrayList<UserOrderModel> list, OnClickListener onClickListener, Context mContext) {
        this.onClickListener = onClickListener;
        this.mContext = mContext;
        this.list = list;
        showProduct = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item, parent, false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserOrderModel data = list.get(position);
        productDetailsList = data.getProductDetails();

        holder.listItem.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        holder.listItem.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        OrderDetails orderDetails=data.getOrderDetails();
        holder.tvDate.setText(orderDetails.getOrderDate());
        holder.tvDate.setText(orderDetails.getOrderDate());
        holder.tvVenName.setText(orderDetails.getVendorName());
        holder.tvPrice.setText("Total Price : "+orderDetails.getTotal());

        holder.tvOrderNumber.setText(orderDetails.getOrderNumber());
        myOrderProduct = new MyOrderProduct(productDetailsList, this::onClickCard, mContext);
        holder.listItem.setAdapter(myOrderProduct);
        String qnty=productDetailsList.size()>0 ? String.valueOf(productDetailsList.size()) : "0";
holder.tvQnty.setText("Quantity : "+qnty);

//        holder.viewProduct.setOnClickListener(v -> {
//            if (showProduct.get(position).isCheck()) {
//                showProduct.get(position).setCheck(false);
//            } else {
//                showProduct.get(position).setCheck(true);
//            }
//            notifyDataSetChanged();
//        });
//        if(showProduct.get(position).isCheck()) {
//            holder.listItem.setVisibility(View.VISIBLE);
//        } else {
//            holder.listItem.setVisibility(View.GONE);
//        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClickCard(UserOrderModel list) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView listItem;
        MaterialTextView tvOrderNumber,tvVenName,tvQnty,tvDate,tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listItem = itemView.findViewById(R.id.listItem);
            tvOrderNumber=itemView.findViewById(R.id.tvOrderNumber);
            tvDate=itemView.findViewById(R.id.tvDate);
            tvQnty=itemView.findViewById(R.id.tvQnty);
            tvVenName=itemView.findViewById(R.id.tvVenName);
            tvPrice=itemView.findViewById(R.id.tvPrice);
        }
    }

    public interface OnClickListener {
        void onClickCard(UserOrderModel list);
    }
}
