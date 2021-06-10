package com.dbcorp.apkaaada.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bhupesh Sen on 15-04-2021.
 */
public class MyComplain implements Serializable {
    @SerializedName("order_complaint_id")
    @Expose
    private String orderComplaintId;
    @SerializedName("vendorName")
    @Expose
    private String vendorName;
    @SerializedName("OrderNumber")
    @Expose
    private String orderNumber;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("orders_detail_id")
    @Expose
    private String ordersDetailId;
    @SerializedName("complaint_date")
    @Expose
    private String complaintDate;
    @SerializedName("complaint")
    @Expose
    private String complaint;
    @SerializedName("complaint_photo")
    @Expose
    private String complaintPhoto;
    @SerializedName("complaint_by")
    @Expose
    private String complaintBy;
    @SerializedName("status")
    @Expose
    private String status;

    public String getOrderComplaintId() {
        return orderComplaintId;
    }

    public void setOrderComplaintId(String orderComplaintId) {
        this.orderComplaintId = orderComplaintId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrdersDetailId() {
        return ordersDetailId;
    }

    public void setOrdersDetailId(String ordersDetailId) {
        this.ordersDetailId = ordersDetailId;
    }

    public String getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(String complaintDate) {
        this.complaintDate = complaintDate;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getComplaintPhoto() {
        return complaintPhoto;
    }

    public void setComplaintPhoto(String complaintPhoto) {
        this.complaintPhoto = complaintPhoto;
    }

    public String getComplaintBy() {
        return complaintBy;
    }

    public void setComplaintBy(String complaintBy) {
        this.complaintBy = complaintBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
