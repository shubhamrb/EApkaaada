package com.dbcorp.apkaaada.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bhupesh Sen on 23-03-2021.
 */
public class UserOrderModel implements Serializable {

    @SerializedName("orderDetails")
    @Expose
    private OrderDetails orderDetails;
    @SerializedName("productDetails")
    @Expose
    private ArrayList<ProductDetail> productDetails = null;

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public ArrayList<ProductDetail> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ArrayList<ProductDetail> productDetails) {
        this.productDetails = productDetails;
    }
}
