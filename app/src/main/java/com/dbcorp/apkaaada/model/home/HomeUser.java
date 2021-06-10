package com.dbcorp.apkaaada.model.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Bhupesh Sen on 16-02-2021.
 */
public class HomeUser {
    @SerializedName("homeData")
    @Expose
    private List<HomeData> homeData = null;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<HomeData> getHomeData() {
        return homeData;
    }

    public void setHomeData(List<HomeData> homeData) {
        this.homeData = homeData;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
