package com.dbcorp.apkaaada.ui.auth.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.Adapter.DroupdownMenuAdapter;
import com.dbcorp.apkaaada.Adapter.HomeAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.DroupDownModel;
import com.dbcorp.apkaaada.model.OTP;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.home.Category;
import com.dbcorp.apkaaada.model.home.HomeData;
import com.dbcorp.apkaaada.model.home.HomeUser;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.Home.HomeActivity;
import com.dbcorp.apkaaada.ui.auth.Login;
import com.dbcorp.apkaaada.ui.auth.Offer.ViewOffer;
import com.dbcorp.apkaaada.ui.auth.fragments.location.maps.MapsFragment;
import com.dbcorp.apkaaada.ui.auth.fragments.nearby.nearbycat;
import com.dbcorp.apkaaada.ui.auth.fragments.order.InvoiceActivityOrder;
import com.dbcorp.apkaaada.ui.auth.fragments.order.PaymentMode;
import com.dbcorp.apkaaada.ui.auth.fragments.shop.NearestShop;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  Home extends Fragment implements HomeAdapter.OnMeneuClickListnser, DroupdownMenuAdapter.OnMeneuClickListnser {

    Context mContext;
    Home listner;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }


    String arrItems[] = new String[]{"One", "One", "One", "One"};
    HomeAdapter homeAdapter;
    View view;
    RecyclerView listItem;

    ArrayList<HomeData> homeDataList;
    ArrayList<DroupDownModel> serviceList;

MaterialTextView selectService;
    UserDetails userDetails;
    String serviceId;
    TextInputLayout edtComment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        this.listner=this;
        userDetails= new SqliteDatabase(getActivity()).getLogin();
        init();
        return view;
    }

    private void init() {
        serviceList=new ArrayList<>();
        listItem = view.findViewById(R.id.listItem);
        listItem.setHasFixedSize(true);
        listItem.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        getHomeData();
    }



    public void getHomeData() {
        if (InternetConnection.checkConnection(mContext)) {

            Util.showDialog("Please wait..",mContext);
            Map<String, String> params = new HashMap<>();
            params.put("userId", userDetails.getUserId());

            Log.e("sk",userDetails.getSk());
            RestClient.post().getHomeData(userDetails.getSk(), ApiService.APP_DEVICE_ID,params).enqueue(new Callback<HomeUser>() {
                @Override
                public void onResponse(@NotNull Call<HomeUser> call, Response<HomeUser> response) {

                    HomeUser obj = response.body();
                    Log.e("getMessage", obj.getMessage().toString());
                    if(obj.getMessage().equalsIgnoreCase("session expired")){
                        Logout();
                    }
                    if(obj.getStatus()){
                        homeDataList= (ArrayList<HomeData>) obj.getHomeData();
                        for(int i=0;i<homeDataList.get(2).getServiceList().size();i++){
                            DroupDownModel droupDownModel=new DroupDownModel();

                            if(i>0){
                                droupDownModel.setName(homeDataList.get(2).getServiceList().get(i).getName());
                                droupDownModel.setId(homeDataList.get(2).getServiceList().get(i).getCategoryId());
                                droupDownModel.setDescription(homeDataList.get(2).getServiceList().get(i).getName());
                                serviceList.add(droupDownModel);
                            }

                        }

                        homeAdapter = new HomeAdapter(homeDataList, listner, mContext);
                        listItem.setAdapter(homeAdapter);

                        Util.hideDialog();

                    }else{
                        Util.hideDialog();

                    }

                }

                @Override
                public void onFailure(@NotNull Call<HomeUser> call, @NotNull Throwable t) {

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

    private void Logout() {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    try {
                        new SqliteDatabase(mContext).removeLoginUser();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(mContext, Login.class));
                    getActivity().finish();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        };
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setMessage("You have Login in another device please logout and login again?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    @Override
    public void onOptionClick(String liveTest, int pos) {

    }

    @Override
    public void clickView(HomeData liveTest, int pos) {


        if(pos==0){

            Intent mv =new Intent(getActivity(), ViewOffer.class);
            startActivity(mv);
        }else if(pos==1){
            nearbycat categoryObj = nearbycat.getInstance("category");
            ((HomeActivity) Objects.requireNonNull(getActivity())).loadFragment(categoryObj, "location");

        }else if(pos==2){
            nearbycat serviceObj = nearbycat.getInstance("service");

            ((HomeActivity) Objects.requireNonNull(getActivity())).loadFragment(serviceObj, "location");

        }else if(pos==4){
            Intent mv =new Intent(getActivity(), NearestShop.class);
            startActivity(mv);
        }

    }

    @Override
    public void clickService(Category liveTest, int pos) {

        if(liveTest.getCategoryId().equalsIgnoreCase("29")){
            chargeDialog();
        }

    }

    public void chargeDialog(){
        AlertDialog alertDialog;

        AlertDialog.Builder builder2 = new AlertDialog.Builder(mContext);
        builder2.setCancelable(false);
        View dialog = LayoutInflater.from(mContext).inflate(R.layout.layout_service_request, null);

         edtComment=dialog.findViewById(R.id.edtComment);
        AppCompatImageView tvClose=dialog.findViewById(R.id.tvClose);
        MaterialTextView btnProceedBtn=dialog.findViewById(R.id.btnProceedBtn);

          selectService=dialog.findViewById(R.id.selectService);
        builder2.setView(dialog);
        selectService.setOnClickListener(v->{
            Util.showDropDown(serviceList,"Please Select Service Category",mContext,listner);
        });



        alertDialog = builder2.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        alertDialog.show();
        tvClose.setOnClickListener(v->{
            alertDialog.dismiss();
            alertDialog.cancel();
        });

        btnProceedBtn.setOnClickListener(v->{
            addService(edtComment.getEditText().getText().toString());
        });

    }

    @Override
    public void onOptionClick(DroupDownModel liveTest) {
        Util.hideDropDown();
        serviceId=liveTest.getId();
        selectService.setText(liveTest.getName());

    }



    public void addService(String content){
        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();

            params.put("user_id", userDetails.getUserId());
            params.put("service_id", serviceId);
            params.put("content", content);
            Log.e("data", params.toString());
            RestClient.post().addService(userDetails.getSk(), ApiService.APP_DEVICE_ID,params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {


                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message", object.getString("message"));

                        if (object.getBoolean("status")) {

                            Util.show(mContext, object.getString("message"));

                        } else {
                            Util.show(mContext, object.getString("message"));
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
