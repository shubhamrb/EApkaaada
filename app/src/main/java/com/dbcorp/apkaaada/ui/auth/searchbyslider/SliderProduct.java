package com.dbcorp.apkaaada.ui.auth.searchbyslider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.Adapter.SearchProductAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.SearchByProduct;
import com.dbcorp.apkaaada.model.UserDetails;
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

public class SliderProduct extends AppCompatActivity implements  SearchProductAdapter.OnClickListener{
    Intent g;
    String categoryName="";
    SliderProduct listner;
    UserDetails userDetails;
    private Toolbar toolbar;
    ArrayList<SearchByProduct> searchByProducts;
    SearchProductAdapter searchProduct;

    ArrayList<SearchByProduct> filterSearchByProducts;
    RecyclerView listProduct;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_by_product);
        mContext=this;
        listner=this;
        userDetails=new SqliteDatabase(this).getLogin();
        listProduct=findViewById(R.id.listProduct);
        listProduct.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        listProduct.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        g=getIntent();
        categoryName=g.getStringExtra("query");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Search Product");
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
            params.put("id", categoryName);

            Log.e("param",params.toString());
            RestClient.post().searchSliderProduct(userDetails.getSk(), ApiService.APP_DEVICE_ID,params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {


                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message", object.getString("message"));

                        if (object.getBoolean("status")) {


                                Type productList = new TypeToken<ArrayList<SearchByProduct>>() {
                                }.getType();
                                searchByProducts = gson.fromJson(object.getJSONArray("productList").toString(), productList);
                                filterSearchByProducts= gson.fromJson(object.getJSONArray("productList").toString(), productList);
                            searchProduct=new SearchProductAdapter("Shop",searchByProducts,listner,mContext);
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



    @Override
    public void productRemove(SearchByProduct liveTest, int pos) {

    }

    @Override
    public void productClick(SearchByProduct liveTest, int pos) {

    }

    @Override
    public void addCartClick(SearchByProduct liveTest, int pos, String subAdd, ArrayList<SearchByProduct> arrayList, View showProduct) {

    }
}