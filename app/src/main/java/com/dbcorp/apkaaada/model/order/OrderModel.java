package com.dbcorp.apkaaada.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderModel implements Serializable {

    @SerializedName("OrderNumber")
    @Expose
    private String orderNumber;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("payment_mode_id")
    @Expose
    private String paymentModeId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("assign_user_id")
    @Expose
    private Object assignUserId;
    @SerializedName("delivery_remark")
    @Expose
    private Object deliveryRemark;
    @SerializedName("complaint_by")
    @Expose
    private Object complaintBy;
    @SerializedName("complaint")
    @Expose
    private Object complaint;
    @SerializedName("complaint_photo")
    @Expose
    private Object complaintPhoto;
    @SerializedName("auto_money_return")
    @Expose
    private Object autoMoneyReturn;
    @SerializedName("active")
    @Expose
    private String active;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaymentModeId() {
        return paymentModeId;
    }

    public void setPaymentModeId(String paymentModeId) {
        this.paymentModeId = paymentModeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public Object getAssignUserId() {
        return assignUserId;
    }

    public void setAssignUserId(Object assignUserId) {
        this.assignUserId = assignUserId;
    }

    public Object getDeliveryRemark() {
        return deliveryRemark;
    }

    public void setDeliveryRemark(Object deliveryRemark) {
        this.deliveryRemark = deliveryRemark;
    }

    public Object getComplaintBy() {
        return complaintBy;
    }

    public void setComplaintBy(Object complaintBy) {
        this.complaintBy = complaintBy;
    }

    public Object getComplaint() {
        return complaint;
    }

    public void setComplaint(Object complaint) {
        this.complaint = complaint;
    }

    public Object getComplaintPhoto() {
        return complaintPhoto;
    }

    public void setComplaintPhoto(Object complaintPhoto) {
        this.complaintPhoto = complaintPhoto;
    }

    public Object getAutoMoneyReturn() {
        return autoMoneyReturn;
    }

    public void setAutoMoneyReturn(Object autoMoneyReturn) {
        this.autoMoneyReturn = autoMoneyReturn;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

}
