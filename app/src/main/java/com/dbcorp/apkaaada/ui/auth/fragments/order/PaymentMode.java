package com.dbcorp.apkaaada.ui.auth.fragments.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.Adapter.InstructionAdapter;
import com.dbcorp.apkaaada.Adapter.ShopProduct;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.Varriant.AttributeValue;
import com.dbcorp.apkaaada.model.card.Instruction;
import com.dbcorp.apkaaada.model.shopview.Product;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.google.android.material.textview.MaterialTextView;
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

public class PaymentMode extends AppCompatActivity implements InstructionAdapter.OnClickListener {


    RecyclerView listItem;
    Context mContext;
    private Toolbar toolbar;
    PaymentMode listner;

    InstructionAdapter instruction;
    ArrayList<Instruction> listInstruction;
    ArrayList<Instruction> selectedInstruction;
    UserDetails userDetails;
    ArrayList<String> instructionList;
    LinearLayoutCompat tvCod, tvPod, online, tvCase;
    MaterialTextView proceedToPay;
String payMode="5";

AppCompatImageView tvBack;
    ArrayList<String>  tokensList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_mode);
        tvBack=findViewById(R.id.tvBack);
        tvBack.setOnClickListener(v->{
            finish();
        });
        tokensList=getIntent().getStringArrayListExtra("tokens");
        userDetails = new SqliteDatabase(this).getLogin();
        mContext = this;
        listner = this;
        init();
    }

    private void init() {
        instructionList=new ArrayList<>();
        listInstruction = new ArrayList<>();
        selectedInstruction = new ArrayList<>();
        proceedToPay = findViewById(R.id.proceedToPay);
        listItem = findViewById(R.id.listItem);
        listItem.setHasFixedSize(true);
        tvCod = findViewById(R.id.tvCod);
        tvPod = findViewById(R.id.tvPod);
        online = findViewById(R.id.online);
        tvCase = findViewById(R.id.tvCase);

        tvCod.setOnClickListener(v -> {
            payMode = "5";
            tvCase.setBackgroundResource(R.drawable.online_boder);
            online.setBackgroundResource(R.drawable.cash_boder);
        });
        tvPod.setOnClickListener(v -> {
            payMode = "4";
            tvCase.setBackgroundResource(R.drawable.cash_boder);
            online.setBackgroundResource(R.drawable.online_boder);
        });
        //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        listItem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        getInstruction();


        proceedToPay.setOnClickListener(v -> {
            if(instructionList.size()>0){

               // Log.e("tokensList.toString()",tokensList.toString());
                 startProceedToPay();
            }else{
                Util.show(mContext,"Please select instruction");
            }

        });
    }

    private void getInstruction() {
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
                            Type productType = new TypeToken<ArrayList<Instruction>>() {
                            }.getType();
                            listInstruction = gson.fromJson(object.getJSONArray("instructionList").toString(), productType);
                            instruction = new InstructionAdapter(listInstruction, listner, mContext);
                            listItem.setAdapter(instruction);

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


    private void startProceedToPay() {
        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();

            params.put("u__id", userDetails.getUserId());
            params.put("pm__id", payMode);
            params.put("tokens", tokensList.toString().replace("[","").replace("]","").replace(" ",""));
            params.put("i__ids", instructionList.toString().replace("[", "").replace("]", "").replace(" ",""));
            Log.e("message", params.toString());

            RestClient.post().proceedOrder(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    try {


                        Gson gson = new Gson();

                        JSONObject object = new JSONObject(response.body());
                        Log.e("message", object.getString("message"));


                        if (object.getBoolean("status")) {
                            Util.show(mContext, object.getString("message"));

                            JSONObject orderDetails = object.getJSONObject("orderDetails");
                        Intent mv = new Intent(PaymentMode.this, SuccessActivity.class);
                            Log.e("order_number", orderDetails.getString("order_number"));
                            mv.putExtra("order_number", orderDetails.getString("order_number"));
                            mv.putExtra("amount", orderDetails.getString("total"));
                             startActivity(mv);
                        } else {

                            Util.show(mContext, object.getString("message"));
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

        Log.e("insruction",instructionList.toString().replace("[", "").replace("]", ""));
    }

    @Override
    public void onClickCard(ArrayList<Instruction> list, int pos, String type) {

        instructionList=new ArrayList<>();
        instructionList.add(list.get(pos).getInstructionId());



    }
}