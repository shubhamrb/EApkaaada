package com.dbcorp.apkaaada.model.Varriant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bhupesh Sen on 16-05-2021.
 */
public class VariantsAttribute implements Serializable {

    @SerializedName("attributeName")
    @Expose
    private String attributeName;
    @SerializedName("attributValue")
    @Expose
    private ArrayList<AttributeValue> attributValue = null;

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public ArrayList<AttributeValue> getAttributValue() {
        return attributValue;
    }

    public void setAttributValue(ArrayList<AttributeValue> attributValue) {
        this.attributValue = attributValue;
    }

}
