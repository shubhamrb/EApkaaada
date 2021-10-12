package com.dbcorp.apkaaada.ui.auth.WebUi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermsCondition extends AppCompatActivity {

    TextView textView;
    @SuppressLint("SetJavaScriptEnabled")
    private Toolbar toolbar;
    String pageType = "";
    private Context mContext;
    UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_condition2);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Terms and Condition");
        setSupportActionBar(toolbar);
        mContext = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView = findViewById(R.id.textView);
        userDetails = new SqliteDatabase(this).getLogin();
        pageType = getIntent().getStringExtra("pageType");
            callPageType(pageType);

    }

    private void callPageType(String pageType) {
        if (InternetConnection.checkConnection(mContext)) {

            Map<String, String> params = new HashMap<>();
            params.put("pageType", pageType);

            RestClient.post().getPageByPageType(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(response.body());
                        if (object.getBoolean("status")) {

                            String content = object.getJSONObject("data").getString("content");
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                textView.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY));
                            } else {
                                textView.setText(Html.fromHtml(content));
                            }

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