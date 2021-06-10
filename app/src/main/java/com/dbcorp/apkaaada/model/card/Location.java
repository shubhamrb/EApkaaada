package com.dbcorp.apkaaada.model.card;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Location implements Serializable {

    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("delivery_charge_type")
    @Expose
    private String deliveryChargeType;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("delivery_charge_per_km")
    @Expose
    private String deliveryChargePerKm;
    @SerializedName("order_range_limit")
    @Expose
    private String orderRangeLimit;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("active")
    @Expose
    private String active;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeliveryChargeType() {
        return deliveryChargeType;
    }

    public void setDeliveryChargeType(String deliveryChargeType) {
        this.deliveryChargeType = deliveryChargeType;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getDeliveryChargePerKm() {
        return deliveryChargePerKm;
    }

    public void setDeliveryChargePerKm(String deliveryChargePerKm) {
        this.deliveryChargePerKm = deliveryChargePerKm;
    }

    public String getOrderRangeLimit() {
        return orderRangeLimit;
    }

    public void setOrderRangeLimit(String orderRangeLimit) {
        this.orderRangeLimit = orderRangeLimit;
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

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

}

