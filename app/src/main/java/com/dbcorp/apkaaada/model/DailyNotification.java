package com.dbcorp.apkaaada.model;


/**
 * Created by Bhupesh Sen on 18-06-2021.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DailyNotification  implements Serializable {

    @SerializedName("notification_custom_id")
    @Expose
    private String notificationCustomId;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("type_id")
    @Expose
    private String typeId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("city_id")
    @Expose
    private String cityId;

    public String getNotificationCustomId() {
        return notificationCustomId;
    }

    public void setNotificationCustomId(String notificationCustomId) {
        this.notificationCustomId = notificationCustomId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

}
