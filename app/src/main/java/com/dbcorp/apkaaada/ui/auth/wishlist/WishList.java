package com.dbcorp.apkaaada.ui.auth.wishlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.dbcorp.apkaaada.Adapter.NearByShopAdapter;
import com.dbcorp.apkaaada.Adapter.service.ServiceAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.ServiceModel;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.VendorDetails;
import com.dbcorp.apkaaada.model.home.Category;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.fragments.ServiceShop;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishList extends AppCompatActivity implements  NearByShopAdapter.OnMeneuClickListnser {
    Context mContext;
    WishList listner;
    UserDetails userDetails;
    ServiceAdapter serviceAdapter;
    ArrayList<ServiceModel> list;
    RecyclerView listItem;
    ArrayList<VendorDetails> vendorDetailsList;
    NearByShopAdapter nearByShopAdapter;
    Category categoryData;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_liat);
        listner=this;
        mContext=this;
        init();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My WishList Shop");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }
    private void init(){
        userDetails = new SqliteDatabase(mContext).getLogin();
        listItem=findViewById(R.id.wishlist);
        listItem.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        listItem.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        getWishList();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void getWishList() {
        if (InternetConnection.checkConnection(mContext)) {

            vendorDetailsList=new ArrayList<>();
            listItem.setVisibility(View.GONE);
            Map<String, String> params = new HashMap<>();

            params.put("userId",userDetails.getUserId());
            Log.e("message",userDetails.getUserId());
            RestClient.post().getWish(userDetails.getSk(), ApiService.APP_DEVICE_ID,params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message",object.getString("message"));

                        if (object.getBoolean("status")) {
                            listItem.setVisibility(View.VISIBLE);
                            Type type = new TypeToken<ArrayList<VendorDetails>>() {
                            }.getType();
                            vendorDetailsList = gson.fromJson(object.getJSONArray("listData").toString(), type);
                            nearByShopAdapter = new NearByShopAdapter(vendorDetailsList, listner, mContext);
                            listItem.setAdapter(nearByShopAdapter);

                        } else {
                            Util.show(mContext, "No wishlist ");
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

    @Override
    public void onOptionClick(VendorDetails data, int pos) {
        Log.e("getMasterCategoryId", data.getMasterCategoryId());
        if(data.getMasterCategoryId().equalsIgnoreCase("1")){
            Intent mv =new Intent(this, ServiceShop.class);
            mv.putExtra("MyData", data);

            Objects.requireNonNull(this).getIntent().getSerializableExtra("MyData");
            startActivity(mv);
        }else{
            Intent mv =new Intent(this, WishlistShopDetails.class);
            mv.putExtra("MyData", data);
            Objects.requireNonNull(this).getIntent().getSerializableExtra("MyData");
            startActivity(mv);
        }

    }

    @Override
    public void onHeartClick(VendorDetails data,String status) {
        likeDislike(data,status);
    }

    private void likeDislike(VendorDetails data,String active){
        if (InternetConnection.checkConnection(mContext)) {

            Map<String, String> params = new HashMap<>();
            params.put("user_id", userDetails.getUserId());
            params.put("vendor_id", data.getUserId());

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

                            nearByShopAdapter.notifyDataSetChanged();
                            getWishList();
                        } else {
                            Util.show(mContext, "something is wrong");
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