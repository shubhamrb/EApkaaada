package com.dbcorp.apkaaada.model.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bhupesh Sen on 13-04-2021.
 */
public class SliderImage  implements Serializable {

    @SerializedName("slider_id")
    @Expose
    private String sliderId;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("add_date")
    @Expose
    private String addDate;

    public String getSliderId() {
        return sliderId;
    }

    public void setSliderId(String sliderId) {
        this.sliderId = sliderId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

}
