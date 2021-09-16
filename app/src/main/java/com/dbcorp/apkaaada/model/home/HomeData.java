package com.dbcorp.apkaaada.model.home;

import com.dbcorp.apkaaada.model.HomeShopListCategory;
import com.dbcorp.apkaaada.model.VendorDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bhupesh Sen on 16-02-2021.
 */
public class HomeData implements Serializable {
    @SerializedName("ecomlist")
    @Expose
    private ArrayList<Category> ecomlist = null;
    @SerializedName("serviceList")
    @Expose
    private ArrayList<Category> serviceList = null;
    @SerializedName("vendorData")
    @Expose
    private ArrayList<VendorData> vendorData = null;
    @SerializedName("offerList")
    @Expose
    private ArrayList<VendorDetails> offerList = null;

    @SerializedName("sliderImages")
    @Expose
    private ArrayList<SliderImage> sliderImages = null;

    @SerializedName("serviceCatVendorList")
    @Expose
    private ArrayList<HomeShopListCategory> serviceCatVendorList = null;
    @SerializedName("ecomCatVendorList")
    @Expose
    private ArrayList<HomeShopListCategory> ecomCatVendorList = null;

    public ArrayList<HomeShopListCategory> getServiceCatVendorList() {
        return serviceCatVendorList;
    }

    public void setServiceCatVendorList(ArrayList<HomeShopListCategory> serviceCatVendorList) {
        this.serviceCatVendorList = serviceCatVendorList;
    }

    public ArrayList<HomeShopListCategory> getEcomCatVendorList() {
        return ecomCatVendorList;
    }

    public void setEcomCatVendorList(ArrayList<HomeShopListCategory> ecomCatVendorList) {
        this.ecomCatVendorList = ecomCatVendorList;
    }

    public ArrayList<SliderImage> getSliderImages() {
        return sliderImages;
    }

    public void setSliderImages(ArrayList<SliderImage> sliderImages) {
        this.sliderImages = sliderImages;
    }



    public ArrayList<Category> getEcomlist() {
        return ecomlist;
    }

    public void setEcomlist(ArrayList<Category> ecomlist) {
        this.ecomlist = ecomlist;
    }

    public ArrayList<Category> getServiceList() {
        return serviceList;
    }

    public void setServiceList(ArrayList<Category> serviceList) {
        this.serviceList = serviceList;
    }

    public ArrayList<VendorData> getVendorData() {
        return vendorData;
    }

    public void setVendorData(ArrayList<VendorData> vendorData) {
        this.vendorData = vendorData;
    }

    public ArrayList<VendorDetails> getOfferList() {
        return offerList;
    }

    public void setOfferList(ArrayList<VendorDetails> offerList) {
        this.offerList = offerList;
    }

}
