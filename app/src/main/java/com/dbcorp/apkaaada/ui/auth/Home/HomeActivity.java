package com.dbcorp.apkaaada.ui.auth.Home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.dbcorp.apkaaada.Adapter.DroupdownMenuAdapter;
import com.dbcorp.apkaaada.Adapter.HomeAdapter;
import com.dbcorp.apkaaada.Adapter.MenuListAdapter;
import com.dbcorp.apkaaada.Adapter.NearByShopAdapter;
import com.dbcorp.apkaaada.Adapter.usercard.UserAddressAdapter;
import com.dbcorp.apkaaada.BuildConfig;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.database.UserSharedPreference;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.DroupDownModel;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.VendorDetails;
import com.dbcorp.apkaaada.model.card.Coupon;
import com.dbcorp.apkaaada.model.card.UserAddress;
import com.dbcorp.apkaaada.model.home.HomeData;
import com.dbcorp.apkaaada.model.home.HomeUser;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.Login;
import com.dbcorp.apkaaada.ui.auth.Offer.ViewOffer;
import com.dbcorp.apkaaada.ui.auth.WebUi.PrivacyPolicy;
import com.dbcorp.apkaaada.ui.auth.WebUi.TermsCondition;
import com.dbcorp.apkaaada.ui.auth.fragments.AutoCompleteDemo;
import com.dbcorp.apkaaada.ui.auth.fragments.Home;
import com.dbcorp.apkaaada.ui.auth.fragments.ServiceShop;
import com.dbcorp.apkaaada.ui.auth.fragments.SetAddressActivity;
import com.dbcorp.apkaaada.ui.auth.fragments.account.account;

import com.dbcorp.apkaaada.ui.auth.fragments.location.maps.SearchAddress;
import com.dbcorp.apkaaada.ui.auth.fragments.nearby.nearbycat;
import com.dbcorp.apkaaada.ui.auth.fragments.order.ActivityOrder;
import com.dbcorp.apkaaada.ui.auth.fragments.order.InvoiceActivityOrder;
import com.dbcorp.apkaaada.ui.auth.fragments.order.myorder.ActivityOrderList;
import com.dbcorp.apkaaada.ui.auth.fragments.order.myorder.MyOrder;
import com.dbcorp.apkaaada.ui.auth.fragments.product.UserSearchActivity;
import com.dbcorp.apkaaada.ui.auth.fragments.product.VarriantActivity;
import com.dbcorp.apkaaada.ui.auth.fragments.shop.CustomActivity;
import com.dbcorp.apkaaada.ui.auth.searchbyslider.MyhelpList;
import com.dbcorp.apkaaada.ui.auth.searchbyslider.SliderByWeb;
import com.dbcorp.apkaaada.ui.auth.userservice.ChatBookingList;
import com.dbcorp.apkaaada.ui.auth.wishlist.WishList;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements MenuListAdapter.OnMeneuClickListnser, DroupdownMenuAdapter.OnMeneuClickListnser,UserAddressAdapter.OnClickListener {

    private FrameLayout frameLayout;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    PopupWindow popupWindow;
    UserAddressAdapter userAddressAdapter;
    String token;
    ArrayList<UserAddress> userAddressesList;
    private Context mContext;
    private static AlertDialog alertDialog;
    private HomeActivity listner;
    private ActionBarDrawerToggle toggle;
    private CoordinatorLayout llRoot;
    BottomNavigationView navView;
    MaterialButton logoutBtn;
    String arrItems[] = new String[]{};
    RecyclerView menuList;
    MenuListAdapter menuListAdapter;

    UserDetails userDetails;
    MaterialTextView   tvCityName,tvSearch,tvAddress,tvName,tvMobile;

    ArrayList<DroupDownModel> listData;
    UserSharedPreference userSharedPreference;

    private static int AUTOCOMPLETE_REQUEST_CODE = 1;

    // Set the fields to specify which types of place data to
    // return after the user has made a selection.
    List<Place.Field> fields ;
    HashMap<String, String> address;
    AppCompatImageView tvNoti,offerClick,tvWishList;

    ShapeableImageView locred;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userSharedPreference=new UserSharedPreference(this);
        address=userSharedPreference.getAddress();
        userDetails = new SqliteDatabase(this).getLogin();
        mContext = this;
        listner=this;
        init();
        tvAddress.setText(address.get(UserSharedPreference.CurrentAddress));


        requestCameraPermission();


        toolbar.setTitle("Dashboard");
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.api_key), Locale.US);
        }
        fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

    }


    private void requestCameraPermission() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if(address.get(UserSharedPreference.CurrentLongitude)!=null && tvAddress.getText().length()>0){

                                tvAddress.setText(address.get(UserSharedPreference.CurrentAddress));
                                loadFragment(new Home(), "HomeFragment");
                            }else{
                                Intent mv=new Intent(HomeActivity.this, SearchAddress.class);
                                mv.putExtra("type","current");
                                startActivity(mv);
                                finish();
                            }



                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showPermissionsAlert();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }


    @Override
    protected void onResume() {
        super.onResume();
        requestCameraPermission();
    }

    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new  AlertDialog.Builder(this);
        builder.setCancelable(false);
        alertDialog=builder.create();
        alertDialog.show();
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.cancel();
                        Intent i = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.cancel();
                        Intent i = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                        startActivity(i);
                        finish();
                    }
                }).show();
    }

    private void init() {
        userAddressesList=new ArrayList<>();
        listData=new ArrayList<>();
        llRoot = findViewById(R.id.llRoot);
        tvCityName=findViewById(R.id.tvCityName);
        logoutBtn = findViewById(R.id.btnLogout);
        navView = findViewById(R.id.nav_view);
        tvAddress=findViewById(R.id.tvAddress);
        locred=findViewById(R.id.locred);
        offerClick=findViewById(R.id.offerClick);
        tvSearch=findViewById(R.id.tvSearch);
        tvMobile=findViewById(R.id.tvMobile);
        tvName=findViewById(R.id.tvName);
        tvWishList=findViewById(R.id.tvWishList);
        tvNoti=findViewById(R.id.tvNoti);
        tvName.setText(userDetails.getName());
        tvMobile.setText(userDetails.getNumber());

        tvSearch.setOnClickListener(v->{
            Intent mv = new Intent(mContext, UserSearchActivity.class);
            mv.putExtra("type", "no");
           mv.putExtra("keyWord", "");
           // mv.putExtra("vendor_id", vendorDetails.getUserId());
            startActivity(mv);

        });
        logoutBtn.setOnClickListener(v -> {
            Logout();
        });
        navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homeId:
                    loadFragment(new Home(), "");
                    break;
                case R.id.service:
                    nearbycat serviceObj = nearbycat.getInstance("service");
                    loadFragment(serviceObj, "");
                    break;
                case R.id.category:
                    nearbycat categoryObj = nearbycat.getInstance("category");
                    loadFragment(categoryObj, "");
                    break;
                case R.id.myorder:
                    Intent mv2 = new Intent(HomeActivity.this, MyOrder.class);
                    startActivity(mv2);
                    break;

                case R.id.account:
                    loadFragment(new account(), "");
                    break;
            }
            return true;

        });
        tvNoti.setOnClickListener(v->{
            Intent wishListMv = new Intent(HomeActivity.this, NotificationActivity.class);
            startActivity(wishListMv);
        });
        tvWishList.setOnClickListener(v->{
            Intent wishListMv = new Intent(HomeActivity.this, WishList.class);
            startActivity(wishListMv);
        });
        offerClick.setOnClickListener(v->{
            Intent offer = new Intent(HomeActivity.this, ViewOffer.class);
            startActivity(offer);
        });
        menuList = findViewById(R.id.menuList);
        arrItems = (HomeActivity.this.getResources().getStringArray(R.array.arr_nav_service_items));
        menuList.setHasFixedSize(true);
        menuList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        menuListAdapter = new MenuListAdapter(arrItems, this, mContext);
        menuList.setAdapter(menuListAdapter);
        frameLayout = findViewById(R.id.frameLayout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        drawer.setScrimColor(Color.TRANSPARENT);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

//                float slideX = drawerView.getWidth() * slideOffset;
//                content.setTranslationX(slideX);

                float scaleFactor = 80f;

                float slideX = drawerView.getWidth() * slideOffset;
                llRoot.setTranslationX(slideX);
                llRoot.setScaleX(1 - (slideOffset / scaleFactor));
                llRoot.setScaleY(1 - (slideOffset / scaleFactor));
            }
        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setClick();
        getAddress();
        tvAddress.setOnClickListener(v->{
            Intent mv=new Intent(HomeActivity.this, SearchAddress.class);
            mv.putExtra("type","current");
            startActivity(mv);
            finish();
        });

        locred.setOnClickListener(v->{
            Intent mv=new Intent(HomeActivity.this, SearchAddress.class);
            mv.putExtra("type","current");
            startActivity(mv);
            finish();
        });
//        findViewById(R.id.btnLogout).setOnClickListener(v->{
//            Logout();
//        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();
                        UpdateNotification();
                        // Log and toast
                        Log.e("msg_token_fmt", token);
                        //Toast.makeText(HomeActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                //tvAddress.setText(place.getName());
                Log.e("TAG", "Place: " + place.getLatLng() + ", " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private  void UpdateNotification(){
        if (InternetConnection.checkConnection(mContext)) {
         //   Util.showDialog("Please wait..",mContext);
            Map<String, String> params = new HashMap<>();
            params.put("user_id", userDetails.getUserId());
            params.put("notification_token", token);


            Log.e("vendor_id",params.toString());
            RestClient.post().updateNotification(ApiService.APP_DEVICE_ID,userDetails.getSk(),params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message",object.getString("message"));

                        if (object.getBoolean("status")) {

                            //Util.show(mContext,"Successfully updated your setting data");


                        //    Util.hideDialog();
                        } else {
                       //     Util.hideDialog();
                            Util.show(mContext, "something is wrong");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                      //  Util.hideDialog();

                    }


                }

                @Override
                public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {

                    try {
                        t.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                 //   Util.hideDialog();
                }
            });

        }
    }
    private  void setClick(){
        tvCityName.setOnClickListener(v->{

        });
    }

    private void Logout() {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    try {
                        new SqliteDatabase(mContext).removeLoginUser();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(HomeActivity.this, Login.class));
                    finish();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        };
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(HomeActivity.this);
        builder.setMessage("Do you want to logout from the app?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    public void loadFragment(Fragment fragment, String fragName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onOptionClick(String liveTest, int pos) {
        Util.show(mContext,""+pos);
        switch (pos) {
            case 1:
                Intent mv = new Intent(HomeActivity.this, ActivityOrder.class);
                startActivity(mv);
                break;
            case 4:
                Intent wishListMv = new Intent(HomeActivity.this, WishList.class);
                startActivity(wishListMv);
                break;
            case 5:
                Intent offer = new Intent(HomeActivity.this, ViewOffer.class);
                startActivity(offer);
                break;
            case 7:
                Intent help = new Intent(HomeActivity.this, HelpActivity.class);
                startActivity(help);
                break;
            case 3:
                Intent mv2 = new Intent(HomeActivity.this, MyOrder.class);
                startActivity(mv2);
                break;
            case 6:
                Intent mv3= new Intent(HomeActivity.this, ChatBookingList.class);
                startActivity(mv3);
                break;
            case 8:
                Intent helpList = new Intent(HomeActivity.this, MyhelpList.class);
                startActivity(helpList);
                break;
            case 9:
                Intent termC = new Intent(HomeActivity.this, TermsCondition.class);
                startActivity(termC);
                break;
            case 10:
                Intent intent = new Intent(mContext, PrivacyPolicy.class);
                startActivity(intent);
                break;
            case 2:
                drawer.closeDrawers();
                loadFragment(new account(), "");
                break;


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
        popupWindow.showAtLocation(popupView, Gravity.TOP, 0, 0);
        ImageView closeImg=popupView.findViewById(R.id.closeImg);
        EditText search=popupView.findViewById(R.id.search);
        MaterialTextView tvChoose=popupView.findViewById(R.id.tvChoose);
        search.setVisibility(View.GONE);
        LinearLayoutCompat addressLayout=popupView.findViewById(R.id.addressLayout);
        TextView tittleName=popupView.findViewById(R.id.tittleName);
        EditText inputSearch=popupView.findViewById(R.id.search);
        tittleName.setVisibility(View.VISIBLE);
        tittleName.setText("Select Address");
        RecyclerView listCountryData2 = popupView.findViewById(R.id.listView);
        addressLayout.setVisibility(View.VISIBLE);
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
    public void onOptionClick(DroupDownModel dataList) {
        Util.hideDropDown();
        tvCityName.setText(dataList.getDescription());
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
                           // Util.show(mContext,object.getString("message"));
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

                            //tvAddress.setText(object.getJSONObject("userAddress").getString("address"));
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
}
