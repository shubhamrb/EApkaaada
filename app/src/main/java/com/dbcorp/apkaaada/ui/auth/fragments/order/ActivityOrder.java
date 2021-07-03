
package com.dbcorp.apkaaada.ui.auth.fragments.order;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dbcorp.apkaaada.Adapter.usercard.CouponAdapter;
import com.dbcorp.apkaaada.Adapter.usercard.OrderPriceAdapter;
import com.dbcorp.apkaaada.Adapter.usercard.UserCardAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.card.CardProduct;
import com.dbcorp.apkaaada.model.card.Coupon;
import com.dbcorp.apkaaada.model.shopview.Product;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.Home.HomeActivity;
import com.dbcorp.apkaaada.ui.auth.Login;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityOrder extends AppCompatActivity implements UserCardAdapter.OnClickListener {

    Context mContext;
    UserDetails userDetails;
    RecyclerView recyclerView;
    ActivityOrder listner;


    UserCardAdapter userCardAdapter;
    private ArrayList<CardProduct> cardProducts;
    OrderPriceAdapter orderPriceAdapter;
    private Toolbar toolbar;
    MaterialTextView tv_totalPrice, tvItemCount, btnProceed;


    CouponAdapter couponAdapter;

    ArrayList<Coupon> listCoupon;
    MaterialTextView tvApplyCoupon;
    LinearLayoutCompat layoutProceed;
    MaterialTextView itemCount,tvPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My Cart");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
this.listner=this;
        userDetails = new SqliteDatabase(this).getLogin();
        this.mContext = this;
        init();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private void init() {
        layoutProceed=findViewById(R.id.layoutProceed);
        btnProceed = findViewById(R.id.btnProceed);


        itemCount=findViewById(R.id.itemCount);
        tvPrice = findViewById(R.id.tvPrice);

        tv_totalPrice = findViewById(R.id.tv_totalPrice);
        recyclerView = findViewById(R.id.listProduct);
        tvItemCount = findViewById(R.id.tvItemCount);
        recyclerView.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        orderDetails();

        btnProceed.setOnClickListener(v -> {
            Intent mv=new Intent(this,InvoiceActivityOrder.class);
            startActivity(mv);

        });
    }
    private void getCardCount() {
        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();
            params.put("userId", userDetails.getUserId());
            RestClient.post().getCardCount(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    try {

                        JSONObject object = new JSONObject(Objects.requireNonNull(response.body()));


                        if (object.getBoolean("status")) {


                            tvPrice.setText("₹ "+object.getJSONObject("totalAmount").getString("price"));
                            itemCount.setText(object.getJSONObject("cardCount").getString("cardCount"));
                        }else{

                        }
                    } catch (Exception ignored) {


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
    public void onResume() {
        super.onResume();
        getCardCount();
    }



    private void orderDetails() {
        cardProducts = new ArrayList<>();
        listCoupon=new ArrayList<>();
        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();
            params.put("userId", userDetails.getUserId());
            Log.e("param", params.toString());
            Util.showDialog("Please Wait..",mContext);
            RestClient.post().userOrder(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    try {
                        Gson gson = new Gson();
                        JSONObject strObj = new JSONObject(Objects.requireNonNull(response.body()));
                        if (strObj.getBoolean("status")) {
                            JSONArray arrayData = strObj.getJSONArray("productDetails");
                            layoutProceed.setVisibility(View.VISIBLE);

                            for (int i = 0; i < arrayData.length(); i++) {


                                JSONObject str = arrayData.getJSONObject(i);
                                CardProduct obj = new CardProduct();
                                obj.setVendorName(str.getString("vendor_name"));
                                obj.setProductCount(str.getInt("productCount"));
                                Type productType = new TypeToken<ArrayList<Product>>() {
                                }.getType();

                                obj.setProductList(gson.fromJson(str.getJSONArray("ProductList").toString(), productType));
                                cardProducts.add(obj);
                            }
                            Type couponType = new TypeToken<ArrayList<Coupon>>() {
                            }.getType();
                            listCoupon=gson.fromJson(strObj.getJSONArray("couponList").toString(), couponType);

                            userCardAdapter = new UserCardAdapter(cardProducts, listner, mContext);
                            recyclerView.setAdapter(userCardAdapter);
                            Util.hideDialog();

                        }else{
                            layoutProceed.setVisibility(View.GONE);
                            Util.hideDialog();
                            Util.show(mContext,strObj.getString("message"));
                        }
                    } catch (Exception e) {
                        Util.hideDialog();
                        Util.show(mContext, e.getMessage());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                    Util.hideDialog();
                    try {
                        t.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        }

    }




    // open final order layout



    // open coupon layout


    @Override
    public void onClickOrder(String liveTest, int pos, String type) {

    }

    @Override
    public void removeCartProduct(String cartId) {
        removeAlert(cartId);
    }

    @Override
    public void addCartClick(Product liveTest, int pos, String subAdd, ArrayList<Product> arrayList, View showProduct) {
         addCart(liveTest,subAdd,showProduct);
    }

    private void removeAlert(String cardId) {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    try {
                        removeProduct(cardId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        };
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ActivityOrder.this);
        builder.setMessage("Are you sure want to remove this product from your cart ?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }
    private void removeProduct(String cart_id) {

        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();
            params.put("cart_id", cart_id);
            Log.e("param", params.toString());
            RestClient.post().deleteCart(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    try {
                        Gson gson = new Gson();
                        JSONObject strObj = new JSONObject(Objects.requireNonNull(response.body()));
                        if (strObj.getBoolean("status")) {

                            orderDetails();
                            getCardCount();

                        }else{
                            layoutProceed.setVisibility(View.GONE);
                            Util.show(mContext,strObj.getString("message"));
                        }
                    } catch (Exception e) {
                        Util.show(mContext, e.getMessage());
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


    private void addCart(Product data, String addSub,View cardv) {

        if (InternetConnection.checkConnection(mContext)) {
            Util.showDialog("Please wait..", mContext);
            Map<String, String> params = new HashMap<>();
            params.put("user_id", userDetails.getUserId());
            params.put("vendor_product_id", data.getVendorProductId());
            params.put("quantity", "1");
            params.put("plus", addSub);

            Log.e("param", params.toString());
            RestClient.post().addToCart(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    try {


                        JSONObject object = new JSONObject(Objects.requireNonNull(response.body()));

                        if (object.getBoolean("status")) {


                            Log.e("param", response.toString());
                            Util.show(mContext, object.getString("message"));
                            getCardCount();
//                            if (cardv != null) {
//                                Bitmap b = FoodOrderApplication.getInstance().loadBitmapFromView(cardv, cardv.getWidth(), cardv.getHeight());
//                                animateView(cardv, b);
//                            }
                        } else {
                            Util.show(mContext, object.getString("message"));
                        }
                        Util.hideDialog();
                    } catch (Exception ignored) {
                        Util.hideDialog();

                    }

                }

                @Override
                public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                    Util.hideDialog();

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