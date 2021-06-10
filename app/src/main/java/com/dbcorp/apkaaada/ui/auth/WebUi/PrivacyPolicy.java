package com.dbcorp.apkaaada.ui.auth.WebUi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.dbcorp.apkaaada.Adapter.NearByShopAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.VendorDetails;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
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

public class PrivacyPolicy extends AppCompatActivity {
    WebView webView;
    private Toolbar toolbar;
    MaterialTextView tvContent;
    Context mContext;
    Intent g;
    UserDetails userDetails;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        toolbar = findViewById(R.id.toolbar);
        userDetails=new SqliteDatabase(this).getLogin();
        g=getIntent();
        type=g.getStringExtra("type");
        toolbar.setTitle("Privacy Policy");
        setSupportActionBar(toolbar);
        mContext=this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvContent=findViewById(R.id.tvContent);

//        webView = findViewById(R.id.payumoney_webview);
//
//        webView.setVisibility(View.VISIBLE);
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webView.getSettings().setDomStorageEnabled(true);
//        webView.clearHistory();
//        webView.clearCache(true);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setSupportZoom(true);
//        webView.getSettings().setUseWideViewPort(false);
//        webView.getSettings().setLoadWithOverviewMode(false);
//
//        webView.loadUrl("http://bhssolution.com/Service.php");

        getData();
    }
    public void getData() {
        if (InternetConnection.checkConnection(mContext)) {


            Map<String, String> params = new HashMap<>();

            params.put("page_type","1");

            RestClient.post().getWebList(userDetails.getSk(), ApiService.APP_DEVICE_ID,params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message",object.getString("message"));

                        if (object.getBoolean("status")) {
                            JSONObject obj=object.getJSONObject("data");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                                tvContent.setText(Html.fromHtml(obj.getString("content"), Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                tvContent.setText(Html.fromHtml(obj.getString("content")));
                            }
                        } else {
                            Util.show(mContext, "No wishlist ");
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
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}