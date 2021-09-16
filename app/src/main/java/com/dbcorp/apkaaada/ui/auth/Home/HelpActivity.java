package com.dbcorp.apkaaada.ui.auth.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.dbcorp.apkaaada.Adapter.DroupdownMenuAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.CameraUtils;
import com.dbcorp.apkaaada.helper.ImageGalleryCode;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.DroupDownModel;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpActivity extends AppCompatActivity implements DroupdownMenuAdapter.OnMeneuClickListnser
{

    Context mContext;
    MaterialTextView tvDrop;
    ArrayList <DroupDownModel> list;
    UserDetails userDetails;
    HelpActivity listner;
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    public static final int PICK_IMAGE_CHOOSER_REQUEST_CODE = 200;
    EditText tvMessage;
    ImageGalleryCode imageGalleryCode;
    static Bitmap bitmapString;
    static String getBitmapstr="",orderId="";
    public static final int BITMAP_SAMPLE_SIZE = 0;
    MaterialButton attached_btn,submit_btn;
    FrameLayout frameId;
    private Toolbar toolbar;
    ShapeableImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_support_acctivity);
        userDetails = new SqliteDatabase(this).getLogin();
        mContext=this;
        listner=this;
        list=new ArrayList<>();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Help & Support");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
    void init(){
        frameId=findViewById(R.id.frameId);
        tvDrop=findViewById(R.id.tvDrop);
        imageView=findViewById(R.id.imageView);
        tvMessage=findViewById(R.id.tvMessage);
        submit_btn=findViewById(R.id.submit_btn);
        attached_btn=findViewById(R.id.attached_btn);
        getOrderIdes();
        submit_btn.setOnClickListener(v->{
            orderComplaint();
        });
        tvDrop.setOnClickListener(v->{
            Util.showDropDown(list,"Select Order Number",mContext,listner);
        });
        attached_btn.setOnClickListener(v->{
            ImagePicker.Companion.with(this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void getOrderIdes() {
        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();
            params.put("userId", userDetails.getUserId());



            RestClient.post().orderIdes(userDetails.getSk(), ApiService.APP_DEVICE_ID,params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {


                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message", object.getString("message"));

                        if (object.getBoolean("status")) {


                            JSONArray jsonArray = object.getJSONArray("idesList");
//                            Type type = new TypeToken<ArrayList<Coupon>>() {
//                            }.getType();
//                          //  listData = gson.fromJson(object.getJSONArray("listData").toString(), type);

                            DroupDownModel obj1=new DroupDownModel();
                            obj1.setId("00");
                            obj1.setName("Other Help");
                            obj1.setDescription("Other Help");
                            list.add(obj1);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject str=jsonArray.getJSONObject(i);
                                DroupDownModel obj=new DroupDownModel();
                                obj.setId(str.getString("orders_id"));
                                obj.setName(str.getString("name")+"\n"+str.getString("vendorName"));
                                obj.setDescription(str.getString("OrderNumber")+"\n"+str.getString("name")+"\n"+str.getString("vendorName"));
                                list.add(obj);
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
    private void orderComplaint() {
        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();
            params.put("complaint_by", userDetails.getUserId());
            params.put("orders_detail_id", orderId);
            params.put("complaint", tvMessage.getText().toString());
            if(getBitmapstr!=null){
                params.put("complaint_photo",getBitmapstr);
            }else{
                params.put("complaint_photo","");
            }
Log.e("data",params.toString());
            RestClient.post().orderComplaint(userDetails.getSk(), ApiService.APP_DEVICE_ID,params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {


                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message", object.getString("message"));

                        if (object.getBoolean("status")) {

                            Util.show(mContext,object.getString("message"));
finish();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                }
                break;
            }

        }

        if (resultCode == RESULT_OK) {
            //Image Uri will not be null for RESULT_OK

            Uri compressUri= imageGalleryCode.getPickImageResultUri(this,data);
            //You can get File object from intent

            //Uri fileUri = data;
            File file = ImagePicker.Companion.getFile(data);
            frameId.setVisibility(View.VISIBLE);
            imageView.setImageURI(compressUri);
            bitmapString = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, file.getAbsolutePath());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmapString.compress(Bitmap.CompressFormat.JPEG, 45, baos);
            byte[] imageBytes = baos.toByteArray();
            getBitmapstr = Base64.encodeToString(imageBytes, Base64.DEFAULT);


            //You can get File object from intent

            //You can also get File Path from intent
            String filePath = ImagePicker.Companion.getFilePath(data);


        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(mContext, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onOptionClick(DroupDownModel liveTest) {
        Util.hideDropDown();
        tvDrop.setText(liveTest.getDescription());

        if(liveTest.getDescription().equalsIgnoreCase("other help")){
            orderId="00";
            Util.show(mContext,"other");
        }else{
            orderId=liveTest.getId();
        }

    }
}