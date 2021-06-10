package com.dbcorp.apkaaada.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bhupesh Sen on 02-05-2021.
 */
public class CustomerOrderDetails implements Serializable {

    @SerializedName("instructionOrderLog")
    @Expose
    private ArrayList<InstructionLog> instructionOrderLog = null;

    @SerializedName("instruction")
    @Expose
    private ArrayList<String> instruction = null;


    @SerializedName("view")
    @Expose
    private Boolean viewList;

    @SerializedName("orderDetails")
    @Expose
    private OrderParam orderDetails;

    @SerializedName("productDetails")
    @Expose
    private ArrayList<Orders> productDetails = null;

    public OrderParam  getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderParam orderDetails) {
        this.orderDetails = orderDetails;
    }

    public ArrayList<Orders> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ArrayList<Orders> productDetails) {
        this.productDetails = productDetails;
    }

    public ArrayList<InstructionLog> getInstructionOrderLog() {
        return instructionOrderLog;
    }

    public void setInstructionOrderLog(ArrayList<InstructionLog> instructionOrderLog) {
        this.instructionOrderLog = instructionOrderLog;
    }

    public ArrayList<String> getInstruction() {
        return instruction;
    }

    public void setInstruction(ArrayList<String> instruction) {
        this.instruction = instruction;
    }

    public Boolean getViewList() {
        return viewList;
    }

    public void setViewList(Boolean viewList) {
        this.viewList = viewList;
    }
}
