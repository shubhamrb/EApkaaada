package com.dbcorp.apkaaada.model.card;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bhupesh Sen on 16-03-2021.
 */
public class Instruction implements Serializable {

    @SerializedName("instruction_id")
    @Expose
    private String instructionId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("active")
    @Expose
    private String active;

    public String getInstructionId() {
        return instructionId;
    }

    public void setInstructionId(String instructionId) {
        this.instructionId = instructionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
