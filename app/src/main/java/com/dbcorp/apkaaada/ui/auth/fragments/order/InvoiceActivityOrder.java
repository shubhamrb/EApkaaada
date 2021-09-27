
package com.dbcorp.apkaaada.ui.auth.fragments.order;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.Adapter.usercard.CouponAdapter;
import com.dbcorp.apkaaada.Adapter.usercard.OrderPriceAdapter;
import com.dbcorp.apkaaada.Adapter.usercard.UserAddressAdapter;
import com.dbcorp.apkaaada.Adapter.usercard.UserCardAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.database.UserSharedPreference;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.DroupDownModel;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.card.CardProduct;
import com.dbcorp.apkaaada.model.card.Coupon;
import com.dbcorp.apkaaada.model.card.UserAddress;
import com.dbcorp.apkaaada.model.shopview.Product;
import com.dbcorp.apkaaada.model.shopview.SubToSubCategory;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.fragments.SetAddressActivity;
import com.google.android.material.button.MaterialButton;
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

public class InvoiceActivityOrder extends AppCompatActivity implements UserAddressAdapter.OnClickListener, CouponAdapter.OnClickListener,UserCardAdapter.OnClickListener {

    Context mContext;
    UserDetails userDetails;
    RecyclerView recyclerView;
    InvoiceActivityOrder listner;
    UserSharedPreference userSharedPreference;


    UserCardAdapter userCardAdapter;
    private ArrayList<CardProduct> cardProducts;
    private ArrayList<CardProduct> oldCardProducts;
    private  String vendorIdsList;
    OrderPriceAdapter orderPriceAdapter;
    private Toolbar toolbar;
    MaterialTextView tvChange, edit_coupon_code,tv_totalPrice, tvItemCount, tvTotalPrice;

    String itemCount,totalPrice,totalPriceAfterCoupon;
    CouponAdapter couponAdapter;

    ArrayList<Coupon> listCoupon;
    MaterialTextView tvApplyCoupon;
    RecyclerView listProductOrder;
    String currentCouponVendorId="";
    String currentCouponPrice="";
    PopupWindow popupWindow;
    UserAddressAdapter userAddressAdapter;

    ArrayList<UserAddress> userAddressesList;
    MaterialTextView locationTv,tvTotalChargePrice, tvAddTotalChargePrice,tvMultiVenCharge,tvDeliveryCharge, tvCouponPrice,tvAddType,tvAddress,tvChoose;

    MaterialTextView btnProceed;
    ArrayList<String> tokens;
    HashMap<String, String> address;
    MaterialTextView itemCountTv,tvPrice;
    MaterialButton submit_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_order_item_cat_layout);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Order Details");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        listner=this;
        userDetails = new SqliteDatabase(this).getLogin();
        this.mContext = this;
        userSharedPreference=new UserSharedPreference(mContext);
        address=userSharedPreference.getAddress();
        init();

      // 23.2048039,77.4011025//     double kmDist=Util.distance(23.2232526,77.4313811,23.2048039,77.4011025,"k");
//        locationTv.setText(String.format("%s km",kmDist));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private void init() {
        userAddressesList=new ArrayList<>();

        orderDetails();
        getCardCount();
        submit_btn=findViewById(R.id.submit_btn);
        locationTv=findViewById(R.id.locationTv);
        itemCountTv=findViewById(R.id.itemCount);
        tvPrice = findViewById(R.id.tvPrice);
        listProductOrder =  findViewById(R.id.listProductOrder);
        tvChange=findViewById(R.id.tvChange);
        tvChoose=findViewById(R.id.tvChoose);
        btnProceed=findViewById(R.id.btnProceed);
        edit_coupon_code=findViewById(R.id.edit_coupon_code);
        tvItemCount=findViewById(R.id.tvItemCount);
        tvAddType=findViewById(R.id.tvAddType);
        tvAddress=findViewById(R.id.tvAddress);
        tvCouponPrice=findViewById(R.id.tvCouponPrice);
        tv_totalPrice=findViewById(R.id.tv_totalPrice);
        tvTotalPrice=findViewById(R.id.tvTotalPrice);
        tvApplyCoupon=findViewById(R.id.tvApplyCoupon);
        listProductOrder.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        listProductOrder.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        edit_coupon_code.setOnClickListener(v->{
            openCoupon();
        });
        tvChange.setOnClickListener(v->{
            Intent mv=new Intent(InvoiceActivityOrder.this, SetAddressActivity.class);
            mv.putExtra("type","deliver");
            startActivity(mv);
        });
        tvChoose.setOnClickListener(v->{
            openAddress();
        });

        btnProceed.setOnClickListener(v->{

            getCharge();


        });

        submit_btn.setOnClickListener(v->{
                applyCoupon(currentCouponVendorId,currentCouponPrice);
        });
        getAddress();
    }


    public void chargeDialog(String delivery_charge,String multi_vendor_charge){
        AlertDialog alertDialog;

        AlertDialog.Builder builder2 = new AlertDialog.Builder(mContext);
        builder2.setCancelable(false);
        View dialog = LayoutInflater.from(mContext).inflate(R.layout.layout_dev_charge, null);

        tvTotalChargePrice = dialog.findViewById(R.id.tvTotalChargePrice);
        tvMultiVenCharge = dialog.findViewById(R.id.tvMultiVenCharge);
        tvAddTotalChargePrice= dialog.findViewById(R.id.tvAddTotalChargePrice);
        tvDeliveryCharge = dialog.findViewById(R.id.tvDeliveryCharge);
        AppCompatImageView tvClose=dialog.findViewById(R.id.tvClose);
       MaterialTextView  btnProceedBtn=dialog.findViewById(R.id.btnProceedBtn);
        builder2.setView(dialog);

        int multiCharge=0;
        if(cardProducts.size()>1){
            multiCharge= Integer.parseInt(multi_vendor_charge);

        }else{
            multiCharge= 0;

        }
        int deliveryCharge= Integer.parseInt(delivery_charge);
        int addChargePrice=multiCharge+deliveryCharge+Integer.parseInt(totalPrice);

        tvMultiVenCharge.setText(String.valueOf(multiCharge));
        tvDeliveryCharge.setText(delivery_charge);
        tvAddTotalChargePrice.setText(String.valueOf(addChargePrice));
        tvTotalChargePrice.setText(totalPriceAfterCoupon);


        alertDialog = builder2.create();
        alertDialog.show();
        tvClose.setOnClickListener(v->{
            alertDialog.dismiss();
            alertDialog.cancel();
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
        btnProceedBtn.setOnClickListener(v->{
            Intent mv=new Intent(InvoiceActivityOrder.this, PaymentMode.class);
            mv.putStringArrayListExtra("tokens",tokens);
            startActivity(mv);
        });

    }
 private void  getCharge(){
     if (InternetConnection.checkConnection(mContext)) {
         Map<String, String> params = new HashMap<>();
         params.put("userId", userDetails.getUserId());
         Log.e("param", params.toString());
         Util.showDialog("Please wait fetching your charge",mContext);
         RestClient.post().getCharge(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
             @RequiresApi(api = Build.VERSION_CODES.N)
             @Override
             public void onResponse(@NotNull Call<String> call, Response<String> response) {
                 try {
                     Gson gson = new Gson();
                     JSONObject strObj = new JSONObject(Objects.requireNonNull(response.body()));
                     if (strObj.getBoolean("status")) {

                         chargeDialog(strObj.getString("multi_vendor_charge"),strObj.getString("multi_vendor_charge"));

                     }
                     Util.hideDialog();

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
    private void orderDetails() {
        cardProducts = new ArrayList<>();
        oldCardProducts=new ArrayList<>();
        listCoupon=new ArrayList<>();
        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();
            params.put("userId", userDetails.getUserId());
            Log.e("param", params.toString());
            RestClient.post().userOrder(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    try {
                        Gson gson = new Gson();
                        JSONObject strObj = new JSONObject(Objects.requireNonNull(response.body()));
                        if (strObj.getBoolean("status")) {
                            JSONArray arrayData = strObj.getJSONArray("productDetails");
                            Type notiType = new TypeToken<ArrayList<String>>() {
                            }.getType();
                            tokens=gson.fromJson(strObj.getJSONArray("notification_token").toString(), notiType);


                            vendorIdsList=strObj.getString("vendorIdes") ;

                            Log.e("notification_token",tokens.toString());

                            for (int i = 0; i < arrayData.length(); i++) {


                                JSONObject str = arrayData.getJSONObject(i);
                                CardProduct obj = new CardProduct();
                                obj.setVendorName(str.getString("vendor_name"));
                                obj.setProductCount(str.getInt("productCount"));
                                obj.setShop_name(str.getString("shop_name"));

                                obj.setCityName(str.getString("cityName"));
                                obj.setDelivery_charge_per_km(str.getString("delivery_charge_per_km"));
                                obj.setDelivery_charge_type(str.getString("delivery_charge_type"));
                                obj.setOrder_range_limit(str.getString("order_range_limit"));
                                obj.setLat(str.getString("lat"));
                                obj.setLng(str.getString("lng"));
                                obj.setVendorId(str.getString("vendor_id"));
                                obj.setDiscountPrice(str.getString("discountPrice"));


                                obj.setTotalProductPrice(str.getString("totalProductPrice"));
                                Type productType = new TypeToken<ArrayList<Product>>() {
                                }.getType();

                                obj.setProductList(gson.fromJson(str.getJSONArray("ProductList").toString(), productType));
                                cardProducts.add(obj);
                            }
                            oldCardProducts.addAll(cardProducts);
                            Type couponType = new TypeToken<ArrayList<Coupon>>() {
                            }.getType();
                            listCoupon=gson.fromJson(strObj.getJSONArray("couponList").toString(), couponType);
                            Type tokensType = new TypeToken<ArrayList<String>>() {
                            }.getType();
                            orderPriceAdapter=new OrderPriceAdapter(Double.parseDouble(address.get(UserSharedPreference.CurrentLatitude)),Double.parseDouble(address.get(UserSharedPreference.CurrentLongitude)),cardProducts,mContext);
                            listProductOrder.setAdapter(orderPriceAdapter);

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


    private void getAddress() {
        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();
            params.put("user_id", userDetails.getUserId());
            RestClient.post().getAddress(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    try {

                        JSONObject object = new JSONObject(Objects.requireNonNull(response.body()));

                        Gson gson = new Gson();
                        if (object.getBoolean("status")) {

                            tvAddress.setText(object.getJSONObject("userAddress").getString("address"));
                            tvAddType.setText(object.getJSONObject("userAddress").getString("address_type").equalsIgnoreCase("1") ? "Home" : "Work");
                            Type productType = new TypeToken<ArrayList<UserAddress>>() {
                            }.getType();
                            userAddressesList=gson.fromJson(object.getJSONArray("location").toString(), productType);

                        }else{
                            Util.show(mContext,object.getString("message"));
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
                            itemCountTv.setText(object.getJSONObject("cardCount").getString("cardCount"));

                            tvTotalPrice.setText("₹ "+object.getJSONObject("totalAmount").getString("price"));
                            itemCount=object.getJSONObject("cardCount").getString("cardCount");
                            totalPrice=object.getJSONObject("totalAmount").getString("price");
                            totalPriceAfterCoupon=object.getJSONObject("totalAmount").getString("price");
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


    // open final order layout


    // open coupon layout
    @SuppressLint("SetTextI18n")
    public void openCoupon() {

        View popupView = null;


        popupView = LayoutInflater.from(mContext).inflate(R.layout.droup_down_layout, null);


        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true;

        popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.white_gredient_bg));
        popupWindow.showAtLocation(popupView, Gravity.TOP, 0, 0);
        ImageView closeImg=popupView.findViewById(R.id.closeImg);
        EditText search=popupView.findViewById(R.id.search);
        search.setVisibility(View.GONE);
        TextView tittleName=popupView.findViewById(R.id.tittleName);
        EditText inputSearch=popupView.findViewById(R.id.search);
        tittleName.setVisibility(View.VISIBLE);
        tittleName.setText("Select Coupon");
        RecyclerView listCountryData2 = popupView.findViewById(R.id.listView);

        listCountryData2.setHasFixedSize(true);
        //syllabus_type_recyclerview.setLayoutManager(new GridLayoutManager(HomeActivity.this, 3));
        listCountryData2.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        closeImg.setOnClickListener(v -> {
            popupWindow.dismiss();

        });



        couponAdapter=new CouponAdapter(listCoupon,listner,mContext);
        listCountryData2.setAdapter(couponAdapter);

    }


    public  void applyCoupon(String vendorId,String price){
        int VenOrderPrice=0;
        int couponPrice=0;
        int updatePrice=0;
        String p=price.equalsIgnoreCase("")?"0":price;
        int getPrice= Integer.parseInt(p);
        totalPriceAfterCoupon=totalPrice;
        for(int i=0;i<cardProducts.size();i++){

            if (cardProducts.get(i).getVendorId().equals(vendorId)) {
                VenOrderPrice= Integer.parseInt(cardProducts.get(i).getTotalProductPrice());

if(getPrice>VenOrderPrice){
     Util.show(mContext,"Please change coupon code");
     break;
}else{

    couponPrice= Integer.parseInt(price);
    updatePrice=VenOrderPrice-couponPrice;
    Log.e("topvendor", String.valueOf(VenOrderPrice));
    cardProducts.get(i).setDiscountPrice(String.valueOf(updatePrice));
    orderPriceAdapter.notifyDataSetChanged();
    int totalCardPrice=Integer.parseInt(totalPrice)-updatePrice;
    int tp=totalCardPrice-updatePrice;
    totalPriceAfterCoupon=String.valueOf(tp);
    break;
}

            }
        }


    }


    @SuppressLint("SetTextI18n")
    public void openAddress() {

        View popupView = null;


        popupView = LayoutInflater.from(mContext).inflate(R.layout.droup_down_layout, null);


        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;

        popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.white_gredient_bg));
        popupWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);
        ImageView closeImg=popupView.findViewById(R.id.closeImg);
        EditText search=popupView.findViewById(R.id.search);
        search.setVisibility(View.GONE);
        TextView tittleName=popupView.findViewById(R.id.tittleName);
        EditText inputSearch=popupView.findViewById(R.id.search);
        tittleName.setVisibility(View.VISIBLE);
        tittleName.setText("Select Address");
        RecyclerView listCountryData2 = popupView.findViewById(R.id.listView);

        listCountryData2.setHasFixedSize(true);
        //syllabus_type_recyclerview.setLayoutManager(new GridLayoutManager(HomeActivity.this, 3));
        listCountryData2.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        closeImg.setOnClickListener(v -> {
            popupWindow.dismiss();

        });
        userAddressAdapter=new UserAddressAdapter(userAddressesList,listner,mContext);
        listCountryData2.setAdapter(userAddressAdapter);

    }


    @Override
    public void onClickOrder(String liveTest, int pos, String type) {

    }

    @Override
    public void removeCartProduct(String cartId) {

    }

    @Override
    public void addCartClick(Product liveTest, int pos, String subAdd, ArrayList<Product> arrayList, View showProduct) {

    }


    @Override
    public void clickCoupon(Coupon data, int pos, String type) {

        popupWindow.dismiss();
        tvApplyCoupon.setText(data.getCouponCode());
        currentCouponVendorId=data.getVendorId();
        currentCouponPrice=data.getPrice();
        int t=Integer.parseInt(totalPrice)-Integer.parseInt(data.getPrice());
        tvTotalPrice.setText("₹ "+String.valueOf(t));
        tvCouponPrice.setText(data.getPrice());
        cardProducts=new ArrayList<>();
        //oldCardProducts
        cardProducts.addAll(oldCardProducts);
        orderPriceAdapter.notifyDataSetChanged();
    }


    @Override
    public void clickOnAddress(UserAddress data, int pos, String type) {
        popupWindow.dismiss();
        updateAddress(data.getAddressId());
    }


    private void updateAddress(String addId) {
        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();
            params.put("user_id", userDetails.getUserId());
            params.put("address_id", addId);
            RestClient.post().updateAddress(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    try {

                        JSONObject object = new JSONObject(Objects.requireNonNull(response.body()));


                        if (object.getBoolean("status")) {
                            Util.show(mContext,object.getString("message"));
                            getAddress();;
                        }else{
                            Util.show(mContext,object.getString("message"));
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
}