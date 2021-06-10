package com.dbcorp.apkaaada.ui.auth.userservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.dbcorp.apkaaada.Adapter.NearByShopAdapter;
import com.dbcorp.apkaaada.Adapter.chat.ChatingRequestAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.ServiceModel;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.VendorDetails;
import com.dbcorp.apkaaada.model.chat.BookingServiceChat;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.Home.HomeActivity;
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

public class ChatBookingList extends AppCompatActivity implements  ChatingRequestAdapter.OnClickListener {

    ChatingRequestAdapter  chatingRequestAdapter;
    ArrayList<BookingServiceChat> userList;
    Context mContext;
    UserDetails userDetails;
    ChatBookingList listner;
    RecyclerView listView;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_booking_list);
        listner=this;
mContext=this;
        userDetails=new SqliteDatabase(this).getLogin();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My Chat Request List");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }
    private void init(){
        listView=findViewById(R.id.listView);
        userList=new ArrayList<>();
        getChatRequest();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
    private  void getChatRequest(){
        if (InternetConnection.checkConnection(mContext)) {

            Map<String, String> params = new HashMap<>();
            params.put("userId", userDetails.getUserId());


            RestClient.post().getBooking(ApiService.APP_DEVICE_ID,userDetails.getSk(),params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message",object.getString("message"));

                        if (object.getBoolean("status")) {

                            Type type = new TypeToken<ArrayList<BookingServiceChat>>() {
                            }.getType();
                            userList = gson.fromJson(object.getJSONArray("serviceChat").toString(), type);
                            chatingRequestAdapter = new ChatingRequestAdapter(userList, listner, mContext);
                            listView.setAdapter(chatingRequestAdapter);

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

    @Override
    public void onClickCard(BookingServiceChat list) {


        Intent mv3= new Intent(ChatBookingList.this, ChatActivity.class);
        mv3.putExtra("myData",list);
        startActivity(mv3);
    }
}