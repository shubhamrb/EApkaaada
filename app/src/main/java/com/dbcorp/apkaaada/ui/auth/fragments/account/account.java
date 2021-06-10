package com.dbcorp.apkaaada.ui.auth.fragments.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.Adapter.NearCatAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.model.OTP;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.Home.HelpActivity;
import com.dbcorp.apkaaada.ui.auth.Home.HomeActivity;
import com.dbcorp.apkaaada.ui.auth.PasswordChange;
import com.dbcorp.apkaaada.ui.auth.userservice.ChatActivity;
import com.dbcorp.apkaaada.ui.auth.userservice.ChatBookingList;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class account extends Fragment {

    Context mContext;
    account listner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    View view;
    LinearLayoutCompat changePassword,tlHelp,tvMyAccount;
    UserDetails userDetails;
    MaterialTextView tvName,tvMobile,tvEmail;
   @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_account, container, false);
       init();
         return view;
    }

    private void init() {
        userDetails = new SqliteDatabase(mContext).getLogin();
        changePassword=view.findViewById(R.id.changePassword);
        tvName=view.findViewById(R.id.tvName);
        tlHelp=view.findViewById(R.id.tlHelp);
        tvMobile=view.findViewById(R.id.tvMobile);
        tvMyAccount=view.findViewById(R.id.tvMyAccount);
        tvEmail=view.findViewById(R.id.tvEmail);
        tvName.setText(userDetails.getName());
        tvMobile.setText(userDetails.getNumber());
        tvEmail.setText(userDetails.getEmail());
        tlHelp.setOnClickListener(v->{
            Intent help = new Intent(getActivity(), HelpActivity.class);
            startActivity(help);
        });

        changePassword.setOnClickListener(v->{
            Intent mv3= new Intent(mContext, PasswordChange.class);
            startActivity(mv3);
        });


        tvMyAccount.setOnClickListener(v->{
            Intent mv3= new Intent(mContext, UserAccount.class);
            startActivity(mv3);
        });

    }






}
