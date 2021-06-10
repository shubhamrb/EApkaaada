package com.dbcorp.apkaaada.model.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bhupesh Sen on 16-02-2021.
 */
public class VendorData  implements Serializable {

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("whislist_status")
    @Expose
    private String whislistStatus;
    @SerializedName("shopOnOff")
    @Expose
    private String shopOnOff;

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("shop_name")
    @Expose
    private String shop_name;


    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("photo")
    @Expose
    private String photo;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
