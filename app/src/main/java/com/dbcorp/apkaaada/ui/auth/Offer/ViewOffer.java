package com.dbcorp.apkaaada.ui.auth.Offer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.dbcorp.apkaaada.Adapter.offer.OfferAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.OfferModel;
import com.dbcorp.apkaaada.model.ServiceModel;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.fragments.ShopDetails;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewOffer extends AppCompatActivity implements OfferAdapter.OnClickListener {
    Context mContext;
    ViewOffer listner;
    UserDetails userDetails;
    OfferAdapter offerAdapter;
    ArrayList<OfferModel> list;
    RecyclerView listItem;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My Offer");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listner=this;
        mContext=this;
        userDetails=new SqliteDatabase(this).getLogin();
        init();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
    private void init(){
        list=new ArrayList<>();
        listItem=findViewById(R.id.OfferlistItem);
        listItem.setHasFixedSize(true);
         listItem.setLayoutManager(new GridLayoutManager(mContext, 2));
      //  listItem.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        getOffer();
    }
    private void getOffer() {
        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();

            RestClient.post().getOffer(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    try {


                        Gson gson = new Gson();

                        JSONObject object = new JSONObject(response.body());
                        Log.e("message", object.getString("message"));


                        if (object.getBoolean("status")) {
                            Type productType = new TypeToken<ArrayList<OfferModel>>() {
                            }.getType();
                            list = gson.fromJson(object.getJSONArray("offer").toString(), productType);
                            offerAdapter = new OfferAdapter(list, listner, mContext);
                            listItem.setAdapter(offerAdapter);

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
    public void onClickCard(OfferModel list) {
        Intent mv =new Intent(this, OfferShopDetails.class);
        mv.putExtra("MyData", list);


        Objects.requireNonNull(this).getIntent().getSerializableExtra("MyData");
        startActivity(mv);
    }
}