package com.dbcorp.apkaaada.model.card;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bhupesh Sen on 16-03-2021.
 */
public class UserAddress implements Serializable {
    @SerializedName("address_id")
    @Expose
    private String addressId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("address_type")
    @Expose
    private String addressType;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
}
