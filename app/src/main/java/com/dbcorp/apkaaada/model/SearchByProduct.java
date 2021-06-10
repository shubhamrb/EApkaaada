package com.dbcorp.apkaaada.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bhupesh Sen on 15-03-2021.
 */
public class SearchByProduct implements Serializable {
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("vendor_settings_id")
    @Expose
    private String vendorSettingsId;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("order_received")
    @Expose
    private String orderReceived;
    @SerializedName("shop_open_time")
    @Expose
    private String shopOpenTime;
    @SerializedName("shop_close_time")
    @Expose
    private String shopCloseTime;
    @SerializedName("shop_off_days")
    @Expose
    private String shopOffDays;
    @SerializedName("today_default_message")
    @Expose
    private String todayDefaultMessage;
    @SerializedName("shopDescription")
    @Expose
    private String shopDescription;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("image1")
    @Expose
    private String image1;
    @SerializedName("image2")
    @Expose
    private String image2;
    @SerializedName("image3")
    @Expose
    private String image3;
    @SerializedName("image4")
    @Expose
    private String image4;
    @SerializedName("vendorName")
    @Expose
    private String vendorName;
    @SerializedName("vendorNumber")
    @Expose
    private String vendorNumber;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("cart_id")
    @Expose
    private String cartId;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("cartQuantity")
    @Expose
    private String cartQuantity;
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
    private Object variantId;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getVendorSettingsId() {
        return vendorSettingsId;
    }

    public void setVendorSettingsId(String vendorSettingsId) {
        this.vendorSettingsId = vendorSettingsId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOrderReceived() {
        return orderReceived;
    }

    public void setOrderReceived(String orderReceived) {
        this.orderReceived = orderReceived;
    }

    public String getShopOpenTime() {
        return shopOpenTime;
    }

    public void setShopOpenTime(String shopOpenTime) {
        this.shopOpenTime = shopOpenTime;
    }

    public String getShopCloseTime() {
        return shopCloseTime;
    }

    public void setShopCloseTime(String shopCloseTime) {
        this.shopCloseTime = shopCloseTime;
    }

    public String getShopOffDays() {
        return shopOffDays;
    }

    public void setShopOffDays(String shopOffDays) {
        this.shopOffDays = shopOffDays;
    }

    public String getTodayDefaultMessage() {
        return todayDefaultMessage;
    }

    public void setTodayDefaultMessage(String todayDefaultMessage) {
        this.todayDefaultMessage = todayDefaultMessage;
    }

    public String getShopDescription() {
        return shopDescription;
    }

    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorNumber() {
        return vendorNumber;
    }

    public void setVendorNumber(String vendorNumber) {
        this.vendorNumber = vendorNumber;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(String cartQuantity) {
        this.cartQuantity = cartQuantity;
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

    public Object getVariantId() {
        return variantId;
    }

    public void setVariantId(Object variantId) {
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
