package com.dbcorp.apkaaada.model.Varriant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Bhupesh Sen on 16-05-2021.
 */
public class VariantProduct implements Serializable {

    @SerializedName("imagesGallery")
    @Expose
    private List<String> imagesGallery = null;
    @SerializedName("gst_name")
    @Expose
    private String gst_name;
    @SerializedName("gst_value")
    @Expose
    private String gst_value;
    @SerializedName("cartQuantity")
    @Expose
    private String cartQuantity;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("valueName")
    @Expose
    private String valueName;
    @SerializedName("vendor_product_id")
    @Expose
    private String vendorProductId;
    @SerializedName("sub_sub_category_id")
    @Expose
    private String subSubCategoryId;
    @SerializedName("userQuantity")
    @Expose
    private String userQuantity;
    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("variantStatus")
    @Expose
    private String variantStatus;
    @SerializedName("variant_id")
    @Expose
    private String variantId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("sub_sub_category_name")
    @Expose
    private String subSubCategoryName;
    @SerializedName("sub_category_name")
    @Expose
    private String subCategoryName;
    @SerializedName("category_name")
    @Expose
    private String categoryName;

    public List<String> getImagesGallery() {
        return imagesGallery;
    }

    public void setImagesGallery(List<String> imagesGallery) {
        this.imagesGallery = imagesGallery;
    }

    public String getGst_name() {
        return gst_name;
    }

    public void setGst_name(String gst_name) {
        this.gst_name = gst_name;
    }

    public String getGst_value() {
        return gst_value;
    }

    public void setGst_value(String gst_value) {
        this.gst_value = gst_value;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public String getVendorProductId() {
        return vendorProductId;
    }

    public void setVendorProductId(String vendorProductId) {
        this.vendorProductId = vendorProductId;
    }

    public String getSubSubCategoryId() {
        return subSubCategoryId;
    }

    public void setSubSubCategoryId(String subSubCategoryId) {
        this.subSubCategoryId = subSubCategoryId;
    }

    public String getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(String cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public String getUserQuantity() {
        return userQuantity;
    }

    public void setUserQuantity(String userQuantity) {
        this.userQuantity = userQuantity;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getVariantStatus() {
        return variantStatus;
    }

    public void setVariantStatus(String variantStatus) {
        this.variantStatus = variantStatus;
    }

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getSubSubCategoryName() {
        return subSubCategoryName;
    }

    public void setSubSubCategoryName(String subSubCategoryName) {
        this.subSubCategoryName = subSubCategoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
