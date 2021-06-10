package com.dbcorp.apkaaada.model.Varriant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bhupesh Sen on 16-05-2021.
 */
public class Attributetype implements Serializable {

    @SerializedName("attributeName")
    @Expose
    private String attributeName;
    @SerializedName("attributevalueName")
    @Expose
    private String attributevalueName;

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributevalueName() {
        return attributevalueName;
    }

    public void setAttributevalueName(String attributevalueName) {
        this.attributevalueName = attributevalueName;
    }

}
