package com.dbcorp.apkaaada.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServiceModel implements Serializable {

    @SerializedName("services_booked_id")
    @Expose
    private String servicesBookedId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("add_by")
    @Expose
    private String addBy;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("house_address")
    @Expose
    private String houseAddress;
    @SerializedName("street_name")
    @Expose
    private String streetName;

    public String getServicesBookedId() {
        return servicesBookedId;
    }

    public void setServicesBookedId(String servicesBookedId) {
        this.servicesBookedId = servicesBookedId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getAddBy() {
        return addBy;
    }

    public void setAddBy(String addBy) {
        this.addBy = addBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

}