package com.dbcorp.apkaaada.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bhupesh Sen on 12-05-2021.
 */
public  class CheckTrueFalse implements Serializable {
    @SerializedName("viewStatus")
    @Expose
    private Boolean viewStatus=false;

    public Boolean getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(Boolean viewStatus) {
        this.viewStatus = viewStatus;
    }
}
