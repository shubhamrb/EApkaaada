package com.dbcorp.apkaaada.ui.auth.fragments.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dbcorp.apkaaada.Adapter.ProductImageSlider;
import com.dbcorp.apkaaada.Adapter.ShopProduct;
import com.dbcorp.apkaaada.Adapter.SliderAdaptercarouseList;
import com.dbcorp.apkaaada.Adapter.varriant.AttributeAdapter;
import com.dbcorp.apkaaada.Adapter.varriant.AttributeValueAdapter;
import com.dbcorp.apkaaada.Adapter.varriant.VariantAttributeAdapter;
import com.dbcorp.apkaaada.Adapter.varriant.VariantProductAdapter;
import com.dbcorp.apkaaada.Adapter.varriant.VariantValueAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.Carouseloffer;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.Varriant.Attribute;
import com.dbcorp.apkaaada.model.Varriant.AttributeValue;
import com.dbcorp.apkaaada.model.Varriant.Attributetype;
import com.dbcorp.apkaaada.model.Varriant.VariantArrayAttributeValue;
import com.dbcorp.apkaaada.model.Varriant.VariantProduct;
import com.dbcorp.apkaaada.model.Varriant.VariantsAttribute;
import com.dbcorp.apkaaada.model.shopview.Product;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.fragments.ShopDetails;
import com.dbcorp.apkaaada.ui.auth.fragments.order.ActivityOrder;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VarriantActivity extends AppCompatActivity implements VariantAttributeAdapter.OnClickListener,VariantValueAdapter.OnMeneuClickListnser,AttributeAdapter.OnClickListener, AttributeValueAdapter.OnClickListener, VariantProductAdapter.OnMeneuClickListnser {


    VarriantActivity listner;
    Map<String,String> getMap;
    private ProductImageSlider sliderAdapter;
    ArrayList<VariantArrayAttributeValue> varriantList;
    Product singleProductDetails;
    ArrayList<Product> filtterVarriantList;
    ArrayList<String> variantListValue;
    ArrayList<Attribute> attributes;
    ArrayList<AttributeValue> attributesValue;
    ArrayList<AttributeValue> filterAttributesValue;
    RecyclerView attributeList, listProduct, attributeValue;
    private ViewPager viewPager;
    Context mContext;
    Timer timer;
    TabLayout indicator;
    Product data, currentProduct;
    VariantProduct currentVariantData;

    AttributeAdapter attributeAdapter;
    AttributeValueAdapter attributeValueAdapter;

    VariantValueAdapter variantAttributeAdapter;

    VariantProductAdapter shopProduct;
    String productId, vendor_id;
    Intent g;


    private ArrayList<Carouseloffer> carouselList;
    private UserDetails userDetails;
    LinearLayoutCompat layoutCard, addBtn,addLayout,layoutProceed;

    MaterialTextView addCartProduct, btnProceed, tvProductName, addToCard, tv_totalPrice, tvItemCount, tvDescription, tvPrice, tv_quantity, tvLeftProduct;
    AppCompatImageView img, button_add, button_subtract;
    private Toolbar toolbar;
    int i = 0;
    int itemCount = 0;

    int leftCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varriant);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Product Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        g = getIntent();
        data = (Product) g.getSerializableExtra("product");


        this.mContext = this;
        listner = this;
        init();
        Gson gson = new Gson();
        Type productList = new TypeToken<VariantProduct>() {
        }.getType();
        VariantProduct ob= gson.fromJson(gson.toJson(data), productList);
        currentVariantData=ob;
        setData(currentVariantData);
    }

    private void addCart(VariantProduct data, String addSub) {

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

Util.hideDialog();
                            Log.e("param", response.toString());
                            Util.show(mContext, object.getString("message"));
                            getCardCount();
                        } else {
                            Util.hideDialog();
                            Util.show(mContext, object.getString("message"));
                        }
                        //Util.hideDialog("Please wait..", mContext);
                    } catch (Exception ignored) {
                        Util.hideDialog( );

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

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private void init() {

        variantListValue=new ArrayList<>();
        addCartProduct=findViewById(R.id.addCartProduct);
        tvProductName = findViewById(R.id.tvProductName);
        tvDescription = findViewById(R.id.tvDescription);
        img = findViewById(R.id.img);
        addBtn=findViewById(R.id.addBtn);
        addLayout = findViewById(R.id.addLayout);
        tv_totalPrice = findViewById(R.id.tv_totalPrice);
        tvItemCount = findViewById(R.id.tvItemCount);
        tvLeftProduct = findViewById(R.id.tvLeftProduct);
        layoutProceed=findViewById(R.id.layoutProceed);
        attributeValue = findViewById(R.id.attributeValue);
        tvPrice = findViewById(R.id.tvPrice);
        tv_quantity = findViewById(R.id.tv_quantity);
        button_subtract = findViewById(R.id.button_subtract);
        button_add = findViewById(R.id.button_add);
        userDetails = new SqliteDatabase(mContext).getLogin();
        viewPager = findViewById(R.id.viewPager);
        layoutCard = findViewById(R.id.layoutCard);
        indicator = findViewById(R.id.indicator);
        attributeList = findViewById(R.id.attributeList);
        listProduct = findViewById(R.id.listProduct);
        btnProceed=findViewById(R.id.btnProceed);
        addToCard = findViewById(R.id.addToCard);

        btnProceed.setOnClickListener(v -> {
            Intent mv = new Intent(mContext, ActivityOrder.class);
            startActivity(mv);
           finish();

        });
        addCartProduct.setOnClickListener(v->{
            Util.show(mContext,"shhs");
            addCart(currentVariantData, "-");
            currentVariantData.setCartQuantity("1");

            setData(currentVariantData);
        });
        addToCard.setOnClickListener(v -> {


            int item= Integer.parseInt(currentVariantData.getCartQuantity());
            item--;
            currentVariantData.setCartQuantity(String.valueOf(item));
                addCart(currentVariantData, "1");
            setData(currentVariantData);


        });

        button_add.setOnClickListener(v -> {

            int item= Integer.parseInt(currentVariantData.getCartQuantity());
            item++;
            currentVariantData.setCartQuantity(String.valueOf(item));




                    addCart(currentVariantData, "1");
            setData(currentVariantData);


        });
        button_subtract.setOnClickListener(v -> {


            int item= Integer.parseInt(currentVariantData.getCartQuantity());
            item--;
            currentVariantData.setCartQuantity(String.valueOf(item));
                    addCart(currentVariantData, "0");
            setData(currentVariantData);


        });

        listProduct.setVisibility(View.GONE);
        listProduct.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        listProduct.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));



        attributeList.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        attributeList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));


        attributeValue.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        attributeValue.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        getVariant();
        getCardCount();

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

                            layoutProceed.setVisibility(View.VISIBLE);
                            tvItemCount.setText(object.getJSONObject("cardCount").getString("cardCount"));
                            tv_totalPrice.setText("₹ "+object.getJSONObject("totalAmount").getString("price"));

                        }else{
                            layoutProceed.setVisibility(View.GONE);
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

    private void setItemCount() {
        if (itemCount > 0) {
            addLayout.setVisibility(View.VISIBLE);
            addToCard.setVisibility(View.GONE);
        } else {

        }
        if (itemCount > 0) {
            tv_quantity.setText(String.valueOf(itemCount));
        }

    }

    @SuppressLint("SetTextI18n")
    private void setData(Product data) {

        tvLeftProduct.setText(data.getQuantity() + " product are left");
        tvPrice.setText("₹ " + data.getPrice());
        tvDescription.setText(data.getDescription());
        tvProductName.setText(data.getName());
        Glide.with(mContext)
                .load(ApiService.PRODUCT_IMG_URL + data.getPhoto())
                .into(img);
        currentProduct = data;



    }

    @SuppressLint("SetTextI18n")
    private void setData(VariantProduct data) {
int quantity= Integer.parseInt(data.getCartQuantity());

        if(data.getCartQuantity().equalsIgnoreCase("0")){
            addCartProduct.setVisibility(View.VISIBLE);
            addBtn.setVisibility(View.GONE);
        }else {
            itemCount= Integer.parseInt(data.getCartQuantity());
            tv_quantity.setText(String.valueOf(itemCount));
            addBtn.setVisibility(View.VISIBLE);
            addCartProduct.setVisibility(View.GONE);
        }
        tvLeftProduct.setText(data.getQuantity() + " product are left");
        tvPrice.setText("₹ " + data.getPrice());
        tvDescription.setText(data.getDescription());
        tvProductName.setText(data.getName());
        Glide.with(mContext)
                .load(ApiService.PRODUCT_IMG_URL + data.getPhoto())
                .into(img);
        currentVariantData=data;

        sliderAdapter = new ProductImageSlider(mContext, currentVariantData.getImagesGallery());
        viewPager.setAdapter(sliderAdapter);
        indicator.setupWithViewPager(viewPager, true);
        sliderAdapter.notifyDataSetChanged();
        timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);
    }

    public void getVariant() {


        carouselList = new ArrayList<>();
        varriantList = new ArrayList<>();
        attributes = new ArrayList<>();
        attributesValue = new ArrayList<>();


        if (InternetConnection.checkConnection(mContext)) {
            Util.showDialog("Please wait..",mContext);
            Map<String, String> params = new HashMap<>();
            params.put("productId", g.getStringExtra("productId"));
            params.put("vendor_id", g.getStringExtra("vendor_id"));
            params.put("userId", userDetails.getUserId());

            Log.e("param", params.toString());
            RestClient.post().getVariant(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    try {

                        Gson gson = new Gson();
                        JSONObject object = new JSONObject(response.body());
                        Log.e("message", object.getString("message"));
                        if (object.getBoolean("status")) {
                            Util.hideDialog();
                            Type productlist = new TypeToken<ArrayList<VariantArrayAttributeValue>>() {
                            }.getType();

                             varriantList = gson.fromJson(object.getJSONArray("listData").toString(), productlist);

                            comMap(varriantList);


//                            Type variantList = new TypeToken<ArrayList<VariantsAttribute>>() {
//                            }.getType();
                            Type variantList = new TypeToken<ArrayList<String>>() {
                            }.getType();
                            variantListValue = gson.fromJson(object.getJSONArray("listDataAttribute").toString(), variantList);


                            variantAttributeAdapter = new VariantValueAdapter(variantListValue, listner, mContext);
                            listProduct.setAdapter(variantAttributeAdapter);

//                            variantListValue = gson.fromJson(object.getJSONArray("attribute").toString(), variantList);
//                            variantAttributeAdapter = new VariantAttributeAdapter(variantListValue, listner, mContext);
//                            listProduct.setAdapter(variantAttributeAdapter);


                            //carouselList = gson.fromJson(object.getJSONArray("sliderImg").toString(), pageViewer);

                        }else{
                            Util.hideDialog();
                        }

                    } catch (Exception e) {

                        Util.hideDialog();
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
                    Util.hideDialog();
                }
            });

        }
    }


    @Override
    public void productClick(Product liveTest, int pos) {


    }


    @Override
    public void onAttributeClick(Attribute data, int pos) {
        Toast.makeText(mContext, "Bhupesh", Toast.LENGTH_SHORT).show();
        filterAttributesValue = new ArrayList<>();
        for (AttributeValue d : attributesValue) {
            if (d.getAttributeId().equals(data.getAttributeId())) {
                filterAttributesValue.add(d);
            }
        }
        attributeValueAdapter = new AttributeValueAdapter(filterAttributesValue, listner, mContext);
        attributeValue.setAdapter(attributeValueAdapter);
        attributeValueAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttributeClick(AttributeValue data, int pos, String type) {

    }



    @Override
    public void addCartClick(Product liveTest, int pos, String addSub, ArrayList<Product> listProduct) {



    }

    @Override
    public void onVariantAttributeClick(VariantsAttribute data, int pos) {

    }

    public  void comMap(ArrayList<VariantArrayAttributeValue> varriantList){

        for (VariantArrayAttributeValue d : varriantList) {
            getMap=new HashMap<>();
            for(int l=0;l<d.getAttributevalue().size();l++){
                getMap.put(d.getAttributevalue().get(l).getAttributeName(),d.getAttributevalue().get(l).getAttributevalueName());
            }
        }
    }

    @Override
    public void onVariantValue(Map<String,String> selectAttribute) {
        addLayout.setVisibility(View.GONE);
        if(variantListValue.size()==selectAttribute.size()){
            String valueName=selectAttribute.toString();
         //   Util.show(mContext, valueName);

int count=0;
                for (int k=0;k<varriantList.size();k++) {
                    String value=varriantList.get(k).getProductList().getValueName().replace(" ","");
                    String s=valueName.replace("{","").replace("}","").replace(" ","").replace("=",":");
                   Log.e("v",value);
                    Log.e("valueName",s);
                    if(s.equalsIgnoreCase(value)){
                        addLayout.setVisibility(View.VISIBLE);
                        VariantProduct vp=varriantList.get(k).getProductList();
                        setData(vp);

                        count++;
                    }else{
                        Util.show(mContext, "Not Found");
                    }
                }

                if(count>0){
                    Util.show(mContext, "Found"+valueName+count);
                }else{
                    Util.show(mContext, "No Product Found");
                }


        }else{
            Util.show(mContext,"Product Not Found, Please Select Both Value");
        }

    }

    @Override
    public void onVariantValue(String liveTest, int pos) {

    }

    //for(int p=0;p<d.getAttributevalue().size();p++){
//    Attributetype attributetype=d.getAttributevalue().get(p);
//    selectAttribute.get(attributetype.getAttributeName());
//    if(selectAttribute.get(attributetype.getAttributeName()).equals(attributetype.getAttributevalueName())){
//        count++;
//        Log.e("map value"+p,selectAttribute.get(attributetype.getAttributeName()));
//    }
//
//}
//                if(count==d.getAttributevalue().size()){
//                    Util.show(mContext,"we found the product"+count);
//
//
//                }else{
//                    Util.show(mContext,"we dont found the product"+count);
//                }
//                if (d.getAttributevalue().c) {
//                    singleProductDetails=d;
//
//                    Log.e("selected valueName",singleProductDetails.getValueName());
//                }
    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            ((VarriantActivity) mContext).runOnUiThread(() -> {
                if (viewPager.getCurrentItem() < carouselList.size() - 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else {
                    viewPager.setCurrentItem(0);
                }
            });
        }
    }
    public static HashMap<String, String>  jsonToMap(String t) throws JSONException {

        HashMap<String, String> map = new HashMap<String, String>();
        JSONObject jObject = new JSONObject(t);
        Iterator<?> keys = jObject.keys();

        while( keys.hasNext() ){
            String key = (String)keys.next();
            String value = jObject.getString(key);
            map.put(key, value);

        }
        return  map;
    }
    public void showAttributevalue(String msg) {

        View popupView = null;


        popupView = LayoutInflater.from(mContext).inflate(R.layout.showattribute, null);


        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);

        MaterialTextView tvName = popupView.findViewById(R.id.tvName);

        RecyclerView listAttribute = popupView.findViewById(R.id.attributeList);
        listAttribute.setHasFixedSize(true);
        listAttribute.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));


    }

    public  void setVariantValue(){

    }
}
