package com.dbcorp.apkaaada.model.card;
import com.dbcorp.apkaaada.model.shopview.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CardProduct implements Serializable {



    @SerializedName("shop_name")
    @Expose
    private String shop_name;
    @SerializedName("discountPrice")
    @Expose
    private String discountPrice;

    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("totalProductPrice")
    @Expose
    private String totalProductPrice;

    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("cityName")
    @Expose
    private String cityName;

    @SerializedName("delivery_charge_per_km")
    @Expose
    private String delivery_charge_per_km;

    @SerializedName("delivery_charge_type")
    @Expose
    private String delivery_charge_type;

    @SerializedName("order_range_limit")
    @Expose
    private String order_range_limit;

    @SerializedName("vendorName")
    @Expose
    private String vendorName;

    public String getVendorId() {
        return vendorId;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getTotalProductPrice() {
        return totalProductPrice;
    }

    public void setTotalProductPrice(String totalProductPrice) {
        this.totalProductPrice = totalProductPrice;
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDelivery_charge_per_km() {
        return delivery_charge_per_km;
    }

    public void setDelivery_charge_per_km(String delivery_charge_per_km) {
        this.delivery_charge_per_km = delivery_charge_per_km;
    }

    public String getDelivery_charge_type() {
        return delivery_charge_type;
    }

    public void setDelivery_charge_type(String delivery_charge_type) {
        this.delivery_charge_type = delivery_charge_type;
    }

    public String getOrder_range_limit() {
        return order_range_limit;
    }

    public void setOrder_range_limit(String order_range_limit) {
        this.order_range_limit = order_range_limit;
    }

    @SerializedName("productCount")
    @Expose
    private Integer productCount;
    @SerializedName("ProductList")
    @Expose
    private ArrayList<Product> productList = null;

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }
}

