package com.dbcorp.apkaaada.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.Home.HomeActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dbcorp.apkaaada.network.ApiService.APP_DEVICE_ID;
import static com.dbcorp.apkaaada.network.ApiService.APP_USER_TYPE;

public class ForgetActivity extends AppCompatActivity {
    TextInputLayout  edit_mobile,edit_pass,edit_cnpass;
    Context mContext;
    MaterialButton submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        init();
    }

    public void init(){
        edit_mobile=findViewById(R.id.edit_mobile);
        edit_pass=findViewById(R.id.edit_pass);
        edit_cnpass=findViewById(R.id.edit_cnpass);
        submit_btn=findViewById(R.id.submit_btn);
    }


    // event listener
    public void clickListener(){
        submit_btn.setOnClickListener(v->{


            if(edit_mobile.getEditText().getText().length()==0){
                Util.show(mContext,"Please enter valid mobile");
                return;
            }

            if(edit_pass.getEditText().getText().length()==0 ){
                Util.show(mContext,"Please enter your password");
                return;
            }
            if(edit_cnpass.getEditText().getText().toString().equalsIgnoreCase(edit_pass.getEditText().getText().toString()) ){
                updatePassword();
            }else{
                Util.show(mContext,"Please enter confirm password");
                return;
            }



        });


    }

    // Api Calling Function
    private void updatePassword() {
        if (InternetConnection.checkConnection(mContext)) {
            Util.showDialog("Please wait",mContext);
            Map<String, String> params = new HashMap<>();
            params.put("userMobile", edit_mobile.getEditText().getText().toString());
            params.put("password", edit_cnpass.getEditText().getText().toString());

            RestClient.post().forGetPassword(ApiService.APP_DEVICE_ID,params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message", object.getString("message"));

                        if (object.getBoolean("status")) {

                            Util.show(mContext, "We have changed your password");
Util.hideDialog();
                        } else {
                            Util.show(mContext, "something is wrong");
                            Util.hideDialog();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Util.hideDialog();
                    }


                }

                @Override
                public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                    try {
                        t.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Util.hideDialog();

                }
            });

        }
    }
}