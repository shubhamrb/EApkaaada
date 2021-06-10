package com.dbcorp.apkaaada.model.Varriant;

import com.dbcorp.apkaaada.model.shopview.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bhupesh Sen on 16-05-2021.
 */
public class VariantArrayAttributeValue implements Serializable {



    @SerializedName("attributeValueName")
    @Expose
    private String attributeValueName;


    @SerializedName("attributevalue")
    @Expose
    private ArrayList<Attributetype> attributevalue = null;
    @SerializedName("productList")
    @Expose
    private VariantProduct productList;

    public ArrayList<Attributetype> getAttributevalue() {
        return attributevalue;
    }

    public void setAttributevalue(ArrayList<Attributetype> attributevalue) {
        this.attributevalue = attributevalue;
    }

    public VariantProduct getProductList() {
        return productList;
    }

    public String getAttributeValueName() {
        return attributeValueName;
    }

    public void setAttributeValueName(String attributeValueName) {
        this.attributeValueName = attributeValueName;
    }

    public void setProductList(VariantProduct productList) {
        this.productList = productList;
    }
}
