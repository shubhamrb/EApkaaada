package com.dbcorp.apkaaada.ui.auth.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.UserSharedPreference;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.ui.auth.Home.HomeActivity;
import com.dbcorp.apkaaada.ui.auth.fragments.location.maps.GetCurrentLocation;
import com.dbcorp.apkaaada.ui.auth.fragments.location.maps.MapsFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.textview.MaterialTextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import afu.org.checkerframework.checker.nullness.qual.NonNull;

public class AutoCompleteDemo extends AppCompatActivity {
    public  AutoCompleteTextView tvStartPoint, tvEndPoint;
    private String latitude = "";
    private String addressType = "1";
    private  String longitude = "";
    PlacesClient placesClient;
    private Toolbar toolbar;
    MaterialTextView pinMap,savebtn;
    PlacesAdapter startPointplaceAdapter;
    UserSharedPreference sessionUser;
    HashMap<String, String> address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_complete_demo);
        sessionUser=new UserSharedPreference(this);
        address=sessionUser.getAddress();
        tvStartPoint = findViewById(R.id.edtStartPoint);
        tvEndPoint =findViewById(R.id.edtEndPoint);
        toolbar = findViewById(R.id.toolbar);
        pinMap=findViewById(R.id.pinMap);
        savebtn=findViewById(R.id.savebtn);
        toolbar.setTitle("Your Location");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Setup Places Client
        if (!Places.isInitialized()) {
            // Places.initialize(getActivity(), getResources().getString(R.string.places_search));
            Places.initialize(this, "AIzaSyBAUTLllLoIeyoajizeO0BZS8HaTYHD2Y0");
        }

        pinMap.setOnClickListener(v->{
            Intent mv=new Intent(AutoCompleteDemo.this, SetAddressActivity.class);
            mv.putExtra("type","current");
            startActivity(mv);
        });
        savebtn.setOnClickListener(v->{
            if(tvStartPoint.getText().length()==0){
                Util.show(this,"Please enter your location");
                return;
            }
            sessionUser.setAddress(tvStartPoint.getText().toString(),String.valueOf(latitude),String.valueOf(longitude));

            Intent mv=new Intent(AutoCompleteDemo.this, HomeActivity.class);
            mv.putExtra("type","current");
            startActivity(mv);
            finish();
        });
        placesClient = Places.createClient(this);

        tvStartPoint.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    tvStartPoint.setThreshold(1);
                    tvStartPoint.setOnItemClickListener(autocompleteClickListener);
                    startPointplaceAdapter = new PlacesAdapter(AutoCompleteDemo.this, placesClient);
                    tvStartPoint.setAdapter(startPointplaceAdapter);
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private AdapterView.OnItemClickListener autocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            try {
                final AutocompletePrediction item = startPointplaceAdapter.getItem(i);
                String placeID = null;
                if (item != null) {
                    placeID = item.getPlaceId();
                }
//                To specify which data types to return, pass an array of Place.Fields in your FetchPlaceRequest
//                Use only those fields which are required.
                List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS
                        , Place.Field.LAT_LNG);

                FetchPlaceRequest request = null;
                if (placeID != null) {
                    request = FetchPlaceRequest.builder(placeID, placeFields)
                            .build();
                }
                if (request != null) {
                    placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onSuccess(FetchPlaceResponse task) {

                            final LatLng lotlonaddress = task.getPlace().getLatLng();
                            double ll = lotlonaddress.latitude;
                            double lo = lotlonaddress.longitude;
                            latitude=String.valueOf(lotlonaddress.latitude);
                            longitude=String.valueOf(lotlonaddress.longitude);
                            sessionUser.setAddress(tvStartPoint.getText().toString(),String.valueOf(lotlonaddress.latitude),String.valueOf(lotlonaddress.longitude));


                            Log.e("dsfsdfsdf", tvStartPoint.getText().toString());
                            Log.e("dsfsdfsdf", String.valueOf(lo));


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("fghfjhfghfgh",e.getMessage());
                            // responseView.setText(e.getMessage());
                        }
                    });
                }
            } catch (Exception e) {
                Log.e("fghfjhfghfgh",e.getMessage());
            }
        }


    };

}