package com.dbcorp.apkaaada.ui.auth.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.dbcorp.apkaaada.Adapter.DailyNotificationAdapter;
import com.dbcorp.apkaaada.Adapter.NotificationAdapter;
import com.dbcorp.apkaaada.Adapter.offer.OfferAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.DailyNotification;
import com.dbcorp.apkaaada.model.NotificationModel;
import com.dbcorp.apkaaada.model.OfferModel;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.Offer.ViewOffer;
import com.google.android.material.button.MaterialButton;
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

public class NotificationActivity extends AppCompatActivity implements  NotificationAdapter.OnClickListener {

    ArrayList<NotificationModel> list;
    ArrayList<DailyNotification> listDaily;

    Context mContext;
    NotificationActivity listner;
    UserDetails userDetails;
    NotificationAdapter notificationAdapter;
    DailyNotificationAdapter dailyNotificationAdapter;
    RecyclerView listItem;
    MaterialButton order_noti_btn,daily_noti_btn;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        init();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private void init() {
        daily_noti_btn=findViewById(R.id.daily_noti_btn);
        order_noti_btn=findViewById(R.id.order_noti_btn);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My Notification");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listner = this;
        mContext = this;
        userDetails = new SqliteDatabase(this).getLogin();
        list = new ArrayList<>();
        listDaily=new ArrayList<>();
        listItem = findViewById(R.id.OfferlistItem);
        listItem.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(mContext, 3));
          listItem.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        getNoti();
        order_noti_btn.setOnClickListener(v->{
            toolbar.setTitle("Order Notification");
            getNoti();
        });
        daily_noti_btn.setOnClickListener(v->{
            toolbar.setTitle("Daily Notification");
            getDailyNotification();
        });
    }

    private void getNoti() {

        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();
            params.put("userId",userDetails.getUserId());
            list.clear();
            RestClient.post().getNotification(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    try {


                        Gson gson = new Gson();

                        JSONObject object = new JSONObject(response.body());
                        Log.e("message", object.getString("message"));


                        if (object.getBoolean("status")) {
                            Type productType = new TypeToken<ArrayList<NotificationModel>>() {
                            }.getType();
                            list = gson.fromJson(object.getJSONArray("notification").toString(), productType);
                            notificationAdapter = new NotificationAdapter(list, listner, mContext);
                            listItem.setAdapter(notificationAdapter);

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
    private void getDailyNotification() {
        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();

            listDaily.clear();
            RestClient.post().getDailyNotification(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    try {


                        Gson gson = new Gson();

                        JSONObject object = new JSONObject(response.body());
                        Log.e("message", object.getString("message"));


                        if (object.getBoolean("status")) {

                            Type productType = new TypeToken<ArrayList<DailyNotification>>() {
                            }.getType();
                            listDaily = gson.fromJson(object.getJSONArray("notification").toString(), productType);
                            dailyNotificationAdapter = new DailyNotificationAdapter(listDaily, mContext);
                            listItem.setAdapter(dailyNotificationAdapter);

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

    }
}