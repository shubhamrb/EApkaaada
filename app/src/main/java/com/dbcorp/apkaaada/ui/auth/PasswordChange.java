package com.dbcorp.apkaaada.ui.auth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.DroupDownModel;
import com.dbcorp.apkaaada.model.OTP;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordChange extends AppCompatActivity {

    MaterialButton submitBtn;
    UserDetails userDetails;
    Context mContext;
    TextInputEditText tvPass,tvCnPass;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Password Change");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        mContext=this;
        init();
        clickListener();

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    // initialization
    public void init(){
        userDetails = new SqliteDatabase(mContext).getLogin();
        submitBtn=findViewById(R.id.submit_btn);

        tvPass=findViewById(R.id.tvPass);
        tvCnPass=findViewById(R.id.tvCnPass);

    }
    // event listener
    public void clickListener(){
submitBtn.setOnClickListener(v->{

    if(tvPass.getText().length()==0 ){
        Util.show(mContext,"Please enter password");
        return;
    }
    if(tvCnPass.getText().toString().equalsIgnoreCase(tvPass.getText().toString()) ){
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
            Map<String, String> params = new HashMap<>();
            params.put("user_id", userDetails.getUserId());
            params.put("password", tvCnPass.getText().toString());

            RestClient.post().changePassword(userDetails.getSk(), ApiService.APP_DEVICE_ID,params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {


                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message", object.getString("message"));

                        if (object.getBoolean("status")) {

                            Util.show(mContext, "We have changed your paawsord");


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







}
