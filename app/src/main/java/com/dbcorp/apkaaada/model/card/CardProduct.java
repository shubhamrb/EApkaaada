package com.dbcorp.apkaaada.model.card;
import com.dbcorp.apkaaada.model.shopview.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CardProduct implements Serializable {


    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
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

