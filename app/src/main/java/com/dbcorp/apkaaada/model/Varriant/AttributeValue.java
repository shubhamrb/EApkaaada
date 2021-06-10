package com.dbcorp.apkaaada.model.Varriant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AttributeValue implements Serializable {



    @SerializedName("attributeSearchValue")
    @Expose
    private String attributeSearchValue;

    @SerializedName("attributeName")
    @Expose
    private String attributeName;

    @SerializedName("attribute_value_id")
    @Expose
    private String attributeValueId;
    @SerializedName("attribute_id")
    @Expose
    private String attributeId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("active")
    @Expose
    private String active;

    public String getAttributeSearchValue() {
        return attributeSearchValue;
    }

    public void setAttributeSearchValue(String attributeSearchValue) {
        this.attributeSearchValue = attributeSearchValue;
    }

    public String getAttributeValueId() {
        return attributeValueId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public void setAttributeValueId(String attributeValueId) {
        this.attributeValueId = attributeValueId;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

}