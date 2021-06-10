package com.dbcorp.apkaaada.ui.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import android.widget.Toast;

import com.dbcorp.apkaaada.R;

import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.OTP;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.network.Constants;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.Home.HomeActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    MaterialButton submitBtn;
    Context mContext;
    TextInputEditText editMobile,edit_email,edit_name,edit_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regster);
       getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        mContext=this;
        init();
        clickListener();

    }


    // initialization
    public void init(){
        submitBtn=findViewById(R.id.submit_btn);
        editMobile=findViewById(R.id.edit_mobile);
        edit_email=findViewById(R.id.edit_email);
        edit_name=findViewById(R.id.edit_name);
        edit_password=findViewById(R.id.edit_password);

    }
    // event listener
    public void clickListener(){
submitBtn.setOnClickListener(v->{

    if(edit_name.getText().length()==0 ){
        Util.show(mContext,"Please enter your name");
        return;
    }
    if(editMobile.getText().length()==0 || editMobile.getText().length()<10){
        Util.show(mContext,"Please enter valid mobile no");
        return;
    }
    if(edit_email.getText().length()==0 ){
        Util.show(mContext,"Please enter valid email");
        return;
    }

    if(edit_password.getText().length()==0 ){
        Util.show(mContext,"Please enter your password");
        return;
    }
    verifyOtp();

});


    }

    // Api Calling Function


    //1 . otp verify
    public void verifyOtp(){
        if (InternetConnection.checkConnection(mContext)) {

            Map<String, String> params = new HashMap<>();
            params.put("number", editMobile.getText().toString());

            RestClient.post().getOtp(params).enqueue(new Callback<OTP>() {
                @Override
                public void onResponse(Call<OTP> call, Response<OTP> response) {
                    Log.e("response",response.body().toString());
                    OTP obj = response.body();
                    Log.e("editMobile", obj.getStatus().toString());
                    if(obj.getStatus()){

                        otpWindow(obj.getTitle());
                    }else{
                        Util.show(mContext,"wrong id password");
                    }


                }

                @Override
                public void onFailure(Call<OTP> call, Throwable t) {

                    try {
                        t.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        }    }





    @SuppressLint("SetTextI18n")
    public void otpWindow(String msg) {

        View popupView = null;


        popupView = LayoutInflater.from(mContext).inflate(R.layout.otp_layout, null);


         int width = LinearLayout.LayoutParams.MATCH_PARENT;
         int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);
        TextInputEditText otpEdit =  popupView.findViewById(R.id.edit_otp);
        MaterialTextView tvMsg =  popupView.findViewById(R.id.message);
        MaterialButton btnVrify =  popupView.findViewById(R.id.verify_btn);
        tvMsg.setText("Kindly check your SMS send on +91 "+editMobile.getText().toString()+" and\n" +
                "your email "+edit_email.getText().toString()+"\n"+msg);


        btnVrify.setOnClickListener(v->{

            if(otpEdit.getText().length()<4){
                Util.show(mContext,"Please enter valid otp");
                return;
            }

            register(otpEdit.getText().toString());
        });


    }

    public void register(String otp){
        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();
            params.put("otp", otp);
            params.put("number", editMobile.getText().toString());
            params.put("name", edit_name.getText().toString());
            params.put("email", edit_email.getText().toString());
            params.put("password", edit_password.getText().toString());
            Log.e("data", params.toString());
            RestClient.post().userRegister("1234",params).enqueue(new Callback<UserDetails>() {
                @Override
                public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {

                    UserDetails obj = response.body();
                    Log.e("data", obj.getMessage());

                    startActivity(new Intent(mContext, Login.class));
                    finish();


                }

                @Override
                public void onFailure(Call<UserDetails> call, Throwable t) {

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
