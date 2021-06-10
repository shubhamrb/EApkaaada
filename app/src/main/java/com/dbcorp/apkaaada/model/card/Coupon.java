package com.dbcorp.apkaaada.model.card;
import com.dbcorp.apkaaada.model.shopview.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Coupon implements Serializable {

    @SerializedName("coupons_id")
    @Expose
    private String couponsId;
    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("coupon_code")
    @Expose
    private String couponCode;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("startdate")
    @Expose
    private String startdate;
    @SerializedName("enddate")
    @Expose
    private String enddate;
    @SerializedName("per_user_limit")
    @Expose
    private String perUserLimit;
    @SerializedName("description")
    @Expose
    private String description;

    public String getCouponsId() {
        return couponsId;
    }

    public void setCouponsId(String couponsId) {
        this.couponsId = couponsId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getPerUserLimit() {
        return perUserLimit;
    }

    public void setPerUserLimit(String perUserLimit) {
        this.perUserLimit = perUserLimit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

