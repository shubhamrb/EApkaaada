package com.dbcorp.apkaaada.ui.auth.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.dbcorp.apkaaada.Adapter.order.OrderAdapter;
import com.dbcorp.apkaaada.Adapter.service.ServiceAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.ServiceModel;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.order.OrderModel;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.userservice.Services;
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

public class OrderDashBoard extends AppCompatActivity implements  OrderAdapter.OnClickListener{
    Context mContext;
    OrderDashBoard listner;
    UserDetails userDetails;
    OrderAdapter serviceAdapter;
    ArrayList<OrderModel> list;
    RecyclerView listItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_dash_board);
        init();
        listner=this;
        mContext=this;
        userDetails=new SqliteDatabase(this).getLogin();
    }
    private void init(){

        listItem=findViewById(R.id.orderd);
    }
    private void getorder() {
        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();

            RestClient.post().getInstruction(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    try {


                        Gson gson = new Gson();

                        JSONObject object = new JSONObject(response.body());
                        Log.e("message", object.getString("message"));


                        if (object.getBoolean("status")) {
                            Type productType = new TypeToken<ArrayList<ServiceModel>>() {
                            }.getType();
                            list = gson.fromJson(object.getJSONArray("serviceList").toString(), productType);
                            serviceAdapter = new OrderAdapter(list, listner, mContext);
                            listItem.setAdapter(serviceAdapter);

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
    public void onClickCard(ServiceModel list) {

    }
}