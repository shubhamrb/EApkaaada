package com.dbcorp.apkaaada.ui.auth.fragments.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.Adapter.ShopHome;
import com.dbcorp.apkaaada.Adapter.offer.OfferAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.OfferModel;
import com.dbcorp.apkaaada.model.ServiceModel;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.VendorDetails;
import com.dbcorp.apkaaada.model.home.HomeData;
import com.dbcorp.apkaaada.model.home.VendorData;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearestShop extends AppCompatActivity implements ShopHome.OnClickListener {
    Context mContext;
    NearestShop listner;
    UserDetails userDetails;
    ShopHome shopHome;

    RecyclerView listItem;
    private Toolbar toolbar;
    ArrayList<VendorData> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Nearest Shop");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listner=this;
        mContext=this;
        userDetails=new SqliteDatabase(this).getLogin();
        init();
    }
    private void init(){
        list=new ArrayList<>();
        listItem=findViewById(R.id.OfferlistItem);

        listItem.setHasFixedSize(true);
         listItem.setLayoutManager(new GridLayoutManager(mContext, 2));
        //listItem.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        getVendor();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
    private void getVendor() {
        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();
            params.put("userId",userDetails.getUserId());

            RestClient.post().getNearest(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    try {


                        Gson gson = new Gson();

                        JSONObject object = new JSONObject(response.body());
                        Log.e("message", object.getString("message"));


                        if (object.getBoolean("status")) {
                            Type productType = new TypeToken<ArrayList<VendorData>>() {
                            }.getType();
                            list = gson.fromJson(object.getJSONArray("vendorData").toString(), productType);
                            shopHome = new ShopHome("list",list, listner, mContext);
                            listItem.setAdapter(shopHome);

                        }
                    } catch (Exception e) {

                        Util.show(mContext, e.getMessage());
                    }
//                    OTP obj = response.body();
                    // assert obj != null;
                    //Log.e("data", obj.getMessage());
                    //otpWindow(obj.getTitle());

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
    public void offerClick(VendorData data, String type, String status) {
        if(type.equalsIgnoreCase("1")){
            Intent mv =new Intent(mContext, SingleShopDetails.class);
            mv.putExtra("MyData", data);
            mContext.startActivity(mv);

        }else{
            likeDislike(data,status);
        }
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