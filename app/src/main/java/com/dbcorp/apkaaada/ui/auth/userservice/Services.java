package com.dbcorp.apkaaada.ui.auth.userservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.dbcorp.apkaaada.Adapter.InstructionAdapter;
import com.dbcorp.apkaaada.Adapter.service.ServiceAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.ServiceModel;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.card.Instruction;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
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

public class Services extends AppCompatActivity implements  ServiceAdapter.OnClickListener {


    Context mContext;
    Services listner;
    UserDetails userDetails;
    ServiceAdapter serviceAdapter;
    ArrayList<ServiceModel> list;
    RecyclerView listItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        init();
        listner=this;
        mContext=this;
        userDetails=new SqliteDatabase(this).getLogin();
    }
    private void init(){

        listItem=findViewById(R.id.listItem);
    }

    private void getServices() {
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
                            serviceAdapter = new ServiceAdapter(list, listner, mContext);
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