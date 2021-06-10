package com.dbcorp.apkaaada.model.shopview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bhupesh Sen on 01-03-2021.
 */
public class SubToSubCategory  implements  Serializable{

    @SerializedName("sub_sub_category_id")
    @Expose
    private String subSubCategoryId;
    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("active")
    @Expose
    private String active;

    public String getSubSubCategoryId() {
        return subSubCategoryId;
    }

    public void setSubSubCategoryId(String subSubCategoryId) {
        this.subSubCategoryId = subSubCategoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

}