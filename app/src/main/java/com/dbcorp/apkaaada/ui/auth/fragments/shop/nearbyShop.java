package com.dbcorp.apkaaada.ui.auth.fragments.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.Adapter.NearByShopAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.database.UserSharedPreference;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.VendorDetails;
import com.dbcorp.apkaaada.model.home.Category;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.fragments.ServiceShop;
import com.dbcorp.apkaaada.ui.auth.fragments.ShopDetails;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class nearbyShop extends Fragment implements NearByShopAdapter.OnMeneuClickListnser{

    Context mContext;
    nearbyShop listner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }


    public static nearbyShop getInstance(Category data) {
        nearbyShop myExamFragment = new nearbyShop();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        myExamFragment.setArguments(bundle);
        return myExamFragment;
    }
    HashMap<String, String> address;
    ArrayList<VendorDetails> vendorDetailsList;
    NearByShopAdapter nearByShopAdapter;
    View view;
    RecyclerView listItem;
    Category categoryData;
    UserDetails userDetails;
    AppCompatImageView noShopFoundImg;
    UserSharedPreference userSharedPreference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_near_by_shop, container, false);
        this.listner=this;
        init();
        return view;
    }

    private void init() {
        userDetails = new SqliteDatabase(getActivity()).getLogin();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            categoryData = (Category) bundle.getSerializable("data");
        }
        vendorDetailsList=new ArrayList<>();
        noShopFoundImg=view.findViewById(R.id.noShopFoundImg);
        listItem = view.findViewById(R.id.listItem);
        listItem.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        listItem.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        userSharedPreference=new UserSharedPreference(mContext);
        address=userSharedPreference.getAddress();
        getHomeData();
    }



    public void getHomeData() {
        if (InternetConnection.checkConnection(mContext)) {
            vendorDetailsList.clear();
            Map<String, String> params = new HashMap<>();
            params.put("mastCatId", categoryData.getCategoryId());
            params.put("userId",userDetails.getUserId());
            params.put("lat",address.get(UserSharedPreference.CurrentLatitude));
            params.put("long",address.get(UserSharedPreference.CurrentLongitude));
            Log.e("is",params.toString());
            RestClient.post().getVendor(userDetails.getSk(), ApiService.APP_DEVICE_ID,params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message",object.getString("message"));

                        if (object.getBoolean("status")) {
                            listItem.setVisibility(View.VISIBLE);
                            noShopFoundImg.setVisibility(View.GONE);
                            Type type = new TypeToken<ArrayList<VendorDetails>>() {
                            }.getType();
                            vendorDetailsList = gson.fromJson(object.getJSONArray("listData").toString(), type);
                            nearByShopAdapter = new NearByShopAdapter(address.get(UserSharedPreference.CurrentLatitude),address.get(UserSharedPreference.CurrentLongitude),vendorDetailsList, listner, mContext);
                            listItem.setAdapter(nearByShopAdapter);

                        } else {
                            listItem.setVisibility(View.GONE);
                            noShopFoundImg.setVisibility(View.VISIBLE);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Util.show(mContext, e.getMessage());
                    }


                }

                @Override
                public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {

                    try {
                        t.printStackTrace();
                    } catch (Exception e) {
                        Util.show(mContext, e.getMessage());
                        e.printStackTrace();
                    }

                }
            });

        }
    }


    @Override
    public void onOptionClick(VendorDetails data, int pos) {

        Log.e("getMasterCategoryId", data.getMasterCategoryId());
        if(data.getMasterCategoryId().equalsIgnoreCase("1")){
            Intent mv =new Intent(getActivity(), ServiceShop.class);
            mv.putExtra("MyData", data);
            mv.putExtra("categoryId", categoryData.getCategoryId());

            Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra("MyData");
            startActivity(mv);
        }else{
            Intent mv =new Intent(getActivity(), ShopDetails.class);
            mv.putExtra("MyData", data);
            mv.putExtra("categoryId", categoryData.getCategoryId());
            Log.e("cat",categoryData.getCategoryId());
            Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra("MyData");
            startActivity(mv);
        }



    }

    @Override
    public void onResume() {
        super.onResume();
        listItem.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        listItem.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        getHomeData();
    }

    @Override
    public void onHeartClick(VendorDetails data,String status) {
        likeDislike(data,status);
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

                            nearByShopAdapter.notifyDataSetChanged();

                        } else {
                            Util.show(mContext, "there is no shop available ");
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
