package com.dbcorp.apkaaada.ui.auth.fragments.nearby;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.Adapter.NearCatAdapter;
import com.dbcorp.apkaaada.Adapter.ShopCategory;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.OTP;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.home.Category;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.Home.HomeActivity;
import com.dbcorp.apkaaada.ui.auth.fragments.product.shopview;
import com.dbcorp.apkaaada.ui.auth.fragments.shop.nearbyShop;
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

public class nearbycat extends Fragment implements ShopCategory.OnMeneuClickListnser {

    Context mContext;
    nearbycat listner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public static nearbycat getInstance(String data) {
        nearbycat myExamFragment = new nearbycat();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        myExamFragment.setArguments(bundle);
        return myExamFragment;
    }

    View view;
    RecyclerView listItem;
    String catType;
    UserDetails userDetails;
    ArrayList<Category> categoriesList;
    ShopCategory shopCategory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_near_by_cat, container, false);
        this.listner = this;
        categoriesList = new ArrayList<>();
        userDetails = new SqliteDatabase(getActivity()).getLogin();
        init();
        return view;
    }

    private void init() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            catType = bundle.getString("data");
        }
        listItem = view.findViewById(R.id.listItem);
        listItem.setHasFixedSize(true);
        listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        // listItem.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        getCategory();

    }


    private void getCategory() {
        if (InternetConnection.checkConnection(mContext)) {

            Map<String, String> params = new HashMap<>();
            params.put("mastCatId", catType.equalsIgnoreCase("service") ? "1" : "2");

            RestClient.post().getCatData(userDetails.getSk(), ApiService.APP_DEVICE_ID, params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {


                    Gson gson = new Gson();

                    JSONObject object = null;
                    try {
                        object = new JSONObject(response.body());
                        if (object.getBoolean("status")) {

                            Type type = new TypeToken<ArrayList<Category>>() {
                            }.getType();
                            categoriesList = gson.fromJson(object.getJSONArray("cateogory").toString(), type);
                            shopCategory = new ShopCategory("allcat",categoriesList, listner, mContext);
                            listItem.setAdapter(shopCategory);

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
    public void catServiceClick(Category liveTest, int pos, String type) {

        nearbyShop categoryObj = nearbyShop.getInstance(liveTest);
        ((HomeActivity) Objects.requireNonNull(mContext)).loadFragment(categoryObj, "");

    }
}
