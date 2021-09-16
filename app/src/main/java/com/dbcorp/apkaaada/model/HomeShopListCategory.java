package com.dbcorp.apkaaada.model;

import com.dbcorp.apkaaada.model.home.VendorData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Bhupesh Sen on 21-07-2021.
 */
public class HomeShopListCategory implements Serializable {

    @SerializedName("catName")
    @Expose
    private String catName;
    @SerializedName("listData")
    @Expose
    private List<VendorData> listData = null;

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public List<VendorData> getListData() {
        return listData;
    }

    public void setListData(List<VendorData> listData) {
        this.listData = listData;
    }
}
