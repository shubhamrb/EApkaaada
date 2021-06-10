package com.dbcorp.apkaaada.ui.auth.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.dbcorp.apkaaada.Adapter.NearByShopAdapter;
import com.dbcorp.apkaaada.Adapter.SearchProductAdapter;
import com.dbcorp.apkaaada.Adapter.SliderAdaptercarouseList;
import com.dbcorp.apkaaada.Adapter.shopview.ServiceAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.Carousel;
import com.dbcorp.apkaaada.model.Carouseloffer;
import com.dbcorp.apkaaada.model.OTP;
import com.dbcorp.apkaaada.model.SearchByProduct;
import com.dbcorp.apkaaada.model.UserDetails;

import com.dbcorp.apkaaada.model.VendorDetails;
import com.dbcorp.apkaaada.model.shopview.VendorService;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.fragments.product.shopview;
import com.dbcorp.apkaaada.ui.auth.userservice.ChatBookingList;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
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
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceShop extends AppCompatActivity {


    private Toolbar toolbar;
    Intent g;

    private Context mContext;
    private UserDetails userDetails;
    private ArrayList<VendorService> servicesList;
    private RecyclerView serviceList;
    private VendorDetails vendorDetails;
    private Intent getInent;
    private MaterialTextView tvShopName,tvAbout,makeCall,chatNow,bookNow;
    private ServiceAdapter serviceAdapter;
    private ArrayList<Carouseloffer> listCarousel;
    SliderAdaptercarouseList sliderAdapter;
    private ViewPager viewPager;
    TabLayout indicator;
    Timer timer;
    String shopMobile="";
    PopupWindow popupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_shop_view_fragment);
        mContext=this;
        userDetails=new SqliteDatabase(this).getLogin();
        getInent = getIntent();
        vendorDetails = (VendorDetails) getInent.getSerializableExtra("MyData");

        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Service Details");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        g = getIntent();
        init();

    }

    private void init(){
        servicesList=new ArrayList<>();
        tvShopName=findViewById(R.id.tvShopName);
        serviceList=findViewById(R.id.serviceList);
        tvAbout=findViewById(R.id.tvAbout);
        bookNow=findViewById(R.id.bookNow);
        chatNow=findViewById(R.id.chatNow);
        makeCall=findViewById(R.id.makeCall);
        listCarousel = new ArrayList<>();
        viewPager = findViewById(R.id.viewPager);
        indicator = findViewById(R.id.indicator);
        serviceList.setHasFixedSize(true);
        serviceList.setLayoutManager(new GridLayoutManager(mContext, 2));
        //serviceList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        makeCall.setOnClickListener(v->{
            if(isPermissionGranted()){
                call_action();
            }

        });
        serviceList();
        chatNow.setOnClickListener(v->{
            addChatBooking();
        });
        bookNow.setOnClickListener(v->{
            addBooking();
        });
    }

    // funtion for calling on make call btn
    public void call_action(){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + shopMobile));
        startActivity(callIntent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    call_action();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void serviceList(){
        if (InternetConnection.checkConnection(mContext)) {

            Map<String, String> params = new HashMap<>();
            params.put("vendorId", vendorDetails.getUserId());

            RestClient.post().getServiceShop(userDetails.getSk(), ApiService.APP_DEVICE_ID,params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message",object.getString("message"));

                        if (object.getBoolean("status")) {

                            Type type = new TypeToken<ArrayList<VendorService>>() {
                            }.getType();
                            servicesList = gson.fromJson(object.getJSONArray("serviceList").toString(), type);
                            serviceAdapter = new ServiceAdapter(servicesList,   mContext);
                            serviceList.setAdapter(serviceAdapter);
                            tvShopName.setText(object.getJSONArray("shopDetails").getJSONObject(0).getString("name"));
                            tvAbout.setText(object.getJSONArray("shopDetails").getJSONObject(0).getString("about"));
                            shopMobile=object.getJSONArray("shopDetails").getJSONObject(0).getString("number");


                            Carouseloffer objSlider=new Carouseloffer();
                            objSlider.setId("");
                            objSlider.setTitle("");
                            objSlider.setImage(object.getJSONArray("shopDetails").getJSONObject(0).getString("photo"));
                            listCarousel.add(objSlider);
                            sliderAdapter = new SliderAdaptercarouseList(mContext, listCarousel);
                            viewPager.setAdapter(sliderAdapter);
                            indicator.setupWithViewPager(viewPager, true);
                            sliderAdapter.notifyDataSetChanged();
                            timer = new Timer();
                            timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);

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


    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            ((ServiceShop) mContext).runOnUiThread(() -> {
                if (viewPager.getCurrentItem() < listCarousel.size() - 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else {
                    viewPager.setCurrentItem(0);
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    public void addChatBooking() {

        View popupView = null;


        popupView = LayoutInflater.from(mContext).inflate(R.layout.chat_booking, null);


        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
           popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(popupView, Gravity.TOP, 0, 0);
        TextInputEditText edit_name =  popupView.findViewById(R.id.edit_name);

        MaterialTextView tvClose=popupView.findViewById(R.id.tvClose);
        TextInputEditText edt_mobile =  popupView.findViewById(R.id.edit_mobile);


        MaterialTextView btnContinue =  popupView.findViewById(R.id.btnContinue);


        tvClose.setOnClickListener(v->{
            popupWindow.dismiss();
        });
        btnContinue.setOnClickListener(v->{

            if(edt_mobile.getText().length()<10){
                Util.show(mContext,"Please enter valid mobile");
                return;
            }
            verifyOtp(edt_mobile.getText().toString());


         });


    }
    //open chat booking form
    @SuppressLint("SetTextI18n")
    public void addBooking() {

        View popupView = null;


        popupView = LayoutInflater.from(mContext).inflate(R.layout.booking_service_form, null);


        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(popupView, Gravity.TOP, 0, 0);
        TextInputEditText edit_name =  popupView.findViewById(R.id.edit_name);

        MaterialTextView tvClose=popupView.findViewById(R.id.tvClose);
        TextInputEditText edt_mobile =  popupView.findViewById(R.id.edit_mobile);
        TextInputEditText edit_email =  popupView.findViewById(R.id.edit_email_id);

        TextInputEditText edit_house =  popupView.findViewById(R.id.edit_house);
        TextInputEditText edit_street =  popupView.findViewById(R.id.edit_street);
        TextInputEditText edit_pinCode =  popupView.findViewById(R.id.edit_pinCode);

        MaterialTextView btnContinue =  popupView.findViewById(R.id.btnContinue);


        tvClose.setOnClickListener(v->{
            popupWindow.dismiss();
        });
        btnContinue.setOnClickListener(v->{

            if(edit_pinCode.getText().length()<6){
                Util.show(mContext,"Please enter valid pincode");
                return;
            }

            if(edit_name.getText().length()==0){
                Util.show(mContext,"Please enter name");
                return;
            }

            if(edt_mobile.getText().length()==0){
                Util.show(mContext,"Please enter mobile");
                return;
            }
            if(edit_street.getText().length()==0){
                Util.show(mContext,"Please enter street name");
                return;
            }
            if(edit_house.getText().length()==0){
                Util.show(mContext,"Please enter house name");
                return;
            }

            bookService(edit_name.getText().toString(),edt_mobile.getText().toString(),edit_email.getText().toString(),edit_house.getText().toString(),edit_street.getText().toString(),edit_pinCode.getText().toString());
        });


    }

    // otp verify
    public void verifyOtp(String mobile){
        if (InternetConnection.checkConnection(mContext)) {

            Map<String, String> params = new HashMap<>();
            params.put("number", mobile);

            RestClient.post().getOtp(params).enqueue(new Callback<OTP>() {
                @Override
                public void onResponse(Call<OTP> call, Response<OTP> response) {
                    Log.e("response",response.body().toString());
                    OTP obj = response.body();
                    Log.e("editMobile", obj.getStatus().toString());
                    if(obj.getStatus()){
                        popupWindow.dismiss();
                        otpWindow(obj.getMessage(),mobile);

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
    public void otpWindow(String msg,String mobile) {

        View popupView = null;


        popupView = LayoutInflater.from(mContext).inflate(R.layout.otp_layout, null);


        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);
        TextInputEditText otpEdit =  popupView.findViewById(R.id.edit_otp);
        MaterialTextView tvMsg =  popupView.findViewById(R.id.message);
        MaterialButton btnVrify =  popupView.findViewById(R.id.verify_btn);
        tvMsg.setText("Kindly check your SMS send on +91 "+mobile.toString()+" and\n" +
                "your email "+mobile.toString()+"\n"+msg);


        btnVrify.setOnClickListener(v->{

            if(otpEdit.getText().length()<4){
                Util.show(mContext,"Please enter valid otp");
                return;
            }
            chatBooking(mobile);

        });


    }

    private void chatBooking(String mobile){
        if (InternetConnection.checkConnection(mContext)) {

            Map<String, String> params = new HashMap<>();
            params.put("user_id", userDetails.getUserId());
            params.put("vendor_id", vendorDetails.getUserId());
            params.put("user_mobile",  mobile);
            params.put("status",  "0");
            Log.e("param",params.toString());
            RestClient.post().addChattingBooking(ApiService.APP_DEVICE_ID,userDetails.getSk(),params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message",object.getString("message"));

                        if (object.getBoolean("status")) {
                            popupWindow.dismiss();
                            Util.show(mContext,"We have booked your service");
                            startActivity(new Intent(ServiceShop.this, ChatBookingList.class));
                            finish();
                        } else {
                            Util.show(mContext, "something is wrong");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                        Util.show(mContext, e.getMessage());
                    }


                }

                @Override
                public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                    try {
                        t.printStackTrace();
                    } catch (Exception e) {
                        Util.show(mContext, e.getMessage());
                        e.printStackTrace();
                    }

                }
            });

        }
    }

    private  void bookService(String name,String mobile,String email,String house,String street,String pincode){
        if (InternetConnection.checkConnection(mContext)) {

            Map<String, String> params = new HashMap<>();
            params.put("name", name);
            params.put("mobile",mobile);
            params.put("email", email);
            params.put("pincode", pincode);
            params.put("house_address", house);
            params.put("vendor_id", vendorDetails.getUserId());

            params.put("street_name", street);
            params.put("add_by", userDetails.getUserId());
            RestClient.post().addServiceShop(userDetails.getSk(), ApiService.APP_DEVICE_ID,params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message",object.getString("message"));

                        if (object.getBoolean("status")) {
                            Util.show(mContext,"We have booked your service");
                            finish();
                        } else {
                            Util.show(mContext, "something is wrong");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                        Util.show(mContext, e.getMessage());
                    }


                }

                @Override
                public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                    try {
                        t.printStackTrace();
                    } catch (Exception e) {
                        Util.show(mContext, e.getMessage());
                        e.printStackTrace();
                    }

                }
            });

        }
    }
}