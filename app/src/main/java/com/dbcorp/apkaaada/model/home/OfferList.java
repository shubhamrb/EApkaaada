package com.dbcorp.apkaaada.model.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bhupesh Sen on 16-02-2021.
 */
public class OfferList {

    @SerializedName("pic")
    @Expose
    private String pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
