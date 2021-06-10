package com.dbcorp.apkaaada.model.Varriant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bhupesh Sen on 06-03-2021.
 */
public class Attribute  implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("product_attribute_id")
    @Expose
    private String productAttributeId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("attribute_id")
    @Expose
    private String attributeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductAttributeId() {
        return productAttributeId;
    }

    public void setProductAttributeId(String productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

}
