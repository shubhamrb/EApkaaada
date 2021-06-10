package com.dbcorp.apkaaada.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bhupesh Sen on 09-06-2021.
 */
public class UserCurrentAddress implements Serializable {

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    public UserCurrentAddress(String mobile,String address,String latitude, String longitude) {
        this.mobile = mobile;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;

    }
}
