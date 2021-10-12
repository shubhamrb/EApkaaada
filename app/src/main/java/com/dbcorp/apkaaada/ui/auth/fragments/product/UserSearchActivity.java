package com.dbcorp.apkaaada.ui.auth.fragments.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dbcorp.apkaaada.Adapter.AutoSearchAdapter;
import com.dbcorp.apkaaada.Adapter.MenuListAdapter;
import com.dbcorp.apkaaada.Adapter.NearByShopAdapter;
import com.dbcorp.apkaaada.Adapter.Search.SearchCategoryAdapter;
import com.dbcorp.apkaaada.Adapter.Search.SearchVendorAdapter;
import com.dbcorp.apkaaada.Adapter.SearchProductAdapter;
import com.dbcorp.apkaaada.Adapter.ShopCategory;
import com.dbcorp.apkaaada.Adapter.TabListAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.database.UserSharedPreference;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.AutoSearch;
import com.dbcorp.apkaaada.model.SearchByProduct;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.Varriant.AttributeValue;
import com.dbcorp.apkaaada.model.VendorDetails;
import com.dbcorp.apkaaada.model.home.Category;
import com.dbcorp.apkaaada.model.shopview.Product;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.Home.HomeActivity;
import com.dbcorp.apkaaada.ui.auth.fragments.Home;
import com.dbcorp.apkaaada.ui.auth.fragments.ServiceShop;
import com.dbcorp.apkaaada.ui.auth.fragments.ShopDetails;
import com.dbcorp.apkaaada.ui.auth.fragments.shop.nearbyShop;
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
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSearchActivity extends AppCompatActivity implements AutoSearchAdapter.OnMeneuClickListnser,NearByShopAdapter.OnMeneuClickListnser,ShopCategory.OnMeneuClickListnser, TabListAdapter.OnMeneuClickListnser, SearchProductAdapter.OnClickListener{


    private Toolbar toolbar;
    Intent g;
    UserSearchActivity listner;
    ArrayList<SearchByProduct> searchByProducts;
    ArrayList<SearchByProduct> searchByVendor;
    ArrayList<SearchByProduct> searchByCategory;
    ArrayList<SearchByProduct> filterSearchByProducts;
    MaterialTextView itemCount,tvPrice;
    HashMap<String, String> address;
    UserSharedPreference userSharedPreference;
    AutoSearchAdapter autoSearchAdapter;
    ArrayList<VendorDetails> vendorDetailsList;
    ArrayList<AutoSearch> autoSearchArrayList;
    ArrayList<Category> categoriesList;
    ArrayList<SearchByProduct> filterSearchByVendor;
    ArrayList<SearchByProduct> filterSearchByCategory;
    Context mContext;
    UserDetails userDetails;
    TextInputEditText edit_name;
    LinearLayoutCompat tvSearchLayout;
    SearchProductAdapter searchProduct;
    SearchCategoryAdapter searchCategoryAdapter;
    SearchVendorAdapter searchVendorAdapter;
    RecyclerView autoSearchList,listProduct,menuList,verndoList,serviceVerndoList,ecomeCategory,serviceCategory;
    TextView tvProduct,tvVendorName,tvVariant;
    AppCompatImageView tvSearchIcon;
    TabListAdapter menuListAdapter;
    String typeView="Product";
    NearByShopAdapter nearByShopAdapter;
    LinearLayoutCompat processBar;
    ShopCategory shopCategory;
    MaterialTextView tvEcomList,tvserviceList,tvServiceCat,tvEcompCat;
    UserSharedPreference sessionUser;
    AutoCompleteTextView edtSearch;
    String typeSearchPage="";
    String arrItems[] = new String[]{"Product","Vendor Shop","ECommerce Category","Service Category","Service Provider"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);
        mContext=this;
        userDetails=new SqliteDatabase(this).getLogin();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Search");
        setSupportActionBar(toolbar);
        sessionUser=new UserSharedPreference(this);
        address=sessionUser.getAddress();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
this.listner=this;
        g = getIntent();


        userSharedPreference=new UserSharedPreference(mContext);
        address=userSharedPreference.getAddress();
        getAutoSearchData();
        init();
        typeSearchPage=g.getStringExtra("type");
        if(typeSearchPage.equalsIgnoreCase("keySearch")){
            edit_name.setText(g.getStringExtra("keyWord"));
        }


    }
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
    private void init(){
        vendorDetailsList=new ArrayList<>();
        categoriesList=new ArrayList<>();
        searchByProducts=new ArrayList<>();
        searchByCategory=new ArrayList<>();
        searchByVendor=new ArrayList<>();


        tvEcomList=findViewById(R.id.tvEcomList);
        tvserviceList=findViewById(R.id.tvserviceList);
        tvServiceCat=findViewById(R.id.tvServiceCat);
        tvEcompCat=findViewById(R.id.tvEcompCat);

        autoSearchList=findViewById(R.id.autoSearchList);
        processBar=findViewById(R.id.processBar);
        listProduct=findViewById(R.id.listProduct);
        menuList=findViewById(R.id.menuList);
        itemCount = findViewById(R.id.itemCount);
        tvPrice = findViewById(R.id.tvPrice);
        verndoList=findViewById(R.id.verndoList);
        serviceVerndoList=findViewById(R.id.serviceVerndoList);
        ecomeCategory=findViewById(R.id.ecomeCategory);
        serviceCategory=findViewById(R.id.serviceCategory);


//        tvProduct=findViewById(R.id.tvProduct);
//        tvVariant=findViewById(R.id.tvVariant);
//        tvVendorName=findViewById(R.id.tvVendorName);
        menuList.setHasFixedSize(true);
        menuList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        menuListAdapter = new TabListAdapter(arrItems, this, mContext);
        menuList.setAdapter(menuListAdapter);
        tvEcomList.setVisibility(View.GONE);
        tvserviceList.setVisibility(View.GONE);
        tvEcompCat.setVisibility(View.GONE);
        tvServiceCat.setVisibility(View.GONE);
        tvSearchLayout=findViewById(R.id.tvSearchLayout);
        edit_name=findViewById(R.id.edit_name);
        tvSearchIcon=findViewById(R.id.tvSearchIcon);
        autoSearchAdapter=new AutoSearchAdapter(autoSearchArrayList,listner,mContext);
        autoSearchList.setAdapter(nearByShopAdapter);
        edit_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                String text = edit_name.getText().toString().toLowerCase(Locale.getDefault());
                Log.e("bhs==>>", text);

                if(text.length()>3){
                    autoSearchList.setVisibility(View.VISIBLE);
                    listProduct.setVisibility(View.VISIBLE);
                }else if(text.length()==0){
                    getSearch();
                }
                autoSearchAdapter.getFilter().filter(cs);
                autoSearchAdapter.notifyDataSetChanged();

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        verndoList.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        verndoList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        listProduct.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        listProduct.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));


        serviceVerndoList.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
         serviceVerndoList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));



        ecomeCategory.setHasFixedSize(true);
         ecomeCategory.setLayoutManager(new GridLayoutManager(mContext, 3));
       // ecomeCategory.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));


        serviceCategory.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        serviceCategory.setLayoutManager(new GridLayoutManager(mContext, 3));



        tvSearchIcon.setOnClickListener(v->{
            if(edit_name.getText().toString().length()==0){
                Util.show(mContext,"Please enter any search keyword");
              //  Util.show(mContext,"Please enter any search keyword");
            }else{
                getSearchData();
              //  getCategory();
                getVendor();
                getServiceVendor();
                getServiceCategory();
                getCategory();
               // getServiceCategory();

            }
        });
//        tvProduct.setOnClickListener(v->{
//
//            filterSearchByProducts=new ArrayList<>();
//            if(edit_name.getText().toString().length()>0){
//                for (SearchByProduct d : searchByProducts) {
//                    if (d.getProductName().contains(edit_name.getText().toString())) {
//                        filterSearchByProducts.add(d);
//                    }
//                }
//                searchProduct=new SearchProductAdapter(searchByProducts,listner,mContext);
//                listProduct.setAdapter(searchProduct);
//                searchProduct.notifyDataSetChanged();
//            }
//
//        });
//        tvVariant.setOnClickListener(v->{
//            filterSearchByProducts=new ArrayList<>();
//            if(edit_name.getText().toString().length()>0){
//                for (SearchByProduct d : searchByProducts) {
//                    if (d.getVendorname().contains(edit_name.getText().toString())) {
//                        filterSearchByProducts.add(d);
//                    }
//                }
//                searchProduct=new SearchProductAdapter(searchByProducts,listner,mContext);
//                listProduct.setAdapter(searchProduct);
//                searchProduct.notifyDataSetChanged();
//            }
//        });
//        tvVendorName.setOnClickListener(v->{
//            filterSearchByProducts=new ArrayList<>();
//            if(edit_name.getText().toString().length()>0){
//                for (SearchByProduct d : searchByProducts) {
//                    if (d.getVendorname().contains(edit_name.getText().toString())) {
//                        filterSearchByProducts.add(d);
//                    }
//                }
//                searchProduct=new SearchProductAdapter(searchByProducts,listner,mContext);
//                listProduct.setAdapter(searchProduct);
//                searchProduct.notifyDataSetChanged();
//            }
//        });

        getCardCount();

    }

    private  void  getSearch(){
        if(edit_name.getText().length()==0){
            autoSearchList.setVisibility(View.GONE);
        }

    }
    private void getSearchData() {
        if (InternetConnection.checkConnection(mContext)) {
            filterSearchByProducts=new ArrayList<>();
            searchByProducts=new ArrayList<>();
            categoriesList=new ArrayList<>();
            vendorDetailsList=new ArrayList<>();
            processBar.setVisibility(View.VISIBLE);
            listProduct.setVisibility(View.GONE);
            Map<String, String> params = new HashMap<>();
            params.put("cityId", "1");
            params.put("userId", userDetails.getUserId());
            params.put("searchValue", edit_name.getText().toString());

            Log.e("param",params.toString());
            RestClient.post().getSearchData(userDetails.getSk(), ApiService.APP_DEVICE_ID,params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {


                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message", object.getString("message"));

                        if (object.getBoolean("status")) {

                            processBar.setVisibility(View.GONE);
                            listProduct.setVisibility(View.VISIBLE);
                            if(typeView.equalsIgnoreCase("Product")){
                                Type productList = new TypeToken<ArrayList<SearchByProduct>>() {
                                }.getType();
                                searchByProducts = gson.fromJson(object.getJSONArray("productList").toString(), productList);
                                filterSearchByProducts= gson.fromJson(object.getJSONArray("productList").toString(), productList);
                                searchProduct=new SearchProductAdapter("product",searchByProducts,listner,mContext);
                                listProduct.setAdapter(searchProduct);
                          }
                        } else {
                            Util.show(mContext, object.getString("message"));
                        }
                    } catch (JSONException e) {
                        processBar.setVisibility(View.GONE);
                        listProduct.setVisibility(View.GONE);

                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                    processBar.setVisibility(View.GONE);
                    listProduct.setVisibility(View.GONE);

                    try {
                        t.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        }
    }

    private void getCategory() {
        if (InternetConnection.checkConnection(mContext)) {
            searchByProducts=new ArrayList<>();
            categoriesList=new ArrayList<>();
            vendorDetailsList=new ArrayList<>();
            processBar.setVisibility(View.VISIBLE);
            listProduct.setVisibility(View.GONE);
            Map<String, String> params = new HashMap<>();
            params.put("searchValue", edit_name.getText().toString());

            RestClient.post().getSearchCategoryData(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {


                    Gson gson = new Gson();

                    JSONObject object = null;
                    try {
                        object = new JSONObject(response.body());
                        if (object.getBoolean("status")) {

                            processBar.setVisibility(View.GONE);
                            listProduct.setVisibility(View.VISIBLE);
                            Type type = new TypeToken<ArrayList<Category>>() {
                            }.getType();
                            categoriesList = gson.fromJson(object.getJSONArray("cateogory").toString(), type);
                            shopCategory = new ShopCategory("allcat",categoriesList, listner, mContext);
                            ecomeCategory.setAdapter(shopCategory);

                        } else {
                            processBar.setVisibility(View.GONE);
                            listProduct.setVisibility(View.GONE);

                            Util.show(mContext, object.getString("message"));
                        }
                    } catch (JSONException e) {
                        processBar.setVisibility(View.GONE);
                        listProduct.setVisibility(View.GONE);

                        Util.show(mContext, e.getMessage());
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                    processBar.setVisibility(View.GONE);
                    listProduct.setVisibility(View.GONE);


                    try {
                        t.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        }
    }


    private void getServiceCategory() {
        if (InternetConnection.checkConnection(mContext)) {
            searchByProducts=new ArrayList<>();
            categoriesList=new ArrayList<>();
            vendorDetailsList=new ArrayList<>();
            processBar.setVisibility(View.VISIBLE);
            listProduct.setVisibility(View.GONE);
            Map<String, String> params = new HashMap<>();
            params.put("searchValue", edit_name.getText().toString());

            RestClient.post().getSearchServiceCategoryData(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {


                    Gson gson = new Gson();

                    JSONObject object = null;
                    try {
                        object = new JSONObject(response.body());
                        if (object.getBoolean("status")) {

                            processBar.setVisibility(View.GONE);
                            listProduct.setVisibility(View.VISIBLE);
                            Type type = new TypeToken<ArrayList<Category>>() {
                            }.getType();
                            categoriesList = gson.fromJson(object.getJSONArray("cateogory").toString(), type);
                            shopCategory = new ShopCategory("allcat",categoriesList, listner, mContext);
                            serviceCategory.setAdapter(shopCategory);

                        } else {
                            processBar.setVisibility(View.GONE);
                            listProduct.setVisibility(View.GONE);

                            Util.show(mContext, object.getString("message"));
                        }
                    } catch (JSONException e) {
                        processBar.setVisibility(View.GONE);
                        listProduct.setVisibility(View.GONE);

                        Util.show(mContext, e.getMessage());
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                    processBar.setVisibility(View.GONE);
                    listProduct.setVisibility(View.GONE);


                    try {
                        t.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        }
    }

    public void getVendor() {
        if (InternetConnection.checkConnection(mContext)) {
            searchByProducts=new ArrayList<>();
            categoriesList=new ArrayList<>();
            vendorDetailsList=new ArrayList<>();
            processBar.setVisibility(View.VISIBLE);
            listProduct.setVisibility(View.GONE);
            Map<String, String> params = new HashMap<>();
            params.put("searchValue", edit_name.getText().toString());
            params.put("userId",userDetails.getUserId());
            params.put("lat",address.get(UserSharedPreference.CurrentLatitude));
            params.put("long",address.get(UserSharedPreference.CurrentLongitude));
            RestClient.post().getSearchVendorData(userDetails.getSk(), ApiService.APP_DEVICE_ID,params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message",object.getString("message"));

                        if (object.getBoolean("status")) {
                            processBar.setVisibility(View.GONE);
                            listProduct.setVisibility(View.VISIBLE);
                            Type type = new TypeToken<ArrayList<VendorDetails>>() {
                            }.getType();
                            vendorDetailsList = gson.fromJson(object.getJSONArray("listData").toString(), type);
                            nearByShopAdapter = new NearByShopAdapter("","",vendorDetailsList, listner, mContext);
                            verndoList.setAdapter(nearByShopAdapter);

                        } else {
                            processBar.setVisibility(View.GONE);
                            verndoList.setVisibility(View.GONE);

                            Util.show(mContext, object.getString("message"));
                        }
                    } catch (JSONException e) {
                        processBar.setVisibility(View.GONE);
                        listProduct.setVisibility(View.GONE);

                        Util.show(mContext, e.getMessage());
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
                    processBar.setVisibility(View.GONE);
                    listProduct.setVisibility(View.GONE);

                    Util.show(mContext, t.getMessage());

                }
            });

        }
    }

    public void getServiceVendor() {
        if (InternetConnection.checkConnection(mContext)) {
            searchByProducts=new ArrayList<>();
            categoriesList=new ArrayList<>();
            vendorDetailsList=new ArrayList<>();
            processBar.setVisibility(View.VISIBLE);
            listProduct.setVisibility(View.GONE);
            Map<String, String> params = new HashMap<>();
            params.put("searchValue", edit_name.getText().toString());
            params.put("userId",userDetails.getUserId());
            params.put("lat",address.get(UserSharedPreference.CurrentLatitude));
            params.put("long",address.get(UserSharedPreference.CurrentLongitude));
            RestClient.post().getSearchServiceVendorData(userDetails.getSk(), ApiService.APP_DEVICE_ID,params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message",object.getString("message"));

                        if (object.getBoolean("status")) {
                            processBar.setVisibility(View.GONE);
                            listProduct.setVisibility(View.VISIBLE);
                            Type type = new TypeToken<ArrayList<VendorDetails>>() {
                            }.getType();
                            vendorDetailsList = gson.fromJson(object.getJSONArray("listData").toString(), type);
                            nearByShopAdapter = new NearByShopAdapter("","",vendorDetailsList, listner, mContext);
                            serviceVerndoList.setAdapter(nearByShopAdapter);

                        } else {
                            processBar.setVisibility(View.GONE);
                            listProduct.setVisibility(View.GONE);

                            Util.show(mContext, object.getString("message"));
                        }
                    } catch (JSONException e) {
                        processBar.setVisibility(View.GONE);
                        listProduct.setVisibility(View.GONE);

                        Util.show(mContext, e.getMessage());
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
                    processBar.setVisibility(View.GONE);
                    listProduct.setVisibility(View.GONE);

                    Util.show(mContext, t.getMessage());

                }
            });

        }
    }



    @Override
    public void onOptionClick(String liveTest, int pos) {
        if(pos==0){

            if(edit_name.getText().toString().length()==0){
                Util.show(mContext,"Please enter any search keyword");
                //  Util.show(mContext,"Please enter any search keyword");
            }else{
                typeView="Product";
                listProduct.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                getSearchData();
            }
        }else if(pos==1){
            if(edit_name.getText().toString().length()==0){
                Util.show(mContext,"Please enter any search keyword");
                //  Util.show(mContext,"Please enter any search keyword");
            }else{
                getVendor();
                typeView="Shop";
                // listProduct.setLayoutManager(new GridLayoutManager(mContext, 3));
                listProduct.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

            }

        }else if(pos==2){
            if(edit_name.getText().toString().length()==0){
                Util.show(mContext,"Please enter any search keyword");
                //  Util.show(mContext,"Please enter any search keyword");
            }else{

                getCategory();

                typeView="Category";
                listProduct.setLayoutManager(new GridLayoutManager(mContext, 3));

            }

        }else if(pos==3){
            if(edit_name.getText().toString().length()==0){
                Util.show(mContext,"Please enter any search keyword");
                //  Util.show(mContext,"Please enter any search keyword");
            }else{

                getServiceCategory();

                typeView="getServiceCategory";
                listProduct.setLayoutManager(new GridLayoutManager(mContext, 3));

            }

        }else if(pos==4){
            if(edit_name.getText().toString().length()==0){
                Util.show(mContext,"Please enter any search keyword");
                //  Util.show(mContext,"Please enter any search keyword");
            }else{

                getServiceVendor();

                typeView="getServiceCategory";
                listProduct.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

            }

        }

    }

    @Override
    public void productRemove(SearchByProduct liveTest, int pos) {

    }

    @Override
    public void productClick(SearchByProduct listData, int pos) {
        Gson gson = new Gson();
        Type productList = new TypeToken<Product>() {
        }.getType();
        Product ob= gson.fromJson(gson.toJson(listData), productList);
        Intent mv = new Intent(mContext, VarriantActivity.class);
        mv.putExtra("product", ob);
        mv.putExtra("productId", listData.getProductId());
        mv.putExtra("vendor_id", listData.getVendorId());
        startActivity(mv);
    }

    @Override
    public void addCartClick(SearchByProduct liveTest, int pos, String subAdd, ArrayList<SearchByProduct> listProduct, View showProduct) {
        Log.e("bhs", "hghj");

        int itemCountValue = 0, price = 0;
        for (int i = 0; i < listProduct.size(); i++) {
            if (Integer.parseInt(listProduct.get(i).getUserQuantity()) > 0) {
                itemCountValue++;
                price = price + Integer.parseInt(listProduct.get(i).getPrice())*Integer.parseInt(listProduct.get(i).getUserQuantity());

            }
        }

        addCart(liveTest,subAdd,showProduct);
    }

    private void addCart(SearchByProduct data, String addSub,View cardv) {

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




    private void getAutoSearchData(){
        if (InternetConnection.checkConnection(mContext)) {
            autoSearchArrayList=new ArrayList<>();
            Map<String, String> params = new HashMap<>();
            params.put("userId",userDetails.getUserId());
            params.put("latitude",address.get(UserSharedPreference.CurrentLatitude));
            params.put("longitude",address.get(UserSharedPreference.CurrentLongitude));

            Log.e("bfbv",params.toString());
            RestClient.post().searchProduct(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    try {

                        JSONObject object = new JSONObject(Objects.requireNonNull(response.body()));
                        JSONObject objectList=object.getJSONObject("listData");
Log.e("lisdataa",objectList.toString());
                        Gson gson = new Gson();
                        Type type1 = new TypeToken<ArrayList<AutoSearch>>() {
                        }.getType();
                        ArrayList<AutoSearch> ecomVendorList = gson.fromJson(objectList.getJSONArray("ecomVendorList").toString(), type1);
                        autoSearchArrayList.addAll(ecomVendorList);
                        Type type2 = new TypeToken<ArrayList<AutoSearch>>() {
                        }.getType();
                        ArrayList<AutoSearch> serviceVendorList = gson.fromJson(objectList.getJSONArray("serviceVendorList").toString(), type2);
                        autoSearchArrayList.addAll(serviceVendorList);
                        Type type3 = new TypeToken<ArrayList<AutoSearch>>() {
                        }.getType();
                        ArrayList<AutoSearch> productList = gson.fromJson(objectList.getJSONArray("productList").toString(), type3);
                        autoSearchArrayList.addAll(productList);
                        Type type4 = new TypeToken<ArrayList<AutoSearch>>() {
                        }.getType();
                        ArrayList<AutoSearch> ecomcategoryList = gson.fromJson(objectList.getJSONArray("ecomcategoryList").toString(), type4);
                        autoSearchArrayList.addAll(ecomcategoryList);
                        Type type5 = new TypeToken<ArrayList<AutoSearch>>() {
                        }.getType();
                        ArrayList<AutoSearch> serviceCategoryList = gson.fromJson(objectList.getJSONArray("serviceCategoryList").toString(), type5);
                        autoSearchArrayList.addAll(serviceCategoryList);
                        autoSearchAdapter=new AutoSearchAdapter(autoSearchArrayList,listner,mContext);
                        autoSearchList.setAdapter(autoSearchAdapter);

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
    public void catServiceClick(Category liveTest, int pos, String type) {
        if(liveTest.getCategoryId().equalsIgnoreCase("29")){
           // onMenuListClicklistener.clickService(liveTest,pos);
        }else{
            Log.e("liveTest   ", liveTest.getCategoryId());
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra("fragmentToDisplay", "nearShop");
            intent.putExtra("catgeory", liveTest);
            startActivity(intent);
        }
    }

    @Override
    public void onOptionClick(VendorDetails data, int pos) {
        if(data.getMasterCategoryId().equalsIgnoreCase("1")){
            Intent mv =new Intent(mContext, ServiceShop.class);
            mv.putExtra("MyData", data);
            mv.putExtra("categoryId", data.getMasterCategoryId());

            Objects.requireNonNull(this).getIntent().getSerializableExtra("MyData");
            startActivity(mv);
        }else{
            Intent mv =new Intent(this, ShopDetails.class);
            mv.putExtra("MyData", data);
            mv.putExtra("categoryId", data.getMasterCategoryId());
           // Log.e("cat",categoryData.getCategoryId());
            Objects.requireNonNull(this).getIntent().getSerializableExtra("MyData");
            startActivity(mv);
        }
        Log.e("vendorshop","shop");
    }

    @Override
    public void onHeartClick(VendorDetails data, String status) {

    }

    @Override
    public void onOptionClick(AutoSearch liveTest) {

        autoSearchList.setVisibility(View.GONE);
        listProduct.setVisibility(View.VISIBLE);
        edit_name.setText(liveTest.getName());
        getSearchData();
        //  getCategory();
        getVendor();
        getServiceVendor();
        getServiceCategory();
        getCategory();
        tvEcomList.setVisibility(View.VISIBLE);
        tvserviceList.setVisibility(View.VISIBLE);
        tvEcompCat.setVisibility(View.VISIBLE);
        tvServiceCat.setVisibility(View.VISIBLE);

    }
}