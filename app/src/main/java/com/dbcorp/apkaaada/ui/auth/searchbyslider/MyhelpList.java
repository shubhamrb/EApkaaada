package com.dbcorp.apkaaada.ui.auth.searchbyslider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.Adapter.MyComplainAdapter;
import com.dbcorp.apkaaada.Adapter.SearchProductAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.MyComplain;
import com.dbcorp.apkaaada.model.SearchByProduct;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.shopview.Product;
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

public class MyhelpList extends AppCompatActivity implements  MyComplainAdapter.OnMeneuClickListnser{
    Intent g;
    String categoryName="";
    MyhelpList listner;
    UserDetails userDetails;
    private Toolbar toolbar;
    ArrayList<MyComplain> searchByProducts;
    MyComplainAdapter searchProduct;

    ArrayList<SearchByProduct> filterSearchByProducts;
    RecyclerView listProduct;
    Context mContext;
    LinearLayoutCompat layoutSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_by_product);
        mContext=this;
        listner=this;
        userDetails=new SqliteDatabase(this).getLogin();
        listProduct=findViewById(R.id.listProduct);
        layoutSearch=findViewById(R.id.layoutSearch);
        layoutSearch.setVisibility(View.GONE);
        listProduct.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        listProduct.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        g=getIntent();
        categoryName=g.getStringExtra("query");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Help List");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSearchData();
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
    private void getSearchData() {
        if (InternetConnection.checkConnection(mContext)) {
            filterSearchByProducts=new ArrayList<>();
            searchByProducts=new ArrayList<>();
            Map<String, String> params = new HashMap<>();
            params.put("userId", userDetails.getUserId());

            Log.e("param",params.toString());
            RestClient.post().helpList(userDetails.getSk(), ApiService.APP_DEVICE_ID,params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {


                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message", object.getString("message"));

                        if (object.getBoolean("status")) {


                                Type productList = new TypeToken<ArrayList<MyComplain>>() {
                                }.getType();
                                searchByProducts = gson.fromJson(object.getJSONArray("list").toString(), productList);
                                filterSearchByProducts= gson.fromJson(object.getJSONArray("list").toString(), productList);
                            searchProduct=new MyComplainAdapter(searchByProducts,listner,mContext);
                                listProduct.setAdapter(searchProduct);


                        } else {
                            Util.show(mContext, object.getString("message"));
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