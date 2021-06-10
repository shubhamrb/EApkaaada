package com.dbcorp.apkaaada.model.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bhupesh Sen on 16-02-2021.
 */
public class Category  implements Serializable {



    @SerializedName("master_category_id")
    @Expose
    private String master_category_id;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("photo")
    @Expose
    private String photo;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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

    public String getMaster_category_id() {
        return master_category_id;
    }

    public void setMaster_category_id(String master_category_id) {
        this.master_category_id = master_category_id;
    }
}
