package com.dbcorp.apkaaada.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bhupesh Sen on 18-02-2021.
 */
public class VendorDetails implements Serializable {

    @SerializedName("today_default_message")
    @Expose
    private String todayDefaultMessage;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("whislist_status")
    @Expose
    private String whislistStatus;
    @SerializedName("shopOnOff")
    @Expose
    private String shopOnOff;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("type_id")
    @Expose
    private String typeId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("master_category_id")
    @Expose
    private String masterCategoryId;
    @SerializedName("p_category_id")
    @Expose
    private String pCategoryId;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("referral")
    @Expose
    private String referral;
    @SerializedName("referral_by")
    @Expose
    private Object referralBy;
    @SerializedName("default_address_id")
    @Expose
    private Object defaultAddressId;
    @SerializedName("distance")
    @Expose
    private String distance;

    public String getTodayDefaultMessage() {
        return todayDefaultMessage;
    }

    public void setTodayDefaultMessage(String todayDefaultMessage) {
        this.todayDefaultMessage = todayDefaultMessage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getWhislistStatus() {
        return whislistStatus;
    }

    public void setWhislistStatus(String whislistStatus) {
        this.whislistStatus = whislistStatus;
    }

    public String getShopOnOff() {
        return shopOnOff;
    }

    public void setShopOnOff(String shopOnOff) {
        this.shopOnOff = shopOnOff;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getMasterCategoryId() {
        return masterCategoryId;
    }

    public void setMasterCategoryId(String masterCategoryId) {
        this.masterCategoryId = masterCategoryId;
    }

    public String getpCategoryId() {
        return pCategoryId;
    }

    public void setpCategoryId(String pCategoryId) {
        this.pCategoryId = pCategoryId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public Object getReferralBy() {
        return referralBy;
    }

    public void setReferralBy(Object referralBy) {
        this.referralBy = referralBy;
    }

    public Object getDefaultAddressId() {
        return defaultAddressId;
    }

    public void setDefaultAddressId(Object defaultAddressId) {
        this.defaultAddressId = defaultAddressId;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

}
