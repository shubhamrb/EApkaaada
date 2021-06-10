package com.dbcorp.apkaaada.ui.auth.fragments.order.myorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dbcorp.apkaaada.Adapter.myorder.OrdersDetailsAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.VendorDetails;
import com.dbcorp.apkaaada.model.order.CustomerOrderDetails;
import com.dbcorp.apkaaada.model.order.Orders;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dbcorp.apkaaada.network.ApiService.IMG_PRODUCT_URL;

public class SingleProductActivity extends AppCompatActivity implements  OrdersDetailsAdapter.OnMeneuClickListnser{
    MaterialTextView tvTotalPrice,tvDeliverBoy,tvProductName,tvProductAttribute,catName,subCat,subtosubCat, tvDate,tvOrderNumber,tvCustName,orderAssign,tvPrice,tvStatus;
    MaterialCardView cardView;
    ArrayList<CustomerOrderDetails> orders;
    RecyclerView orderList, menuList;
    OrdersDetailsAdapter ordersAdapter;
    LinearLayoutCompat acceptLayout;
    TextInputEditText edtOtp;
    ProgressBar progressBar;
    PopupWindow popupWindow;
    MaterialTextView tvError;
    private  static AlertDialog alertDialog;
    MaterialTextView tvTotal,tvPT;
    ArrayList<Orders> selectedProductData;
    Context mContext;
    SingleProductActivity listnerContext;
    AppCompatCheckBox tvCheckBox;
    LinearLayoutCompat detailsShow;
    CircleImageView productImg;

    private Toolbar toolbar;

    UserDetails loginDetails;
    ArrayList<String> selectedIdes;

    AppCompatImageView imgClose;
    MaterialButton tvCancel,tvAccept;
    Orders ordersData;
    String customerNumber="",getSeverOtp="";
    private static Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_my_order_list);
        // setContentView(R.layout.activity_single_product);

        loginDetails=new SqliteDatabase(this).getLogin();
        this.mContext=this;
        this.listnerContext=this;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Order Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ordersData = (Orders) getIntent().getSerializableExtra("MyData");

        init();
    }
    public void init() {
        orders=new ArrayList<>();
        selectedIdes=new ArrayList<>();
        selectedProductData=new ArrayList<>();

        tvPT=  findViewById(R.id.tvPT);
        imgClose= findViewById(R.id.imgClose);
        tvCancel=  findViewById(R.id.tvCancel);
        tvAccept=  findViewById(R.id.tvAccept);
        acceptLayout= findViewById(R.id.acceptLayout);
        orderList =  findViewById(R.id.orderList);

        tvTotal= findViewById(R.id.tvTotal);
        tvError= findViewById(R.id.tvError);
        progressBar= findViewById(R.id.progressBar);


        orderList.setHasFixedSize(true);
        orderList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));



        getOrder( );

        imgClose.setOnClickListener(v->{
            acceptLayout.setVisibility(View.GONE);
        });
        tvCancel.setOnClickListener(v->{
            if(selectedIdes.size()<1){
                Util.show(mContext,"please Select Minimum one product ");
                return;
            }
            //   orderAcceptedByVendor("Rejected","3");
        });
        tvAccept.setOnClickListener(v->{
            if(selectedIdes.size()<1){
                Util.show(mContext,"please Select Minimum one product ");
                return;
            }


        });

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void viewSingleOrder(Orders data, int pos) {


        //otpWindow(data);// orderAcceptDialog(param);
    }

    @Override
    public void deliveryCall(Orders data, int pos) {

    }

    @Override
    public void vendorCalling(CustomerOrderDetails data, int pos) {

    }


    @Override
    public void selectProduct(ArrayList<CustomerOrderDetails> listData) {
        selectedIdes=new ArrayList<>();

        for(int i=0;i<listData.size();i++){
            for (int p=0;p<listData.get(i).getProductDetails().size();p++){
                Log.e("ordecheck"+p,listData.get(i).getProductDetails().get(p).getCheckbox()+"="+listData.get(i).getProductDetails().get(p).getProductName());
                if(listData.get(i).getProductDetails().get(p).getCheckbox().equalsIgnoreCase("1")){

                    selectedIdes.add(listData.get(i).getProductDetails().get(p).getOrdersDetailId());
                }
            }
        }
        acceptLayout.setVisibility(View.VISIBLE);
        tvPT.setText(String.valueOf(selectedIdes.size()));
    }

    private void getOrder(){
        acceptLayout.setVisibility(View.GONE);
        orders=new ArrayList<>();
        orderList.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.VISIBLE);
        tvError.setText("Please Wait...");
        tvTotal.setText("");
        if (InternetConnection.checkConnection(mContext)) {


            Map<String, String> params = new HashMap<>();
            params.put("userId",loginDetails.getUserId());
            params.put("orderIds",ordersData.getOrdersId());
            params.put("orders_detail_id",ordersData.getOrdersDetailId());

            // Calling JSON
            Log.e("params",params.toString());
            Call<String> call = RestClient.post().getSingle(loginDetails.getSk(),"1234", params);

            // Enqueue Callback will be call when get response...
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        try {


                            Log.e("datares",response.body());
                            Gson gson = new Gson();

                            JSONObject object=new JSONObject(response.body());
                            if(object.getBoolean("status")){


                                Type type = new TypeToken<ArrayList<CustomerOrderDetails>>(){}.getType();
                                orders=gson.fromJson(object.getJSONArray("orderList").toString(),type);

                                if(orders.size()>0){
                                    orderList.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    tvError.setVisibility(View.GONE);
                                    tvTotal.setText(""+orders.size());
                                    ordersAdapter = new OrdersDetailsAdapter(orders, listnerContext, mContext,"6");
                                    orderList.setAdapter(ordersAdapter);
                                    ordersAdapter.notifyDataSetChanged();
                                }else{
                                    progressBar.setVisibility(View.GONE);
                                    tvError.setVisibility(View.VISIBLE);
                                    tvError.setText(object.getString("message"));
                                    orders.clear();
                                    ordersAdapter.notifyDataSetChanged();
                                }

                            }else{

                                Util.show(mContext,object.getString("message"));

                            }


                        } catch (Exception e) {

                            e.printStackTrace();
                        }

                    } else {
                        try {
                            assert response.errorBody() != null;
                            Toast.makeText(mContext, "error message" + response.errorBody().string(), Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            Toast.makeText(mContext, "error message" + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    Toast.makeText(mContext, R.string.string_some_thing_wrong, Toast.LENGTH_SHORT).show();
                }

            });
        } else {

            Toast.makeText(mContext, R.string.string_internet_connection_not_available, Toast.LENGTH_SHORT).show();
        }
    }
}