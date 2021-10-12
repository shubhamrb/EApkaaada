package com.dbcorp.apkaaada.network;

import com.dbcorp.apkaaada.model.OTP;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.home.HomeUser;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface ApiService {

    //   String BASE_URL = "http://cashsaledev.groupbhaskar.in/";   static String AppType="( TEST )";

    //test server start
   // String MAIN_BASE_URL = "http://192.168.29.56/";
    //String MAIN_IMAGE_BASE_URL = "http://192.168.29.56/top10/";
    //String BASE_URL = MAIN_BASE_URL + "top10/api/v1/user-api/";
    //test server en

    //live server start
    String MAIN_BASE_URL ="http://top10india.in/";
    String MAIN_IMAGE_BASE_URL ="http://top10india.in/";
    String BASE_URL = MAIN_BASE_URL+"api/v1/user-api/";
    //live server end

    String PRODUCT_IMG_URL = MAIN_IMAGE_BASE_URL + "upload/product/";


    String IMG_PRODUCT_URL = MAIN_IMAGE_BASE_URL + "upload/product/";

    String APP_FOLDER_NAME = "apkaadda/";
    String BASE_IMG_CAT_URL = MAIN_IMAGE_BASE_URL + "upload/category/";
    String OFFER_URL = MAIN_IMAGE_BASE_URL + "upload/offer/";
    String slider_URL = MAIN_IMAGE_BASE_URL + "upload/slider/";

    String VENDOR_IMG_URL = MAIN_IMAGE_BASE_URL + "upload/profilePhoto/";
    String VENDOR_SHOP_IMG_URL = MAIN_IMAGE_BASE_URL + "upload/shop/";
    String COMPLAINT_IMG_URL = MAIN_IMAGE_BASE_URL + "upload/complaint/";


    String APP_USER_TYPE = "5";
    String APP_DEVICE_ID = "1234";
    String AppType = "( LIVE )";//LIVE

    //------------Login Api--------
    @FormUrlEncoded
    @POST(Constants.GET_OTP)
    Call<OTP> getOtp(@FieldMap Map<String, String> uData);


    //------------Register Api--------
    @FormUrlEncoded
    @POST(Constants.MATCH_OTP)
    Call<UserDetails> userRegister(@Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.USER_LOGIN)
    Call<UserDetails> getLogin(@Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.USER_HOME)
    Call<HomeUser> getHomeData(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);

 @FormUrlEncoded
    @POST(Constants.GETPAGEBYPAGETYPE)
    Call<String> getPageByPageType(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.GET_CATEGORY)
    Call<String> getCatData(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);

    // get vendor list
    @FormUrlEncoded
    @POST(Constants.GET_VENDOR)
    Call<String> getVendor(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    // get vendor list
    @FormUrlEncoded
    @POST(Constants.GET_SHOP_VENDOR)
    Call<String> getProductDetails(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.GET_PRODUCT_BY_ID)
    Call<String> getProductById(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    // varriant page
    @FormUrlEncoded
    @POST(Constants.GET_VARIANT)
    Call<String> getVariant(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.ADD_TO_CART)
    Call<String> addToCart(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.GET_ORDER)
    Call<String> userOrder(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.GET_CART_COUNT)
    Call<String> getCardCount(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.GET_CART_COUNT)
    Call<String> getCoupon(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    //location
    @FormUrlEncoded
    @POST(Constants.GET_LOCATION)
    Call<String> getLocation(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    //Search product Data
    @FormUrlEncoded
    @POST(Constants.GET_SEARCH_PRODUCT_DATA)
    Call<String> getSearchData(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    //Search Data
    @FormUrlEncoded
    @POST(Constants.GET_SEARCH_VENDOR_DATA)
    Call<String> getSearchVendorData(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    //Search Data
    @FormUrlEncoded
    @POST(Constants.GET_SEARCH_SERVICE_VENDOR_DATA)
    Call<String> getSearchServiceVendorData(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);

    //Search Data
    @FormUrlEncoded
    @POST(Constants.GET_SEARCH_CAT_DATA)
    Call<String> getSearchCategoryData(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);

    @FormUrlEncoded
    @POST(Constants.GET_SEARCH_SERVICE_CAT_DATA)
    Call<String> getSearchServiceCategoryData(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    //get service shop Data
    @FormUrlEncoded
    @POST(Constants.GET_SERVICE_SHOP)
    Call<String> getServiceShop(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    //get service shop Data
    @FormUrlEncoded
    @POST(Constants.GET_USER_ADDRESS)
    Call<String> getAddress(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    // address in db
    @FormUrlEncoded
    @POST(Constants.ADD_ADDRESS)
    Call<String> setAddress(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.GET_INSTRUCTION)
    Call<String> getInstruction(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.GET_UPDATE_ADDRESS)
    Call<String> updateAddress(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.ADD_USER_ORDER)
    Call<String> proceedOrder(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.ADD_SERVICE_USER)
    Call<String> addServiceShop(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.GET_SERVICE_USER)
    Call<String> getServices(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);

    @FormUrlEncoded
    @POST(Constants.GET_OFFER_USER)
    Call<String> getOffer(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);

    @FormUrlEncoded
    @POST(Constants.GET_ORDERD)
    Call<String> getorder(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);

    @FormUrlEncoded
    @POST(Constants.GET_ORDERD_ITEM)
    Call<String> getorderitem(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);

    @FormUrlEncoded
    @POST(Constants.GET_ORDERD_STATUS)
    Call<String> getorderstatus(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.Add_Booking)
    Call<String> addChattingBooking(@Header("DID") String did, @Header("SK") String SK, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.GET_Booking)
    Call<String> getBooking(@Header("DID") String did, @Header("SK") String SK, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.Add_User_Message)
    Call<String> addMessage(@Header("DID") String did, @Header("SK") String SK, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.My_Chat)
    Call<String> getMyChat(@Header("DID") String did, @Header("SK") String SK, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.My_LAST_Chat)
    Call<String> getLastMessage(@Header("DID") String did, @Header("SK") String SK, @FieldMap Map<String, String> uData);

    @FormUrlEncoded
    @POST(Constants.LIKE_SHOP)
    Call<String> likeShop(@Header("DID") String did, @Header("SK") String SK, @FieldMap Map<String, String> uData);


    // get wishList list
    @FormUrlEncoded
    @POST(Constants.WISH_LIST)
    Call<String> getWish(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    // get wishList list
    @FormUrlEncoded
    @POST(Constants.NEAREST_SHOP)
    Call<String> getNearest(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);

    // get wishList list
    @FormUrlEncoded
    @POST(Constants.CHANGE_PASSWORD)
    Call<String> changePassword(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    // get wishList list
    @FormUrlEncoded
    @POST(Constants.ORDER_IDES)
    Call<String> orderIdes(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    // get wishList list
    @FormUrlEncoded
    @POST(Constants.Add_COMPLAINT)
    Call<String> orderComplaint(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    //Search Data
    @FormUrlEncoded
    @POST(Constants.SEARCH_PRODUCT)
    Call<String> searchSliderProduct(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    //help List  Data
    @FormUrlEncoded
    @POST(Constants.HELP_LIST)
    Call<String> helpList(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    //get web List  Data
    @FormUrlEncoded
    @POST(Constants.HELP_LIST_web)
    Call<String> getWebList(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    // get wishList list
    @FormUrlEncoded
    @POST(Constants.FORGET_PASSWORD)
    Call<String> forGetPassword(@Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.GET_NOTIFICATION)
    Call<String> getNotification(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);

    @FormUrlEncoded
    @POST(Constants.GET_DAILY_NOTIFICATION)
    Call<String> getDailyNotification(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.UPDATE_NOTIFICATION)
    Call<String> updateNotification(@Header("DID") String did, @Header("SK") String SK, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.GET_OFFER_SHOP)
    Call<String> getOfferShop(@Header("SK") String SK, @Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.GET_SINGLE_ORDER)
    Call<String> getSingle(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.GET_DELETE_PRODUCT)
    Call<String> deleteCart(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.GET_ADDRESS)
    Call<String> getUserAddress(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.UPDATE_DATA)
    Call<String> updateData(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.GET_CHARGE)
    Call<String> getCharge(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.ADD_SERVICE_DATA)
    Call<String> addService(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.SEARCH_AUTO_DATA)
    Call<String> searchProduct(@Header("SK") String sk, @Header("DID") String did, @FieldMap Map<String, String> uData);

}



