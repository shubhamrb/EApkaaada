package com.dbcorp.apkaaada.Adapter.usercard;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.Adapter.ShopProduct;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.card.CardProduct;
import com.dbcorp.apkaaada.model.shopview.Product;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.Home.HomeActivity;
import com.dbcorp.apkaaada.ui.auth.fragments.order.ActivityOrder;
import com.dbcorp.apkaaada.ui.auth.fragments.order.myorder.MyOrder;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderPriceAdapter extends RecyclerView.Adapter<OrderPriceAdapter.MyViewHolder> {


    Context mContext;

    ProductVariantAdapter shopProduct;

    ArrayList<CardProduct> cardProducts;
    ArrayList<Product> listProduct;
    UserDetails userDetails;
    double lati, longi;
    private final  OnDeleteVendor onDeleteVendor;
    boolean checkDistance=false;
    public OrderPriceAdapter(OnDeleteVendor onDeleteVendor,double lati, double longi, ArrayList<CardProduct> listData, Context context) {
        this.cardProducts = listData;
        this.lati = lati;
        this.longi = longi;
        this.mContext = context;
        this.onDeleteVendor = onDeleteVendor;
        userDetails = new SqliteDatabase(context).getLogin();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_final_oder, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CardProduct cardProduct = cardProducts.get(position);

        holder.tvVendorName.setText(cardProduct.getShop_name());
        double kmDist = Util.distance(lati, longi, Double.parseDouble(cardProduct.getLat()), Double.parseDouble(cardProduct.getLng()), "k");

        holder.tvKm.setText(String.format("%s km", kmDist));
        int kmDistInt = (int) kmDist;

        int charge = Integer.parseInt(cardProduct.getDelivery_charge()) + kmDistInt * Integer.parseInt(cardProduct.getDelivery_charge_per_km());
        DecimalFormat formater = new DecimalFormat("##.#");
        String km = formater.format(kmDist);
        holder.tvKm.setText(String.format("%s km", km));
        holder.tvCharge.setText(String.format("₹ %s ", charge));
        cardProduct.setShop_distance(km);
        cardProduct.setShop_distance_delivery_charge(String.valueOf(charge));
        int orderRang = Integer.parseInt(cardProduct.getOrder_range_limit());

        if (kmDist > orderRang) {
            holder.imgDelete.setVisibility(View.VISIBLE);
            checkDistance=true;
            onDeleteVendor.deleteVendor(checkDistance);
        } else {
            onDeleteVendor.deleteVendor(checkDistance);
            holder.imgDelete.setVisibility(View.GONE);
        }

        holder.tvCharge.setText(String.format("₹ %s ", charge));
        holder.tvVendProductPrice.setText(String.format("Total Price Of This Shop : ₹ %s ", cardProduct.getTotalProductPrice()));

        if (cardProduct.getDiscountPrice().equalsIgnoreCase("0")) {
            holder.tvVendDiscountPrice.setVisibility(View.GONE);
        } else {
            holder.tvVendDiscountPrice.setVisibility(View.VISIBLE);
            holder.tvVendDiscountPrice.setText(String.format("Discount Price Of This Shop : ₹ %s ", cardProduct.getDiscountPrice()));

        }

        shopProduct = new ProductVariantAdapter(cardProduct.getProductList(), mContext);
        holder.itemList.setAdapter(shopProduct);

        holder.imgDelete.setOnClickListener(v -> {
            removeAlert(cardProduct.getProductIdes(), position);
        });

    }

    @Override
    public int getItemCount() {
        return cardProducts.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView imgDelete;
        MaterialTextView tvVendorName, tvVendDiscountPrice, tvCharge, tvKm, tvVendProductPrice;
        RecyclerView itemList;

        MyViewHolder(View view) {
            super(view);
            imgDelete = view.findViewById(R.id.imgDelete);
            tvVendorName = view.findViewById(R.id.tvVendorName);
            tvKm = view.findViewById(R.id.tvKm);
            tvCharge = view.findViewById(R.id.tvCharge);
            tvVendDiscountPrice = view.findViewById(R.id.tvVendDiscountPrice);
            itemList = view.findViewById(R.id.itemList);
            tvVendProductPrice = view.findViewById(R.id.tvVendProductPrice);
            itemList.setHasFixedSize(true);
            //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            itemList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        }
    }


    public void  getOrderList(){
        Log.e("datat","jhkjhkjhjkhkj");
        int totalDeliveryCharge=0;
        for(int i=0;i<cardProducts.size();i++){

            int shDeliveryCharge= Integer.parseInt(cardProducts.get(i).getShop_distance_delivery_charge());
            totalDeliveryCharge+=shDeliveryCharge;
        }
        onDeleteVendor.invoiceOrderList(cardProducts,totalDeliveryCharge);
    }
    private void removeAlert(String cardId, int pos) {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    try {
                        Log.e("positive","ddddddddddddddddddddddddddd");
                        removeProduct(cardId, pos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        };
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        builder.setMessage("Are you sure want to remove this product from your cart ?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    private void removeProduct(String cart_id, int position) {

        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();
            params.put("cart_id", cart_id);
            Log.e("param", params.toString());
            RestClient.post().deleteInvoiceData(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    try {
                        Gson gson = new Gson();
                        JSONObject strObj = new JSONObject(Objects.requireNonNull(response.body()));
                        if (strObj.getBoolean("status")) {
                            checkDistance=false;
                            cardProducts.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, cardProducts.size());
                            notifyDataSetChanged();
                            Intent mv2 = new Intent(mContext, HomeActivity.class);
                            mContext.startActivity(mv2);


                        } else {

                            Util.show(mContext, strObj.getString("message"));
                        }
                    } catch (Exception e) {
                        Util.show(mContext, e.getMessage());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {

                    try {
                        t.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        }

    }

    public interface OnDeleteVendor {
        void deleteVendor(boolean distance);
        void invoiceOrderList(ArrayList<CardProduct> cardProducts,int tdc);
     }
}


