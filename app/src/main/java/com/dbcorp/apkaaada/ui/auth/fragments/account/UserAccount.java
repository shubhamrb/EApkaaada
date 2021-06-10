package com.dbcorp.apkaaada.ui.auth.fragments.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.dbcorp.apkaaada.Adapter.AddressListAdapter;
import com.dbcorp.apkaaada.Adapter.MyComplainAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.MyComplain;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.card.UserAddress;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.Home.HomeActivity;
import com.dbcorp.apkaaada.ui.auth.Login;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
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

public class UserAccount extends AppCompatActivity implements AddressListAdapter.OnMeneuClickListnser{
    UserAccount listner;
    AppCompatImageView tvEdit;
    private UserDetails userDetails;
    MaterialTextView tvName,tvMobile,tvEmail,tvLogout;
    Context mContext;
    AddressListAdapter addressListAdapter;
    private Toolbar toolbar;
    RecyclerView listGreen,listRed;
    ArrayList<UserAddress> listAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Account");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.mContext=this;
        this.listner=this;
        userDetails = new SqliteDatabase(mContext).getLogin();
        init();
    }
    public void init(){
        tvName=findViewById(R.id.tvName);
        tvEdit=findViewById(R.id.tvEdit);
        tvMobile=findViewById(R.id.tvMobile);
        tvLogout=findViewById(R.id.tvLogout);
        tvEmail=findViewById(R.id.tvEmail);
        listRed=findViewById(R.id.listRed);
        listGreen=findViewById(R.id.listGreen);
        tvName.setText(userDetails.getName());
        tvMobile.setText(userDetails.getNumber());
        tvEmail.setText(userDetails.getEmail());
        listGreen.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        listGreen.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        listRed.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        listRed.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        tvEdit.setOnClickListener(v->{
            otpWindow();
        });
        tvLogout.setOnClickListener(v->{
            Logout();
        });
        getSearchData();
    }
    private void Logout() {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    try {
                        new SqliteDatabase(mContext).removeLoginUser();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(UserAccount.this, Login.class));
                    finish();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        };
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UserAccount.this);
        builder.setMessage("Do you want to logout from the app?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    private void getSearchData() {
        if (InternetConnection.checkConnection(mContext)) {
            listAddress=new ArrayList<>();
            Map<String, String> params = new HashMap<>();
            params.put("userId", userDetails.getUserId());
            Log.e("param",params.toString());

            RestClient.post().getUserAddress(userDetails.getSk(), ApiService.APP_DEVICE_ID,params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {


                    Gson gson = new Gson();

                    try {

                        JSONObject object = new JSONObject(response.body());

                        if (object.getBoolean("status")) {


                            Type list = new TypeToken<ArrayList<UserAddress>>() {
                            }.getType();

                            Log.e("message", object.getJSONArray("workaddress").toString());

                            listAddress = gson.fromJson(object.getJSONArray("workaddress").toString(), list);
                            addressListAdapter=new AddressListAdapter("red",listAddress,listner,mContext);
                            listRed.setAdapter(addressListAdapter);

                            listAddress = gson.fromJson(object.getJSONArray("homeaddress").toString(), list);
                            addressListAdapter=new AddressListAdapter("green",listAddress,listner,mContext);
                            listGreen.setAdapter(addressListAdapter);


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
    public void onOptionClick(String liveTest, int pos) {

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
    @SuppressLint("SetTextI18n")
    public void otpWindow() {

        View popupView = null;


        popupView = LayoutInflater.from(mContext).inflate(R.layout.layout_profile_edit, null);


        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.white_gredient_bg));
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        TextInputLayout otpEdit =  popupView.findViewById(R.id.editName);
        TextInputLayout edtEmail =  popupView.findViewById(R.id.edtEmail);
        AppCompatImageView tvClose=popupView.findViewById(R.id.tvClose);

otpEdit.getEditText().setText(userDetails.getName());
        edtEmail.getEditText().setText(userDetails.getEmail());
        MaterialButton btnVrify =  popupView.findViewById(R.id.submit_btn);

        tvClose.setOnClickListener(v->{
            popupWindow.dismiss();
        });

        btnVrify.setOnClickListener(v->{

            if (otpEdit.getEditText().getText().toString().length()==0){

                Util.show(mContext,"Please enter name");
                return;
            }

            if (edtEmail.getEditText().getText().toString().length()==0){
                Util.show(mContext,"Please enter email");
                return;
            }

            updateData(otpEdit.getEditText().getText().toString(),edtEmail.getEditText().getText().toString());
        });


    }



    public void updateData(String name,String mail){
        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();

            params.put("name", name);
            params.put("email", mail);
            params.put("user_id", userDetails.getUserId());
            Log.e("data", params.toString());
            RestClient.post().updateData(userDetails.getSk(), ApiService.APP_DEVICE_ID,params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {


                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message", object.getString("message"));

                        if (object.getBoolean("status")) {

                            Util.show(mContext, object.getString("message"));

                            userDetails.setEmail(mail);
                            userDetails.setName(name);
                            new SqliteDatabase(mContext).updateLogin(userDetails);
                            userDetails = new SqliteDatabase(mContext).getLogin();
                       init();
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