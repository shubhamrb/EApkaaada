package com.dbcorp.apkaaada.model.Varriant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Varriants implements Serializable {

    @SerializedName("attribute_name")
    @Expose
    private String attributeName;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("variant_id")
    @Expose
    private String variantId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("discount")
    @Expose
    private Object discount;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("cashback")
    @Expose
    private Object cashback;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("add_date")
    @Expose
    private String addDate;
    @SerializedName("add_by")
    @Expose
    private String addBy;
    @SerializedName("update_date")
    @Expose
    private Object updateDate;
    @SerializedName("update_by")
    @Expose
    private Object updateBy;
    @SerializedName("quantity")
    @Expose
    private String quantity;

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Object getDiscount() {
        return discount;
    }

    public void setDiscount(Object discount) {
        this.discount = discount;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Object getCashback() {
        return cashback;
    }

    public void setCashback(Object cashback) {
        this.cashback = cashback;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getAddBy() {
        return addBy;
    }

    public void setAddBy(String addBy) {
        this.addBy = addBy;
    }

    public Object getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Object updateDate) {
        this.updateDate = updateDate;
    }

    public Object getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Object updateBy) {
        this.updateBy = updateBy;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}