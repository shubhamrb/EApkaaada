package com.dbcorp.apkaaada.ui.auth.fragments.location.maps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.UserSharedPreference;
import com.dbcorp.apkaaada.helper.AppUtils;
import com.dbcorp.apkaaada.ui.auth.Home.HomeActivity;
import com.dbcorp.apkaaada.ui.auth.fragments.AutoCompleteDemo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SearchAddress extends AppCompatActivity implements OnMapReadyCallback,
        LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    private LatLng mCenterLatLong;
    private GoogleMap mMap;
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    String TAG=getClass().getSimpleName();
    TextView getPlace;
    protected String mAddressOutput;
    protected String mAreaOutput;
    protected String mCityOutput;
    protected String mStateOutput;
    private AppCompatTextView shortName,compAddress;
    private AddressResultReceiver mResultReceiver;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    TextView searchLocation;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Context mContext;
    UserSharedPreference sessionUser;
    TextView mLocationMarkerText;
    String latitude,longitude,addressName,localAddress;
    ProgressBar my_progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_location_layout);
        mContext=this;
        sessionUser=new UserSharedPreference(this);
        mResultReceiver = new AddressResultReceiver(new Handler());
        mLocationMarkerText = (TextView) findViewById(R.id.locationMarkertext);
        searchLocation=findViewById(R.id.searchLocation);
        shortName =findViewById(R.id.shortName);
        my_progressBar=findViewById(R.id.my_progressBar);
        compAddress = findViewById(R.id.compAddress);
        String apiKey = getString(R.string.api_key);
        if(apiKey.isEmpty()){

            return;
        }

        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        searchLocation.setOnClickListener(v->{
            List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS_COMPONENTS, Place.Field.NAME);

// Start the autocomplete intent.
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(this);
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
                mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
                    @Override
                    public void onCameraMoveStarted(int i) {
                        my_progressBar.setVisibility(View.VISIBLE);
                    }
                });
                mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                    @Override
                    public void onCameraChange(CameraPosition cameraPosition) {
                        Log.d("Camera postion change" + "", cameraPosition + "");
                        mCenterLatLong = cameraPosition.target;


                        mMap.clear();

                        try {

                            Location mLocation = new Location("");
                            mLocation.setLatitude(mCenterLatLong.latitude);
                            mLocation.setLongitude(mCenterLatLong.longitude);


                            double longitude1 = mCenterLatLong.longitude;
                            double latitude1 =mCenterLatLong.latitude;


                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                            try {
                                List<Address> listAddresses = geocoder.getFromLocation(latitude1, longitude1, 1);
                                if (null != listAddresses && listAddresses.size() > 0) {
                                    //getlongitude=String.valueOf(longitude1);
                                    // getlatitude=String.valueOf(latitude1);
//                    Here we are finding , whatever we want our marker to show when clicked
                                    String state = listAddresses.get(0).getAdminArea();
                                    String country = listAddresses.get(0).getCountryName();
                                    String subLocality = listAddresses.get(0).getSubLocality();

                                    //String addresstxt=listAddresses.get(0).getAddressLine(0);
                                    String log= String.valueOf(listAddresses.get(0).getAddressLine(0));
                                    // Toast.makeText(Takeattendnace.this,log,Toast.LENGTH_LONG).show();

                                    Log.e("currentplacename",listAddresses.get(0).getLocality());
                                   // current.setText(String.valueOf(listAddresses.get(0).getAddressLine(0)));
                                    latitude= String.valueOf(mCenterLatLong.latitude);
                                    longitude= String.valueOf(mCenterLatLong.longitude);
                                    addressName=listAddresses.get(0).getAddressLine(0);
                                    localAddress=listAddresses.get(0).getLocality();
                                    shortName.setText(listAddresses.get(0).getLocality());
                                    compAddress.setText(listAddresses.get(0).getAddressLine(0));
                                    mLocationMarkerText.setText(listAddresses.get(0).getAddressLine(0)+"\n Lat : " + mCenterLatLong.latitude + "," + "Long : " + mCenterLatLong.longitude);
                                    my_progressBar.setVisibility(View.GONE);

                                    sessionUser.setAddress(listAddresses.get(0).getAddressLine(0),longitude,latitude);

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                });

                try {
                    boolean success = googleMap.setMapStyle(
                            MapStyleOptions.loadRawResourceStyle(
                                    this, R.raw.myy_style_map));

                    if (!success) {
                        Log.e(TAG, "Style parsing failed.");
                    }
                } catch (Resources.NotFoundException e) {
                    Log.e(TAG, "Can't find style. Error: ", e);
                }

            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }



    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        /**
         * Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string or an error message sent from the intent service.
            mAddressOutput = resultData.getString(AppUtils.LocationConstants.RESULT_DATA_KEY);

            mAreaOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_AREA);

            mCityOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_CITY);
            mStateOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_STREET);

            displayAddressOutput();

            // Show a toast message if an address was found.
            if (resultCode == AppUtils.LocationConstants.SUCCESS_RESULT) {
                //  showToast(getString(R.string.address_found));


            }


        }

    }
    protected void displayAddressOutput() {
        //  mLocationAddressTextView.setText(mAddressOutput);
        try {
            if (mAreaOutput != null){

            }
                // mLocationText.setText(mAreaOutput+ "");

                //mLocationAddress.setText(mAddressOutput);
            //mLocationText.setText(mAreaOutput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        my_progressBar.setVisibility(View.VISIBLE);
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    public void searchLocationName(View view) {
        String apiKey = getString(R.string.api_key);
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

// Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

        if(apiKey.isEmpty()){

            return;
        }

        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }
        PlacesClient placesClient = Places.createClient(this);
    }
    public void searchLocation(View view) {
//        EditText locationSearch = (EditText) findViewById(R.id.editText);
//        String location = locationSearch.getText().toString();
//        List<Address> addressList = null;
//
//        if (location != null || !location.equals("")) {
//            Geocoder geocoder = new Geocoder(this);
//            try {
//                addressList = geocoder.getFromLocationName(location, 1);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Address address = addressList.get(0);
//            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//            mMap.addMarker(new MarkerOptions().position(latLng).title(location));
//            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));


//
//        if (getIntent().hasExtra("helpForm")) {
//
//
//            Intent intent=new Intent(mContext,DashboardActivity.class);
//            intent.putExtra("helpForm", true);
//            finish();
//            startActivity(intent);
//
//        }
//        if (getIntent().hasExtra("nearest")) {
//
//            Intent intent=new Intent(mContext,DashboardActivity.class);
//            intent.putExtra("nearest", true);
//            finish();
//            startActivity(intent);
//
//        }

        //Toast.makeText(getApplicationContext(),""+latitude+""+longitude+""+addressName+""+localAddress, Toast.LENGTH_LONG).show();
        Intent mv=new Intent(SearchAddress.this, HomeActivity.class);
        mv.putExtra("type","current");
        startActivity(mv);
        finish();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                Log.e("Place: " , place.toString());
                mLocationMarkerText.setText(place.getName());

                String location = place.getName();
                List<Address> addressList = null;

                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(addressList.size()>0){
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                    }else{
                        final LatLng lotlonaddress = place.getLatLng();
                        double ll = lotlonaddress.latitude;
                        double lo = lotlonaddress.longitude;
                        Log.e("latitudebgttt: " , String.valueOf(lotlonaddress.latitude));

                        latitude=String.valueOf(lotlonaddress.latitude);
                        longitude=String.valueOf(lotlonaddress.longitude);
                        shortName.setText(place.getName());
                        compAddress.setText(place.getName());
                        sessionUser.setAddress(place.getName(),longitude,latitude);

                        my_progressBar.setVisibility(View.GONE);
                    }


                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

}
