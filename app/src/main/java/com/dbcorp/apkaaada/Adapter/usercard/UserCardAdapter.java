package com.dbcorp.apkaaada.Adapter.usercard;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */
 
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dbcorp.apkaaada.Adapter.HomeItemAdapter;
import com.dbcorp.apkaaada.Adapter.MyCardProductAdapter;
import com.dbcorp.apkaaada.Adapter.ShopCardProduct;
import com.dbcorp.apkaaada.Adapter.ShopProduct;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.card.CardProduct;
import com.dbcorp.apkaaada.model.card.Coupon;
import com.dbcorp.apkaaada.model.home.OfferList;
import com.dbcorp.apkaaada.model.shopview.Product;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dbcorp.apkaaada.network.ApiService.OFFER_URL;

public class UserCardAdapter extends RecyclerView.Adapter<UserCardAdapter.MyViewHolder> implements MyCardProductAdapter.OnMeneuClickListnser{

    HomeItemAdapter homeItemAdapter;
    UserCardAdapter listner;
    private String arrItems[];
    private String arrItemslist[]={"a","b","c"};
    private final OnClickListener onClickListener;
    static int  getPosition;
    private int row_index;
    Context mContext;
    int select;
    MyCardProductAdapter shopProduct;
    ArrayList<CardProduct> list;
    ArrayList<Product> listProduct;

    public UserCardAdapter(ArrayList<CardProduct> getlist, OnClickListener listener, Context context) {
        this.list = getlist;
        this.onClickListener = listener;
        this.mContext=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_card_by_vendor, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CardProduct cardProduct=list.get(position);

        holder.productCount.setText(cardProduct.getProductCount().toString());
        holder.tvVendorName.setText(cardProduct.getVendorName());
        shopProduct = new MyCardProductAdapter(cardProduct.getProductList(), UserCardAdapter.this,mContext);
        holder.itemList.setAdapter(shopProduct);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void productRemove(Product liveTest, int pos) {
onClickListener.removeCartProduct(liveTest.getCart_id());
    }

    @Override
    public void productClick(Product liveTest, int pos) {

    }

    @Override
    public void addCartClick(Product data, int position, String subAdd, ArrayList<Product> arrayList, View showProduct) {

        onClickListener.addCartClick(data,position,subAdd,arrayList,showProduct);

    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView productCount,tvVendorName;
        RecyclerView itemList;
          MyViewHolder(View view) {
            super(view);
              tvVendorName=view.findViewById(R.id.tvVendorName);
              productCount=view.findViewById(R.id.productCount);
              itemList=view.findViewById(R.id.itemList);

              itemList.setHasFixedSize(true);
              //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
              itemList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

          }
    }
    public interface OnClickListener{
        void onClickOrder(String liveTest, int pos, String type);
        void removeCartProduct(String cartId);
        void addCartClick(Product liveTest, int pos,String subAdd,ArrayList<Product> arrayList,View showProduct);
    }



 }


