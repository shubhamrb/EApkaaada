package com.dbcorp.apkaaada.model.shopview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bhupesh Sen on 16-03-2021.
 */
public class VendorService implements Serializable {

    @SerializedName("services_id")
    @Expose
    private String id;
    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("name")
    @Expose
    private String serviceName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

}
