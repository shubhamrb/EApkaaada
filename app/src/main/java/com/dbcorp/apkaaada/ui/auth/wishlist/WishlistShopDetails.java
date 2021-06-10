package com.dbcorp.apkaaada.ui.auth.wishlist;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.dbcorp.apkaaada.Adapter.ShopProduct;
import com.dbcorp.apkaaada.Adapter.SliderAdaptercarouseList;
import com.dbcorp.apkaaada.Adapter.shopview.SubCatAdapter;
import com.dbcorp.apkaaada.Adapter.shopview.SubToSubCatAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.Carousel;
import com.dbcorp.apkaaada.model.Carouseloffer;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.VendorDetails;
import com.dbcorp.apkaaada.model.shopview.Product;
import com.dbcorp.apkaaada.model.shopview.SubCategory;
import com.dbcorp.apkaaada.model.shopview.SubToSubCategory;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.fragments.order.ActivityOrder;
import com.dbcorp.apkaaada.ui.auth.fragments.product.VarriantActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistShopDetails extends AppCompatActivity implements SubToSubCatAdapter.OnClickListener, ShopProduct.OnMeneuClickListnser, SubCatAdapter.OnClickListener {

    private Toolbar toolbar;
    Intent getInent;

    Context mContext;
    int actionbarheight;
    String catId="";
    static String fragmentType = "";
    WishlistShopDetails listner;
    VendorDetails vendorDetails;
    static String masterCatId = "";

    String arrItems[] = new String[]{"One", "One", "One", "One"};
    ShopProduct shopProduct;
    SubCatAdapter subcat;
    SubToSubCatAdapter subtoSubCat;
    View view;
    RecyclerView listItem, listSubCat, listSubToCat;
    private SliderAdaptercarouseList sliderAdapter;
    private ArrayList<Carouseloffer> carouselList;
    private ArrayList<Product> productList;
    private ArrayList<Product> productFilterList;
    AppCompatImageView mDummyImgView, mCheckoutImgView;
    private ArrayList<SubCategory> subCategoriesList;
    private ArrayList<SubCategory> subCategoriesListFilter;
    private ArrayList<SubToSubCategory> subToSubCategorieslist;
    private ArrayList<SubToSubCategory> subToSubCategoriesfillterlist;
    private ViewPager viewPager;
    private MaterialTextView btnProceed;
    private ArrayList<Carousel> listCarousel;
    private UserDetails userDetails;
    String subCatId, subtosubcatId;
    TabLayout indicator;
    Timer timer;
    LottieAnimationView hearAnimation;
    AppCompatImageView likeDislike,tvStartOne,tvStartTwo,tvStartThree,tvStartFour,tvStartFive;

    MaterialTextView itemCount,tvPrice,shopDes,rating,shopName,rateCount;
    TextInputEditText edit_name;
    LinearLayoutCompat headerLayout, layView,layoutNoProduct,btnProcess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_deatils);
        this.mContext=this;
        listner=this;
        getInent = getIntent();
        vendorDetails = (VendorDetails) getInent.getSerializableExtra("MyData");
        toolbar = findViewById(R.id.toolbar);
        mCheckoutImgView =  findViewById(R.id.chk_icon);
        mDummyImgView =    findViewById(R.id.img_cpy);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Shop Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionbarheight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        catId=getInent.getStringExtra("categoryId");
        masterCatId=catId;
init();
        Animation shake;
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        mCheckoutImgView.setAnimation(shake);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private void init() {
        userDetails = new SqliteDatabase(mContext).getLogin();
        edit_name = findViewById(R.id.edit_name);
        hearAnimation=findViewById(R.id.hearAnimation);
        likeDislike=findViewById(R.id.likeDislike);
        //like
        likeDislike.setOnClickListener(v -> {

            if(vendorDetails.getWhislistStatus().equalsIgnoreCase("0")){
                vendorDetails.setWhislistStatus("1");
            }else{
                vendorDetails.setWhislistStatus("0");
            }
            likeDislike(vendorDetails,"");
        });




        if(vendorDetails.getWhislistStatus().equalsIgnoreCase("0")){


            hearAnimation.setVisibility(View.GONE);
            likeDislike.setVisibility(View.VISIBLE);

            likeDislike.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A3A3A3")));

        }else{
            hearAnimation.setVisibility(View.GONE);
            likeDislike.setVisibility(View.VISIBLE);

            hearAnimation.setVisibility(View.VISIBLE);
            likeDislike.setVisibility(View.GONE);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    likeDislike.setVisibility(View.VISIBLE);
                    hearAnimation.setVisibility(View.GONE);
                    // Do something after 5s = 5000ms
                }
            }, 1000);


            likeDislike.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9423D")));

        }
        tvStartOne=findViewById(R.id.tvStartOne);
        tvStartTwo=findViewById(R.id.tvStartTwo);
        tvStartThree=findViewById(R.id.tvStartThree);
        tvStartFour=findViewById(R.id.tvStartFour);
        tvStartFive=findViewById(R.id.tvStartFive);
        shopDes=findViewById(R.id.shopDes);
        rateCount=findViewById(R.id.rateCount);
        shopName=findViewById(R.id.shopName);
        rating=findViewById(R.id.rating);

        listCarousel = new ArrayList<>();
        tvPrice= findViewById(R.id.tvPrice);
        btnProcess= findViewById(R.id.btnProcess);
        itemCount = findViewById(R.id.itemCount);
        viewPager = findViewById(R.id.viewPager);
        layView= findViewById(R.id.layView);
        layoutNoProduct=  findViewById(R.id.layoutNoProduct);
        btnProceed =   findViewById(R.id.btnProceed);
        indicator = findViewById(R.id.indicator);

        edit_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                String text = edit_name.getText().toString().toLowerCase(Locale.getDefault());
                Log.e("bhs==>>", text);


                shopProduct.getFilter().filter(cs);
                shopProduct.notifyDataSetChanged();

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


        //-------------product list
        listItem =  findViewById(R.id.listProduct);
        listItem.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(mContext, 3));
        listItem.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
//        listItem.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                View firstChild = listItem.getChildAt(0);
//                int topY = 0;
//                if (firstChild != null) {
//                    topY = firstChild.getTop();
//                }
//
//                int headerTopY = headerSpace.getTop();
//                listSubCat.setY(Math.max(0, headerTopY + topY));
//
//                // Set the image to scroll half of the amount that of ListView
//                headerView.setY(topY * 0.5f);
//            }
//        });

        //category list view
        listSubCat =  findViewById(R.id.listCat);
        listSubCat.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(mContext, 3));
        listSubCat.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        //-----sub cat--------
        listSubToCat = findViewById(R.id.listSubCat);
        listSubToCat.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(mContext, 3));
        listSubToCat.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        btnProceed.setOnClickListener(v -> {
            Intent mv = new Intent(mContext, ActivityOrder.class);
            startActivity(mv);
          finish();

        });

        getData();
        getCardCount();
    }

    private void getData() {
        carouselList = new ArrayList<>();
        subCategoriesList = new ArrayList<>();
        subToSubCategorieslist = new ArrayList<>();
        subToSubCategoriesfillterlist = new ArrayList<>();
        productList = new ArrayList<>();
        productFilterList = new ArrayList<>();

        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();
            params.put("vendorId", vendorDetails.getUserId());
            params.put("userId", userDetails.getUserId());

            Log.e("param", params.toString());
            RestClient.post().getOfferShop(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    try {

                        Gson gson = new Gson();
                        JSONObject object = new JSONObject(response.body());
                        Log.e("message", object.getString("message"));
                        if (object.getBoolean("status")) {
                            layView.setVisibility(View.VISIBLE);
                            layoutNoProduct.setVisibility(View.GONE);
                            btnProcess.setVisibility(View.VISIBLE);
                            Type pageViewer = new TypeToken<ArrayList<Carouseloffer>>() {
                            }.getType();
                            Type subCatType = new TypeToken<ArrayList<SubCategory>>() {
                            }.getType();
                            Type subtoSubCatType = new TypeToken<ArrayList<SubToSubCategory>>() {
                            }.getType();
                            Type productType = new TypeToken<ArrayList<Product>>() {
                            }.getType();
                            rateCount.setText(object.getJSONObject("ratingCount").getString("rateCount"));

                            shopDes.setText(object.getJSONObject("vendorSetting").getString("description"));
                            shopName.setText(object.getJSONObject("vendorSetting").getString("shop_name"));
                            rating.setText("Rating : 5/"+object.getJSONObject("vendorSetting").getString("rating"));
                            setRating(object.getJSONObject("vendorSetting").getString("rating"));

                            productList = gson.fromJson(object.getJSONArray("productList").toString(), productType);
                            productFilterList = gson.fromJson(object.getJSONArray("productList").toString(), productType);
                            shopProduct = new ShopProduct(productList, listner, mContext);
                            listItem.setAdapter(shopProduct);
                            SubCategory obj = new SubCategory();
                            obj.setSubCategoryId("0000");
                            obj.setName("ALL");
                            obj.setPhoto("");
                            subCategoriesList.add(obj);
                            JSONArray subCatList = object.getJSONArray("subCat");
                            for (int i = 0; i < subCatList.length(); i++) {

                                JSONObject str = subCatList.getJSONObject(i);
                                SubCategory objData = new SubCategory();
                                objData.setSubCategoryId(str.getString("sub_category_id"));
                                objData.setName(str.getString("name"));
                                objData.setPhoto(str.getString("photo"));
                                subCategoriesList.add(objData);

                            }

//                            subCategoriesList = gson.fromJson(object.getJSONArray("subCat").toString(), subCatType);

                            subcat = new SubCatAdapter(subCategoriesList, listner, mContext);
                            listSubCat.setAdapter(subcat);


                            SubToSubCategory subToSubobj = new SubToSubCategory();
                            subToSubobj.setSubSubCategoryId("0000");
                            subToSubobj.setSubCategoryId("0000");
                            subToSubobj.setName("ALL");
                            subToSubobj.setPhoto("");
                            subToSubCategorieslist.add(subToSubobj);
                            JSONArray subToCatList = object.getJSONArray("subToSubcatList");
                            for (int i = 0; i < subToCatList.length(); i++) {

                                JSONObject str = subToCatList.getJSONObject(i);
                                SubToSubCategory objData = new SubToSubCategory();
                                objData.setSubSubCategoryId(str.getString("sub_sub_category_id"));
                                objData.setSubCategoryId(str.getString("sub_category_id"));
                                objData.setName(str.getString("name"));
                                objData.setPhoto(str.getString("photo"));
                                subToSubCategorieslist.add(objData);

                            }

                            subToSubCategoriesfillterlist = subToSubCategorieslist;
                            subtoSubCat = new SubToSubCatAdapter(subToSubCategorieslist, listner, mContext);
                            listSubToCat.setAdapter(subtoSubCat);

                            carouselList = gson.fromJson(object.getJSONArray("sliderImg").toString(), pageViewer);

                            if(carouselList.size()>0){
                                sliderAdapter = new SliderAdaptercarouseList(mContext, carouselList);
                                viewPager.setAdapter(sliderAdapter);
                                indicator.setupWithViewPager(viewPager, true);
                                sliderAdapter.notifyDataSetChanged();
                                timer = new Timer();
                                timer.scheduleAtFixedRate(new  SliderTimer(), 4000, 6000);

                            }else{
                                indicator.setVisibility(View.GONE);
                                viewPager.setVisibility(View.GONE);
                            }

                        }else{
                            layView.setVisibility(View.GONE);
                            layoutNoProduct.setVisibility(View.VISIBLE);
                            Util.show(mContext,object.getString("message"));
                        }
                    } catch (Exception e) {


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

    private void getProduct() {

        productList = new ArrayList<>();

        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();
            params.put("sub_category_id", masterCatId);
            params.put("sub_sub_category_id", subtosubcatId);
            Log.e("param", params.toString());
            RestClient.post().getProductDetails(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    try {


                        Gson gson = new Gson();

                        JSONObject object = new JSONObject(response.body());
                        Log.e("message", object.getString("message"));


                        if (object.getBoolean("status")) {
                            Type productType = new TypeToken<ArrayList<Product>>() {
                            }.getType();
                            productList = gson.fromJson(object.getJSONArray("subCat").toString(), productType);
                            shopProduct = new ShopProduct(productList, listner, mContext);
                            listItem.setAdapter(shopProduct);

                        }
                    } catch (Exception e) {


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

    @Override
    public void catOnClick(SubCategory list, int pos, String type) {
        Log.e("data", list.getSubCategoryId());

        subCatId = list.getSubCategoryId();
        subToSubCategoriesfillterlist = new ArrayList<>();
        if (list.getSubCategoryId().equalsIgnoreCase("0000")) {
            subtoSubCat = new SubToSubCatAdapter(subToSubCategorieslist, listner, mContext);
            listSubToCat.setAdapter(subtoSubCat);
            subtoSubCat.notifyDataSetChanged();
        } else {


            for (SubToSubCategory d : subToSubCategorieslist) {
                if (d.getSubCategoryId().equals(list.getSubCategoryId())) {
                    subToSubCategoriesfillterlist.add(d);
                }
            }
            subtoSubCat = new SubToSubCatAdapter(subToSubCategoriesfillterlist, listner, mContext);
            listSubToCat.setAdapter(subtoSubCat);
            subtoSubCat.notifyDataSetChanged();

        }

    }
    @Override
    public void subCatOnClick(SubToSubCategory list, int pos, String type) {

        productFilterList = new ArrayList<>();
        if (list.getName().equalsIgnoreCase("ALL")) {
            shopProduct = new ShopProduct(productList, listner, mContext);
            listItem.setAdapter(shopProduct);
            shopProduct.notifyDataSetChanged();
        } else {
            for (Product d : productList) {
                if (d.getSubSubCategoryId().equals(list.getSubSubCategoryId())) {
                    productFilterList.add(d);
                }
            }
            shopProduct = new ShopProduct(productFilterList, listner, mContext);
            listItem.setAdapter(shopProduct);
            shopProduct.notifyDataSetChanged();
        }


    }
    @Override
    public void productClick(Product listData, int pos) {
        Log.e("mks", "mks");

        Intent mv = new Intent(mContext, VarriantActivity.class);
        mv.putExtra("product", listData);
        mv.putExtra("productId", listData.getProductId());
        mv.putExtra("vendor_id", vendorDetails.getUserId());
        startActivity(mv);
    }
    @Override
    public void addCartClick(Product liveTest, int pos, String addSub, ArrayList<Product> listProduct,View  cardv) {

        Log.e("bhs", "hghj");

        int itemCountValue = 0, price = 0;
        for (int i = 0; i < listProduct.size(); i++) {
            if (Integer.parseInt(listProduct.get(i).getUserQuantity()) > 0) {
                itemCountValue++;
                price = price + Integer.parseInt(listProduct.get(i).getPrice())*Integer.parseInt(listProduct.get(i).getUserQuantity());

            }
        }

        addCart(liveTest,addSub,cardv);


    }

    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            ((WishlistShopDetails) mContext).runOnUiThread(() -> {
                if (viewPager.getCurrentItem() < carouselList.size() - 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else {
                    viewPager.setCurrentItem(0);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (fragmentType.equalsIgnoreCase("HomeFragment")) {
            super.onBackPressed();
            finish();
        }


    }

    public void loadFragment(Fragment fragment, String fragName) {
        fragmentType = fragName;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void animateView(View foodCardView, Bitmap b) {
        mDummyImgView.setImageBitmap(b);
        mDummyImgView.setVisibility(View.VISIBLE);
        int u[] = new int[2];
        mCheckoutImgView.getLocationInWindow(u);
        mDummyImgView.setLeft(foodCardView.getLeft());
        mDummyImgView.setTop(foodCardView.getTop());
        AnimatorSet animSetXY = new AnimatorSet();
        ObjectAnimator y = ObjectAnimator.ofFloat(mDummyImgView, "translationY", mDummyImgView.getTop(), u[1] - (2 * actionbarheight));
        ObjectAnimator x = ObjectAnimator.ofFloat(mDummyImgView, "translationX", mDummyImgView.getLeft(), u[0] - (2 * actionbarheight));
        ObjectAnimator sy = ObjectAnimator.ofFloat(mDummyImgView, "scaleY", 0.8f, 0.1f);
        ObjectAnimator sx = ObjectAnimator.ofFloat(mDummyImgView, "scaleX", 0.8f, 0.1f);
        animSetXY.playTogether(x, y, sx, sy);
        animSetXY.setDuration(1000);
        animSetXY.start();
    }


    public void setRating(String rate){

        Util.show(mContext,rate);
        if(rate.equalsIgnoreCase("5.0")){
            tvStartFive.setVisibility(View.VISIBLE);
            tvStartFour.setVisibility(View.VISIBLE);
            tvStartThree.setVisibility(View.VISIBLE);
            tvStartTwo.setVisibility(View.VISIBLE);
            tvStartOne.setVisibility(View.VISIBLE);
        }else if(rate.equalsIgnoreCase("4.0")){
            tvStartFive.setVisibility(View.GONE);
            tvStartFour.setVisibility(View.VISIBLE);
            tvStartThree.setVisibility(View.VISIBLE);
            tvStartTwo.setVisibility(View.VISIBLE);
            tvStartOne.setVisibility(View.VISIBLE);

        }else if(rate.equalsIgnoreCase("3.0")){
            tvStartFive.setVisibility(View.GONE);
            tvStartFour.setVisibility(View.GONE);
            tvStartThree.setVisibility(View.VISIBLE);
            tvStartTwo.setVisibility(View.VISIBLE);
            tvStartOne.setVisibility(View.VISIBLE);
        }else if(rate.equalsIgnoreCase("2.0")){
            tvStartFive.setVisibility(View.GONE);
            tvStartFour.setVisibility(View.GONE);
            tvStartThree.setVisibility(View.GONE);
            tvStartTwo.setVisibility(View.VISIBLE);
            tvStartOne.setVisibility(View.VISIBLE);

        } else if(rate.equalsIgnoreCase("1.0")){
            tvStartFive.setVisibility(View.GONE);
            tvStartFour.setVisibility(View.GONE);
            tvStartThree.setVisibility(View.GONE);
            tvStartTwo.setVisibility(View.GONE);
            tvStartOne.setVisibility(View.VISIBLE);

        }

    }


    private void likeDislike(VendorDetails data,String active){
        if (InternetConnection.checkConnection(mContext)) {

            Map<String, String> params = new HashMap<>();
            params.put("user_id", userDetails.getUserId());
            params.put("vendor_id", data.getUserId());

            params.put("active", data.getWhislistStatus());

            Log.e("params",params.toString());
            RestClient.post().likeShop(ApiService.APP_DEVICE_ID,userDetails.getSk(),params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message",object.getString("message"));

                        if (object.getBoolean("status")) {


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
}
