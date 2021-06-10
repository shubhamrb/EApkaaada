package com.dbcorp.apkaaada.ui.auth.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.VendorDetails;
import com.dbcorp.apkaaada.ui.auth.fragments.location.maps.GetCurrentLocation;
import com.dbcorp.apkaaada.ui.auth.fragments.location.maps.MapsFragment;
import com.dbcorp.apkaaada.ui.auth.fragments.product.shopview;

import java.util.Objects;

public class SetAddressActivity extends AppCompatActivity {

    private Toolbar toolbar;


    Intent g;
    static String fragmentType = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_activity_custom);
        g=getIntent();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My Location");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

       if(g.getStringExtra("type").equalsIgnoreCase("deliver")){
            MapsFragment nav = new MapsFragment();
            ((SetAddressActivity) Objects.requireNonNull(this)).loadFragment(nav, "location");

        }else{
            GetCurrentLocation nav = new GetCurrentLocation();
            ((SetAddressActivity) Objects.requireNonNull(this)).loadFragment(nav, "location");

        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


            finish();



    }

    public void loadFragment(Fragment fragment, String fragName) {
        fragmentType = fragName;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
