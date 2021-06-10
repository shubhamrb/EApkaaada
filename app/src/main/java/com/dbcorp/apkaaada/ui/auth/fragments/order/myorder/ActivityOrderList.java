
package com.dbcorp.apkaaada.ui.auth.fragments.order.myorder;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.Adapter.myorder.MyOrderAdapter;
import com.dbcorp.apkaaada.Adapter.usercard.UserCardAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.BooleanModel;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.order.UserOrderModel;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityOrderList extends AppCompatActivity implements MyOrderAdapter.OnClickListener {

    Context mContext;


    UserDetails userDetails;
    RecyclerView orderd_list;
    ActivityOrderList listner;
    MyOrderAdapter myOrderAdapter;

    private Toolbar toolbar;


    ArrayList<UserOrderModel> listOrder;
    MaterialTextView tvApplyCoupon;
    LinearLayoutCompat layoutProceed;
    ArrayList<BooleanModel> showProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My Order");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userDetails = new SqliteDatabase(this).getLogin();
        this.mContext = this;
        listner=this;
        init();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private void init() {



        orderd_list = findViewById(R.id.orderd_list);

        orderd_list.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        orderd_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        orderDetails();


    }

    private void orderDetails() {
        showProduct=new ArrayList<>();
        listOrder=new ArrayList<>();
        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();
            params.put("userId", userDetails.getUserId());
            Log.e("param", params.toString());
            RestClient.post().getorder(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    try {
                        Gson gson = new Gson();
                        JSONObject object = new JSONObject(response.body());
                        Log.e("message",object.getString("message"));

                        if (object.getBoolean("status")) {

                            Type type = new TypeToken<ArrayList<UserOrderModel>>() {
                            }.getType();
                            listOrder = gson.fromJson(object.getJSONArray("listData").toString(), type);
                            myOrderAdapter=new MyOrderAdapter(listOrder,listner,mContext);
                            orderd_list.setAdapter(myOrderAdapter);
                        } else {
                            Util.show(mContext, "something is wrong");
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

    // open final order layout



    // open coupon layout
    @Override
    public void onClickCard(UserOrderModel list) {

    }
}