package com.dbcorp.apkaaada.Adapter;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.Adapter.offer.SliderCategoryAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.HomeShopListCategory;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.VendorDetails;
import com.dbcorp.apkaaada.model.home.Category;
import com.dbcorp.apkaaada.model.home.HomeData;
import com.dbcorp.apkaaada.model.home.SliderImage;
import com.dbcorp.apkaaada.model.home.VendorData;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.Home.HomeActivity;
import com.dbcorp.apkaaada.ui.auth.fragments.Home;
import com.dbcorp.apkaaada.ui.auth.fragments.ServiceShop;
import com.dbcorp.apkaaada.ui.auth.fragments.SetAddressActivity;
import com.dbcorp.apkaaada.ui.auth.fragments.ShopDetails;
import com.dbcorp.apkaaada.ui.auth.fragments.product.UserSearchActivity;
import com.dbcorp.apkaaada.ui.auth.fragments.product.shopview;
import com.dbcorp.apkaaada.ui.auth.fragments.shop.SingleShopDetails;
import com.dbcorp.apkaaada.ui.auth.fragments.shop.nearbyShop;
import com.dbcorp.apkaaada.ui.auth.searchbyslider.SliderByCategory;
import com.dbcorp.apkaaada.ui.auth.searchbyslider.SliderByWeb;
import com.dbcorp.apkaaada.ui.auth.searchbyslider.SliderProduct;
import com.dbcorp.apkaaada.ui.auth.searchbyslider.SliderSearchKeyWord;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> implements Offer.OnClickListener,ShopCategoryVendor.OnClickListener, HomeItemAdapter.OnMeneuClickListnser, ShopCategory.OnMeneuClickListnser,ShopHome.OnClickListener,SliderCategoryAdapter.OnClickListener {

    HomeItemAdapter homeItemAdapter;
    HomeAdapter listner;
    ShopCategory shopCategory;
    ShopHome shopHome;
    Offer offerAdapter;
    ShopCategoryVendor shopCategoryVendor;
    SliderCategoryAdapter sliderCategoryAdapter;
    private String arrItems[];
    private String arrItemslist[] = {"a", "b", "c"};
    private final OnMeneuClickListnser onMenuListClicklistener;
    static int getPosition;
    private int row_index;
    Context mContext;
    int select;
    UserDetails userDetails;

    ArrayList<HomeData> homeDataArrayList;

    public HomeAdapter(ArrayList<HomeData> gethomeDataArrayList, OnMeneuClickListnser onLiveTestClickListener, Context context) {
        this.homeDataArrayList = gethomeDataArrayList;
        this.onMenuListClicklistener = onLiveTestClickListener;
        this.mContext = context;
        userDetails=new SqliteDatabase(context).getLogin();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        HomeData data = homeDataArrayList.get(position);
        holder.clickView.setOnClickListener(v->{
            onMenuListClicklistener.clickView( data,position);
        });

        if (position == 0) {
            holder.itemList.setHasFixedSize(true);
            holder.itemList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

            holder.tvText.setText("Top Offers");
            if(data.getOfferList()!=null) {
                holder.clickView.setVisibility(View.VISIBLE);
                offerAdapter = new Offer(data.getOfferList(), HomeAdapter.this::offerClickView, mContext);
                holder.itemList.setAdapter(offerAdapter);
            }
        } else if (position == 1) {
            holder.itemList.setHasFixedSize(true);
            holder.itemList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

            holder.tvText.setText("Top Ecommerce Category");
            if(data.getEcomlist()!=null) {
                shopCategory = new ShopCategory("home",data.getEcomlist(), HomeAdapter.this::catServiceClick, mContext);
                holder.itemList.setAdapter(shopCategory);
            }
        } else if (position == 2) {
            holder.itemList.setHasFixedSize(true);
            holder.itemList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

            holder.tvText.setText("Top Services Category");
            if(data.getServiceList()!=null){

                shopCategory = new ShopCategory("home",data.getServiceList(), HomeAdapter.this::catServiceClick, mContext);
                holder.itemList.setAdapter(shopCategory);
            }


        } else if (position == 3) {
            holder.itemList.setHasFixedSize(true);
            holder.itemList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));


            if(data.getSliderImages()!=null) {
                holder.clickView.setVisibility(View.GONE);
                holder.tvText.setText("Top Deal");
                sliderCategoryAdapter = new SliderCategoryAdapter(data.getSliderImages(), HomeAdapter.this::clickProductCategory, mContext);
                holder.itemList.setAdapter(sliderCategoryAdapter);
            }
        }else if (position == 4) {
            holder.itemList.setHasFixedSize(true);
            holder.itemList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

            holder.tvText.setText("Nearest  Shopes");
            if(data.getVendorData()!=null) {
                shopHome = new ShopHome("home",data.getVendorData(), HomeAdapter.this::offerClick, mContext);
                holder.itemList.setAdapter(shopHome);
            }
        }else if (position == 6) {
            holder.itemList.setHasFixedSize(true);
            holder.itemList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

            holder.tvText.setText("Nearest  E-commerce vendor ");
            if(data.getEcomCatVendorList()!=null) {
                shopCategoryVendor = new ShopCategoryVendor("home",data.getEcomCatVendorList(), HomeAdapter.this::clickShopCat, mContext);
                holder.itemList.setAdapter(shopCategoryVendor);
            }
        }
//        else if (position == 6) {
//            holder.tvText.setText("Nearest  Service vendor ");
//            if(data.getServiceCatVendorList()!=null) {
//                shopCategoryVendor = new ShopCategoryVendor("home",data.getServiceCatVendorList(), HomeAdapter.this::clickShopCat, mContext);
//                holder.itemList.setAdapter(shopCategoryVendor);
//            }
//        }


    }


    @Override
    public int getItemCount() {
        return homeDataArrayList.size();
    }

    @Override
    public void onOptionClick(String liveTest, int pos) {

    }



    @Override
    public void offerClickView(VendorDetails data, int pos, String type) {
       Intent mv =new Intent(mContext, SingleShopDetails.class);
        VendorData obj=new VendorData();
        obj.setUser_id(data.getUserId());
        obj.setWhislistStatus(data.getWhislistStatus());
        mv.putExtra("MyData", obj);
        mv.putExtra("categoryId", data.getpCategoryId());
          mContext.startActivity(mv);
    }

    @Override
    public void catServiceClick(Category liveTest, int pos, String type) {
        if(liveTest.getCategoryId().equalsIgnoreCase("29")){
            onMenuListClicklistener.clickService(liveTest,pos);
        }else{
            nearbyShop categoryObj = nearbyShop.getInstance(liveTest);
            ((HomeActivity) Objects.requireNonNull(mContext)).loadFragment(categoryObj, "");

        }

    }

    @Override
    public void offerClick(VendorData data, String type, String status) {

        if(type.equalsIgnoreCase("1")){
            Intent mv =new Intent(mContext, SingleShopDetails.class);
            mv.putExtra("MyData", data);
            mContext.startActivity(mv);

        }else{
            likeDislike(data,status);
        }

    }



    @Override
    public void clickProductCategory(SliderImage data, int pos, String type) {

        if(data.getType().equalsIgnoreCase("1")){
            Category catObj=new Category();
            catObj.setCategoryId(data.getQuery());
            nearbyShop categoryObj = nearbyShop.getInstance(catObj);
            ((HomeActivity) Objects.requireNonNull(mContext)).loadFragment(categoryObj, "");

        }else if(data.getType().equalsIgnoreCase("2")){

            Intent mv = new Intent(mContext, UserSearchActivity.class);
            mv.putExtra("type", "keySearch");
            mv.putExtra("keyWord", data.getQuery());
            // mv.putExtra("vendor_id", vendorDetails.getUserId());
            mContext.startActivity(mv);

        }else if(data.getType().equalsIgnoreCase("3")){
            Intent intent = new Intent(mContext, SliderProduct.class);
            intent.putExtra("query",data.getQuery());
            mContext.startActivity(intent);
        }else if(data.getType().equalsIgnoreCase("4")){
            Intent intent = new Intent(mContext, SliderByWeb.class);
            intent.putExtra("query",data.getQuery());
            mContext.startActivity(intent);
        }

    }

    @Override
    public void clickShopCat(HomeShopListCategory liveTest, int pos, String type) {

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        RecyclerView itemList;
        MaterialTextView tvText;
        MaterialTextView clickView;

        MyViewHolder(View view) {
            super(view);
            itemList = view.findViewById(R.id.itemList);
            tvText=view.findViewById(R.id.tvText);
            clickView=view.findViewById(R.id.clickView);
            itemList.setHasFixedSize(true);
            itemList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));


        }
    }

    public interface OnMeneuClickListnser {
        void onOptionClick(String liveTest, int pos);
        void clickView(HomeData liveTest, int pos);
        void clickService(Category  liveTest, int pos);
    }

    private void likeDislike(VendorData data, String active){
        if (InternetConnection.checkConnection(mContext)) {

            Map<String, String> params = new HashMap<>();
            params.put("user_id", userDetails.getUserId());
            params.put("vendor_id", data.getUser_id());

            params.put("active", data.getWhislistStatus());

            Log.e("params",params.toString());
            RestClient.post().likeShop(ApiService.APP_DEVICE_ID,userDetails.getSk(),params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message",object.getString("message"));

                        if (object.getBoolean("status")) {

                            shopHome.notifyDataSetChanged();

                        } else {
                            Util.show(mContext, "there is no shop available ");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
}


